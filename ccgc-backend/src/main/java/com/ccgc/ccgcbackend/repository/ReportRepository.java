package com.ccgc.ccgcbackend.repository;

import java.util.List;

import com.ccgc.ccgcbackend.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {
    List <Report> findByUserId(Long userId);
}
