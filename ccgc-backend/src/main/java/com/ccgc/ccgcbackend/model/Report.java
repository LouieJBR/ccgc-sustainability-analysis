package com.ccgc.ccgcbackend.model;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Table(name = "reports")
@Data
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId; //reference to user

    @Column(nullable = false)
    private double cpuUsage;

    @Column(nullable = false)
    private double memoryUsage;

    @Column(nullable = false)
    private double energyEstimate;

    private String recommendations;

    private String createdAt;
}
