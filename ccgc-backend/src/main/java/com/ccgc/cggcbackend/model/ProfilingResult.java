package com.ccgc.cggcbackend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
public class ProfilingResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private Long id;

    @Getter @Setter
    private double cpuTimeMs;

    @Getter @Setter
    private double memoryUsedMb;

    @Getter @Setter
    private double estimatedEnergyJoules;

    @Getter @Setter
    private int exitCode;

    @Getter @Setter
    private int greenScore;

    @ElementCollection
    @Getter @Setter
    private List<String> suggestions;

    @Getter @Setter
    private String executionRegion;

    @Getter @Setter
    private double carbonIntensity;

    @CreationTimestamp
    @Column(updatable = false)
    @Getter @Setter
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @Getter @Setter
    private User user;

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
}
