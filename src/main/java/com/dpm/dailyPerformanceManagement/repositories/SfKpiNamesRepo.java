package com.dpm.dailyPerformanceManagement.repositories;

import com.dpm.dailyPerformanceManagement.domain.SfKpiNames;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SfKpiNamesRepo extends JpaRepository<SfKpiNames, Long> {
    SfKpiNames findByKpiName(String name);
}
