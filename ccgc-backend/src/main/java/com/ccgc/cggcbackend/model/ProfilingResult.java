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
@Table(name = "profiling_data")
public class ProfilingResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @Getter @Setter
    private User user;

    @Column(name = "profiling_date")
    @Getter @Setter
    private LocalDateTime profilingDate = LocalDateTime.now();

    @Column(name = "profile_url")
    @Getter @Setter
    private String profileUrl;

    @Column(name = "cpu_usage")
    @Getter @Setter
    private Double cpuUsage;

    @Column(name = "memory_usage")
    @Getter @Setter
    private Double memoryUsage;

    @Column(name = "network_usage")
    @Getter @Setter
    private Double networkUsage;

    @Column(name = "energy_consumption")
    @Getter @Setter
    private Double energyConsumption;

    @ElementCollection
    @CollectionTable(name = "profiling_suggestions", joinColumns = @JoinColumn(name = "profiling_id"))
    @Column(name = "recommendations")
    @Getter @Setter
    private List<String> suggestions;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    @Getter @Setter
    private LocalDateTime createdAt;

    @Column(name = "carbon_intensity")
    @Getter @Setter
    private Double carbonIntensity;

    @Column(name = "cpu_time_ms")
    @Getter @Setter
    private double cpuTimeMs;

    @Column(name = "estimated_energy_joules")
    @Getter @Setter
    private double estimatedEnergyJoules;

    @Column(name = "execution_region")
    @Getter @Setter
    private String executionRegion;

    @Column(name = "exit_code")
    @Getter @Setter
    private int exitCode;

    @Column(name = "green_score")
    @Getter @Setter
    private int greenScore;

    @Column(name = "memory_used_mb")
    @Getter @Setter
    private double memoryUsedMb;

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

        // Set calculated/duplicated fields for DB schema compatibility
        this.cpuUsage = cpuTimeMs; // You can later map this differently if needed
        this.memoryUsage = memoryUsedMb;
        this.energyConsumption = estimatedEnergyJoules;
    }
}
