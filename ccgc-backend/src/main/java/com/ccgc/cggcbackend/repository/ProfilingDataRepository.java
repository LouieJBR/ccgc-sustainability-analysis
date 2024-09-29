package com.ccgc.cggcbackend.repository;

import com.ccgc.cggcbackend.model.ProfilingData;
import com.ccgc.cggcbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProfilingDataRepository extends JpaRepository<ProfilingData, Long> {
    List<ProfilingData> findByUser(User user);
}
