package com.ccgc.cggcbackend.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "profiling_data")
public class ProfilingData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // Establishes a relationship to the User model.

    @Column(name = "profiling_date")
    private LocalDateTime profilingDate;

    @Column(name = "profile_url")
    private String profileUrl;

    @Column(name = "cpu_usage")
    private Float cpuUsage;

    @Column(name = "memory_usage")
    private Float memoryUsage;

    @Column(name = "network_usage")
    private Float networkUsage;

    @Column(name = "energy_consumption")
    private Float energyConsumption;

    @Column(name = "recommendations")
    private String recommendations;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public ProfilingData() {
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public LocalDateTime getProfilingDate() { return profilingDate; }
    public void setProfilingDate(LocalDateTime profilingDate) { this.profilingDate = profilingDate; }

    public String getProfileUrl() { return profileUrl; }
    public void setProfileUrl(String profileUrl) { this.profileUrl = profileUrl; }

    public Float getCpuUsage() { return cpuUsage; }
    public void setCpuUsage(Float cpuUsage) { this.cpuUsage = cpuUsage; }

    public Float getMemoryUsage() { return memoryUsage; }
    public void setMemoryUsage(Float memoryUsage) { this.memoryUsage = memoryUsage; }

    public Float getNetworkUsage() { return networkUsage; }
    public void setNetworkUsage(Float networkUsage) { this.networkUsage = networkUsage; }

    public Float getEnergyConsumption() { return energyConsumption; }
    public void setEnergyConsumption(Float energyConsumption) { this.energyConsumption = energyConsumption; }

    public String getRecommendations() { return recommendations; }
    public void setRecommendations(String recommendations) { this.recommendations = recommendations; }

    public LocalDateTime getCreatedAt() { return createdAt; }
}
