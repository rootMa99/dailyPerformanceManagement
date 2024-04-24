package com.dpm.dailyPerformanceManagement.repositories;

import com.dpm.dailyPerformanceManagement.domain.KKpiNames;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KKpiNamesRepo extends JpaRepository<KKpiNames, Long> {
    KKpiNames findByKpiName(String name);
    KKpiNames findByAlias(String name);
}
