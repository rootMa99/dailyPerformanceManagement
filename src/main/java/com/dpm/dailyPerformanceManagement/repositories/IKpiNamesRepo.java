package com.dpm.dailyPerformanceManagement.repositories;

import com.dpm.dailyPerformanceManagement.domain.IKpiNames;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IKpiNamesRepo extends JpaRepository<IKpiNames, Long> {
    IKpiNames findByKpiName(String name);
    IKpiNames findByAlias(String name);
}
