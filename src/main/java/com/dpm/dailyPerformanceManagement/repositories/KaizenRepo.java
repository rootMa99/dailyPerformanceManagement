package com.dpm.dailyPerformanceManagement.repositories;

import com.dpm.dailyPerformanceManagement.domain.Kaizen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface KaizenRepo extends JpaRepository<Kaizen, Long> {
    List<Kaizen> findAllByDbdDateDpmBetween(Date startDate, Date endDate);
}
