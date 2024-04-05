package com.dpm.dailyPerformanceManagement.repositories;

import com.dpm.dailyPerformanceManagement.domain.DKpiNames;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DKpiNamesRepo extends JpaRepository<DKpiNames, Long> {
    DKpiNames findByKpiName(String name);
}
