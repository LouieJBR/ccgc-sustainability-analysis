package com.ccgc.cggcbackend.service;

import com.ccgc.cggcbackend.model.ProfilingResult;
import com.ccgc.cggcbackend.request.CodeSubmissionRequest;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Value;


@Service
public class CodeProfilerService {

    public ProfilingResult profileCode(CodeSubmissionRequest request) {
        String filePath = saveCodeToTempFile(request.getCode(), request.getLanguage(), request.getFileNameHint());

        long startTime = System.nanoTime();
        long startMemory = getUsedMemory();

        Process process = runCode(filePath, request.getLanguage());

        long endTime = System.nanoTime();
        long endMemory = getUsedMemory();

        double cpuTimeMs = (endTime - startTime) / 1_000_000.0;
        double memoryUsedMb = (endMemory - startMemory) / (1024.0 * 1024);
        double estimatedEnergy = estimateEnergy(cpuTimeMs, memoryUsedMb);

        int greenScore = calculateGreenScore(cpuTimeMs, memoryUsedMb, estimatedEnergy);
        List<String> suggestions = generateSuggestions(request.getCode(), cpuTimeMs, memoryUsedMb);

//        String region = getExecutionRegion();
        String region = "GB"; // Use zone code like "GB", "DE", "FR", "US-NY"
        double carbonIntensity = getCarbonIntensityFromAPI(region);

        return new ProfilingResult(cpuTimeMs, memoryUsedMb, estimatedEnergy, process.exitValue(),
                greenScore, suggestions, region, carbonIntensity);
    }

    private String saveCodeToTempFile(String code, String language, String fileNameHint) {
        String extension = switch (language.toLowerCase()) {
            case "python" -> ".py";
            case "js" -> ".js";
            case "java" -> ".java";
            default -> throw new RuntimeException("Unsupported language");
        };
        try {
            Path tempFile = Path.of(System.getProperty("java.io.tmpdir"), fileNameHint + extension);
            Files.writeString(tempFile, code);
            return tempFile.toAbsolutePath().toString();
        } catch (IOException e) {
            throw new RuntimeException("Failed to save code", e);
        }
    }

    private Process runCode(String filePath, String language) {
        List<String> command;
        switch (language.toLowerCase()) {
            case "python" -> command = List.of("python", filePath);
            case "js" -> command = List.of("node", filePath);
            case "java" -> {
                String className = new File(filePath).getName().replace(".java", "");
                try {
                    Process compile = new ProcessBuilder("javac", filePath)
                            .inheritIO() // ✅ This will show compile errors in your console
                            .start();
                    compile.waitFor();
                } catch (Exception e) {
                    throw new RuntimeException("Compilation failed: " + e.getMessage());
                }
                command = List.of("java", "-cp", new File(filePath).getParent(), className);
            }
            default -> throw new RuntimeException("Unsupported language");
        }

        try {
            ProcessBuilder builder = new ProcessBuilder(command);
            builder.redirectErrorStream(true);
            Process process = builder.start();

            // ✅ Capture output for debugging
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println("[OUTPUT] " + line);
                }
            }

            process.waitFor(10, TimeUnit.SECONDS);
            return process;
        } catch (Exception e) {
            throw new RuntimeException("Code execution failed", e);
        }
    }


    private long getUsedMemory() {
        Runtime runtime = Runtime.getRuntime();
        return runtime.totalMemory() - runtime.freeMemory();
    }

    private double estimateEnergy(double cpuMs, double memMb) {
        return 0.0001 * cpuMs + 0.00005 * memMb;
    }

    int calculateGreenScore(double cpuTimeMs, double memoryUsedMb, double energyJoules) {
        double maxCpuTime = 2000;
        double maxMemory = 500;
        double maxEnergy = 10.0;

        double cpuScore = 1.0 - normalize(cpuTimeMs, maxCpuTime);
        double memScore = 1.0 - normalize(memoryUsedMb, maxMemory);
        double energyScore = 1.0 - normalize(energyJoules, maxEnergy);

        double weightedScore = (cpuScore * 0.4) + (memScore * 0.3) + (energyScore * 0.3);
        return (int) Math.round(weightedScore * 100);
    }

    private double normalize(double value, double max) {
        return Math.min(1.0, value / max);
    }

    List<String> generateSuggestions(String code, double cpuMs, double memMb) {
        List<String> tips = new ArrayList<>();

        if (cpuMs > 1000) tips.add("Optimize loops or function calls to reduce CPU time.");
        if (memMb > 200) tips.add("High memory use detected. Use efficient data structures.");

        // Improved logic for multiple languages
        if ((code.contains("for (") && code.split("for \\(").length > 2) ||
                (code.contains("for ") && code.contains("in range(") && code.split("for ").length > 2)) {
            tips.add("Nested loops detected. Consider refactoring.");
        }

        if (code.contains("sleep(") || code.contains("time.sleep(")) {
            tips.add("Avoid unnecessary sleep statements.");
        }

        if (code.contains(".map(") && code.contains(".filter(")) {
            tips.add("Combine map/filter for efficiency.");
        }

        return tips;
    }


//    private String getExecutionRegion() {
//        try {
//            return InetAddress.getLocalHost().getHostName();
//        } catch (UnknownHostException e) {
//            return "unknown";
//        }
//    }

    @Value("${electricitymap.api.key}")
    private String electricityMapApiKey;

    private double getCarbonIntensityFromAPI(String region) {
        try {
            String url = "https://api.electricitymap.org/v3/carbon-intensity/latest?zone=" + region;

            HttpHeaders headers = new HttpHeaders();
            headers.set("auth-token", "ZcY4ZMGEz2kopq9IOC2K"); // ✅ Correct token header

            HttpEntity<Void> entity = new HttpEntity<>(headers);
            RestTemplate restTemplate = new RestTemplate();

            ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                Object intensityValue = response.getBody().get("carbonIntensity");

                if (intensityValue instanceof Number) {
                    return ((Number) intensityValue).doubleValue();
                }
            }
        } catch (Exception e) {
            System.err.println("Failed to fetch carbon intensity: " + e.getMessage());
        }

        return 450.0; // fallback value
    }

}
