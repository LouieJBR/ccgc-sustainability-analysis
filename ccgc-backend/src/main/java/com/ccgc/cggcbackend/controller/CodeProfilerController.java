package com.ccgc.cggcbackend.controller;

import com.ccgc.cggcbackend.model.ProfilingResult;
import com.ccgc.cggcbackend.request.CodeSubmissionRequest;
import com.ccgc.cggcbackend.service.CodeProfilerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/analyze")
public class CodeProfilerController {

    private final CodeProfilerService service;

    public CodeProfilerController(CodeProfilerService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ProfilingResult> analyzeCode(@RequestBody CodeSubmissionRequest request) {
        return ResponseEntity.ok(service.profileCode(request));
    }
}
