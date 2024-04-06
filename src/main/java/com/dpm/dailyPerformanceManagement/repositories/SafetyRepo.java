package com.dpm.dailyPerformanceManagement.repositories;

import com.dpm.dailyPerformanceManagement.domain.Safety;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface SafetyRepo extends JpaRepository<Safety, Long> {
    List<Safety> findAllByDbdDateDpmBetween(Date startDate, Date endDate);
}
