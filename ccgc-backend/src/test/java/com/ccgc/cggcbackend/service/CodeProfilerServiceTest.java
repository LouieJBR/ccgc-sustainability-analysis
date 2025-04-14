package com.ccgc.cggcbackend.service;

import com.ccgc.cggcbackend.model.ProfilingResult;
import com.ccgc.cggcbackend.repository.UserRepository;
import com.ccgc.cggcbackend.request.CodeSubmissionRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(properties = {
        "electricitymap.api.key=ZcY4ZMGEz2kopq9IOC2K"
})
public class CodeProfilerServiceTest {

    UserRepository mockRepo = Mockito.mock(UserRepository.class);
    CodeProfilerService service = new CodeProfilerService(mockRepo);

    @BeforeEach
    public void setUp() {
        service = new CodeProfilerService(mockRepo);
    }

    @Test
    public void testProfileSimplePythonCode() {
        CodeSubmissionRequest request = new CodeSubmissionRequest();
        request.setLanguage("python");
        request.setFileNameHint("test_python");
        request.setCode("print('Hello World')");

        ProfilingResult result = service.profileCode(request);

        assertEquals(0, result.getExitCode());
        assertTrue(result.getCpuTimeMs() >= 0);
        assertTrue(result.getMemoryUsedMb() >= 0);
        assertTrue(result.getGreenScore() >= 0 && result.getGreenScore() <= 100);
        assertNotNull(result.getSuggestions());
    }

    @Test
    public void testProfileSimpleJavaScriptCode() {
        CodeSubmissionRequest request = new CodeSubmissionRequest();
        request.setLanguage("js");
        request.setFileNameHint("test_js");
        request.setCode("console.log('Hello World')");

        ProfilingResult result = service.profileCode(request);

        assertEquals(0, result.getExitCode());
        assertTrue(result.getCpuTimeMs() >= 0);
        assertTrue(result.getMemoryUsedMb() >= 0);
        assertTrue(result.getGreenScore() >= 0 && result.getGreenScore() <= 100);
        assertNotNull(result.getSuggestions());
    }

    @Test
    public void testProfileSimpleJavaCode() {
        CodeSubmissionRequest request = new CodeSubmissionRequest();
        request.setLanguage("java");
        request.setFileNameHint("TestJava");
        request.setCode("""
                public class TestJava {
                    public static void main(String[] args) {
                        System.out.println("Hello");
                    }
                }
                """);

        ProfilingResult result = service.profileCode(request);

        assertEquals(0, result.getExitCode());
        assertTrue(result.getCpuTimeMs() >= 0);
        assertTrue(result.getMemoryUsedMb() >= 0);
        assertTrue(result.getGreenScore() >= 0 && result.getGreenScore() <= 100);
        assertNotNull(result.getSuggestions());
    }

    // Inefficiency Detection Tests
    @Test
    public void testInefficientPythonCodeSuggestions() {
        CodeSubmissionRequest request = new CodeSubmissionRequest();
        request.setLanguage("python");
        request.setFileNameHint("inefficient_python");
        request.setCode("""
                import time
                for i in range(10):
                    for j in range(10):
                        time.sleep(0.01)
                print('Done')
                """);

        ProfilingResult result = service.profileCode(request);

        assertTrue(result.getSuggestions().stream().anyMatch(s -> s.toLowerCase().contains("sleep")));
        assertTrue(result.getSuggestions().stream().anyMatch(s -> s.toLowerCase().contains("nested")));
    }

    @Test
    public void testInefficientJavaScriptCodeSuggestions() {
        CodeSubmissionRequest request = new CodeSubmissionRequest();
        request.setLanguage("js");
        request.setFileNameHint("inefficient_js");
        request.setCode("""
                const data = [1, 2, 3, 4, 5];
                const result = data.map(x => x * 2).filter(x => x > 5);
                for (let i = 0; i < 10; i++) {
                    for (let j = 0; j < 10; j++) {}
                }
                console.log(result);
                """);

        ProfilingResult result = service.profileCode(request);
        assertTrue(result.getSuggestions().stream().anyMatch(s -> s.toLowerCase().contains("nested")));
        assertTrue(result.getSuggestions().stream().anyMatch(s -> s.toLowerCase().contains("map")));
    }

    @Test
    public void testInefficientJavaCodeSuggestions() {
        CodeSubmissionRequest request = new CodeSubmissionRequest();
        request.setLanguage("java");
        request.setFileNameHint("InefficientJava");
        request.setCode("""
                public class InefficientJava {
                    public static void main(String[] args) {
                        for (int i = 0; i < 10; i++) {
                            for (int j = 0; j < 10; j++) {
                                System.out.println(i * j);
                            }
                        }
                    }
                }
                """);

        ProfilingResult result = service.profileCode(request);
        assertTrue(result.getSuggestions().stream().anyMatch(s -> s.toLowerCase().contains("nested")));
    }

    // Internal Logic Tests
    @Test
    public void testGreenScoreBoundaries() {
        int score = service.calculateGreenScore(0, 0, 0);
        assertEquals(100, score);

        score = service.calculateGreenScore(5000, 1000, 100);
        assertTrue(score < 10);
    }

    @Test
    public void testGenerateSuggestions() {
        List<String> suggestions = service.generateSuggestions("for (int i = 0; i < 10; i++) { for (int j = 0; j < 10; j++) {} }", 1500, 250);
        assertTrue(suggestions.stream().anyMatch(s -> s.toLowerCase().contains("nested")));
    }

    @Test
    public void testEstimateEnergyCalculation() {
        double energy = service.estimateEnergy(1000, 500);
        assertEquals(0.125, energy, 0.001);
    }

    @Test
    public void testNormalizeWithinBounds() {
        assertEquals(0.5, service.normalize(50, 100));
        assertEquals(1.0, service.normalize(200, 100));
    }

    @Test
    public void testSaveCodeToTempFileAddsCorrectExtension() {
        String code = "print('test')";
        String path = service.saveCodeToTempFile(code, "python", "snippet_test");
        assertTrue(path.endsWith(".py"));
        assertTrue(new File(path).exists());
    }

    @Test
    public void testSaveCodeToTempFileUnsupportedLanguageThrows() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            service.saveCodeToTempFile("echo Hello", "bash", "script");
        });

        assertTrue(exception.getMessage().contains("Unsupported language"));
    }

    @Test
    public void testRunCodeJavaHandlesCompilationError() {
        String faultyJava = """
                public class Broken {
                    public static void main(String[] args) {
                        System.out.println("Missing bracket"
                    }
                }
                """;
        String path = service.saveCodeToTempFile(faultyJava, "java", "Broken");

        Exception exception = assertThrows(RuntimeException.class, () -> {
            service.runCode(path, "java");
        });

        assertTrue(exception.getMessage().contains("Compilation failed"));
    }

    @Test
    public void testUnsupportedLanguageThrowsException() {
        CodeSubmissionRequest request = new CodeSubmissionRequest();
        request.setLanguage("ruby");
        request.setFileNameHint("test_ruby");
        request.setCode("puts 'Hello'");

        Exception exception = assertThrows(RuntimeException.class, () -> {
            service.profileCode(request);
        });

        assertTrue(exception.getMessage().contains("Unsupported language"));
    }

    @Test
    public void testCarbonIntensityFallback() {
        double intensity = service.getCarbonIntensityFromAPI("INVALID");
        assertTrue(intensity >= 0); // Default value should be returned
    }
}
