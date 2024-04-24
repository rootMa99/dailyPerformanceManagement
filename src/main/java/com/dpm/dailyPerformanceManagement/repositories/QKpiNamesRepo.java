package com.dpm.dailyPerformanceManagement.repositories;

import com.dpm.dailyPerformanceManagement.domain.QKpiNames;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QKpiNamesRepo extends JpaRepository<QKpiNames, Long> {
    QKpiNames findByKpiName(String name);
    QKpiNames findByAlias(String name);
}
