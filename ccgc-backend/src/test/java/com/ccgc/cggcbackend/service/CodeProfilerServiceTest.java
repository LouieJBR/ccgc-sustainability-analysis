package com.ccgc.cggcbackend.service;

import com.ccgc.cggcbackend.model.ProfilingResult;
import com.ccgc.cggcbackend.repository.UserRepository;
import com.ccgc.cggcbackend.request.CodeSubmissionRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(properties = {
        "electricitymap.api.key=ZcY4ZMGEz2kopq9IOC2K"  // use your real key or dummy - remove
})

public class CodeProfilerServiceTest {
    UserRepository mockRepo = Mockito.mock(UserRepository.class);
    CodeProfilerService service = new CodeProfilerService(mockRepo);

    @BeforeEach
    public void setUp() {
        service = new CodeProfilerService(mockRepo);
    }

    @Test
    // Tests if a basic Python snippet can be profiled successfully
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
    public void testInefficientPythonCodeSuggestions() {
        CodeSubmissionRequest request = new CodeSubmissionRequest();
        request.setLanguage("python");
        request.setFileNameHint("inefficient_python");
        request.setCode(
                "import time\n" +
                        "for i in range(10):\n" +
                        "    for j in range(10):\n" +
                        "        time.sleep(0.01)\n" +
                        "print('Done')"
        );

        ProfilingResult result = service.profileCode(request);

        assertTrue(result.getSuggestions().stream().anyMatch(s -> s.toLowerCase().contains("sleep")));
        assertTrue(result.getSuggestions().stream().anyMatch(s -> s.toLowerCase().contains("nested")));
    }



    @Test
    public void testProfileSimpleJavaScriptCode() {
        // Ensures the profiler handles JavaScript correctly.
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
    public void testProfileSimpleJavaCode() {
        // Tests profiling of a simple Java class.
        CodeSubmissionRequest request = new CodeSubmissionRequest();
        request.setLanguage("java");
        request.setFileNameHint("TestJava");
        request.setCode(
                "public class TestJava {\n" +
                        "    public static void main(String[] args) {\n" +
                        "        System.out.println(\"Hello\");\n" +
                        "    }\n" +
                        "}"
        );

        ProfilingResult result = service.profileCode(request);

        assertEquals(0, result.getExitCode());
        assertTrue(result.getCpuTimeMs() >= 0);
        assertTrue(result.getMemoryUsedMb() >= 0);
        assertTrue(result.getGreenScore() >= 0 && result.getGreenScore() <= 100);
        assertNotNull(result.getSuggestions());
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


    @Test
    public void testGreenScoreBoundaries() {
        // Tests edge cases for the green score calculation.
        int score = service.calculateGreenScore(0, 0, 0);
        assertEquals(100, score);

        score = service.calculateGreenScore(5000, 1000, 100);
        assertTrue(score < 10);
    }

    @Test
    public void testGenerateSuggestions() {
        // Validates that the suggestion engine catches inefficient code patterns
        List<String> suggestions = service.generateSuggestions("for (int i = 0; i < 10; i++) { for (int j = 0; j < 10; j++) {} }", 1500, 250);
        assertTrue(suggestions.stream().anyMatch(s -> s.toLowerCase().contains("nested")));
    }

    @Test
    public void testUnsupportedLanguageThrowsException() {
        CodeSubmissionRequest request = new CodeSubmissionRequest();
        request.setLanguage("ruby");  // Not supported
        request.setFileNameHint("test_ruby");
        request.setCode("puts 'Hello'");

        Exception exception = assertThrows(RuntimeException.class, () -> {
            service.profileCode(request);
        });

        String expectedMessage = "Unsupported language";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

}
