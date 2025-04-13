package com.ccgc.cggcbackend.controller;

import com.ccgc.cggcbackend.model.ProfilingResult;
import com.ccgc.cggcbackend.model.User;
import com.ccgc.cggcbackend.repository.ProfilingResultRepository;
import com.ccgc.cggcbackend.repository.UserRepository;
import com.ccgc.cggcbackend.request.CodeSubmissionRequest;
import com.ccgc.cggcbackend.service.CodeProfilerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/analyze")
public class CodeProfilerController {

    private final CodeProfilerService service;
    private final ProfilingResultRepository resultRepository;
    private final UserRepository userRepository;

    public CodeProfilerController(CodeProfilerService service,
                                  ProfilingResultRepository resultRepository,
                                  UserRepository userRepository) {
        this.service = service;
        this.resultRepository = resultRepository;
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<ProfilingResult> analyzeCode(@RequestBody CodeSubmissionRequest request,
                                                       @RequestHeader("Authorization") String token) {
        User user = service.extractUserFromToken(token);
        ProfilingResult result = service.profileCode(request);
        result.setUser(user);
        return ResponseEntity.ok(resultRepository.save(result));
    }

    @GetMapping("/user")
    public ResponseEntity<List<ProfilingResult>> getUserResults(@RequestHeader("Authorization") String token) {
        User user = service.extractUserFromToken(token);
        return ResponseEntity.ok(resultRepository.findByUser(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteResult(@PathVariable Long id,
                                               @RequestHeader("Authorization") String token) {
        User user = service.extractUserFromToken(token);
        ProfilingResult result = resultRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Result not found"));

        if (!result.getUser().getId().equals(user.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Not authorized to delete this result");
        }

        resultRepository.deleteById(id);
        return ResponseEntity.ok("Result deleted successfully");
    }
}