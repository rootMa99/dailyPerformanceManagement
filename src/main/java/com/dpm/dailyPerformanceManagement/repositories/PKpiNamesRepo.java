package com.dpm.dailyPerformanceManagement.repositories;

import com.dpm.dailyPerformanceManagement.domain.PkpiNames;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PKpiNamesRepo extends JpaRepository<PkpiNames, Long> {
    PkpiNames findByKpiName(String name);
}
