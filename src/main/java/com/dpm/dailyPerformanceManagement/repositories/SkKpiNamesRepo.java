package com.dpm.dailyPerformanceManagement.repositories;

import com.dpm.dailyPerformanceManagement.domain.SfKpiNames;
import com.dpm.dailyPerformanceManagement.domain.SkKpiNames;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkKpiNamesRepo extends JpaRepository<SkKpiNames, Long> {
    SkKpiNames findByKpiName(String name);
    SkKpiNames findByAlias(String name);
}
