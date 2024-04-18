package com.dpm.dailyPerformanceManagement.repositories;

import com.dpm.dailyPerformanceManagement.domain.Pareto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParetoRepo extends JpaRepository<Pareto, Long> {
    Pareto findByMotif(String motif);
}
