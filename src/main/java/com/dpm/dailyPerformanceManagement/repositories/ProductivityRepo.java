package com.dpm.dailyPerformanceManagement.repositories;

import com.dpm.dailyPerformanceManagement.domain.Productivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ProductivityRepo extends JpaRepository<Productivity, Long> {
    List<Productivity> findAllByDbdDateDpmBetween(Date startDate, Date endDate);
}
