package com.ccgc.cggcbackend.repository;

import com.ccgc.cggcbackend.model.ProfilingResult;
import com.ccgc.cggcbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProfilingResultRepository extends JpaRepository<ProfilingResult, Long> {
    List<ProfilingResult> findByUser(User user);
}
