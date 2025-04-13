package com.ccgc.cggcbackend.service;

import com.ccgc.cggcbackend.model.ProfilingResult;
import com.ccgc.cggcbackend.request.CodeSubmissionRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.TimeUnit;

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

        String region = getExecutionRegion();
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
            Path tempFile = Files.createTempFile(fileNameHint != null ? fileNameHint : "temp", extension);
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
                    Process compile = new ProcessBuilder("javac", filePath).start();
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

    private int calculateGreenScore(double cpuTimeMs, double memoryUsedMb, double energyJoules) {
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

    private List<String> generateSuggestions(String code, double cpuMs, double memMb) {
        List<String> tips = new ArrayList<>();
        if (cpuMs > 1000) tips.add("Optimize loops or function calls to reduce CPU time.");
        if (memMb > 200) tips.add("High memory use detected. Use efficient data structures.");
        if (code.contains("for (") && code.split("for \\(").length > 2) tips.add("Nested loops detected. Consider refactoring.");
        if (code.contains("sleep(")) tips.add("Avoid unnecessary sleep statements.");
        if (code.contains(".map(") && code.contains(".filter(")) tips.add("Combine map/filter for efficiency.");
        return tips;
    }

    private String getExecutionRegion() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            return "unknown";
        }
    }

    private double getCarbonIntensityFromAPI(String region) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String apiUrl = "https://api.electricitymap.org/v3/carbon-intensity?region=" + region;
            Map<String, Object> response = restTemplate.getForObject(apiUrl, Map.class);
            if (response != null && response.containsKey("carbonIntensity")) {
                return Double.parseDouble(response.get("carbonIntensity").toString());
            }
        } catch (Exception e) {
            System.err.println("Failed to fetch carbon intensity: " + e.getMessage());
        }
        return 450.0;
    }
}
