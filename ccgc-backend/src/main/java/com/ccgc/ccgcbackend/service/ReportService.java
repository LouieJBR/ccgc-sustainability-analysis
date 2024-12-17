package com.ccgc.ccgcbackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ccgc.ccgcbackend.repository.ReportRepository;
import com.ccgc.ccgcbackend.model.Report;

import java.util.List;

@Service
public class ReportService {

    @Autowired
    private ReportRepository reportRepository;

    public Report saveReport(Report report) {
        return reportRepository.save(report);
    }

    public List<Report> getReportsByUserId(Long userId) {
        return reportRepository.findByUserId(userId);
    }

}
