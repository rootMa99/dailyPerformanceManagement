package com.dpm.dailyPerformanceManagement.repositories;

import com.dpm.dailyPerformanceManagement.domain.KpiOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KpiOwnerRepo extends JpaRepository<KpiOwner, Long> {
    KpiOwner findByKpiOwn(String kpi);
}
