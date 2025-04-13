package com.ccgc.cggcbackend.model;

import java.util.List;

public class ProfilingResult {
    private double cpuTimeMs;
    private double memoryUsedMb;
    private double estimatedEnergyJoules;
    private int exitCode;
    private int greenScore;
    private List<String> suggestions;
    private String executionRegion;
    private double carbonIntensity;

    public ProfilingResult(double cpuTimeMs, double memoryUsedMb, double estimatedEnergyJoules,
                           int exitCode, int greenScore, List<String> suggestions,
                           String executionRegion, double carbonIntensity) {
        this.cpuTimeMs = cpuTimeMs;
        this.memoryUsedMb = memoryUsedMb;
        this.estimatedEnergyJoules = estimatedEnergyJoules;
        this.exitCode = exitCode;
        this.greenScore = greenScore;
        this.suggestions = suggestions;
        this.executionRegion = executionRegion;
        this.carbonIntensity = carbonIntensity;
    }

    public double getCpuTimeMs() { return cpuTimeMs; }
    public double getMemoryUsedMb() { return memoryUsedMb; }
    public double getEstimatedEnergyJoules() { return estimatedEnergyJoules; }
    public int getExitCode() { return exitCode; }
    public int getGreenScore() { return greenScore; }
    public List<String> getSuggestions() { return suggestions; }
    public String getExecutionRegion() { return executionRegion; }
    public double getCarbonIntensity() { return carbonIntensity; }
}
