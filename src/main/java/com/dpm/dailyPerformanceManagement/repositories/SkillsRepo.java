package com.dpm.dailyPerformanceManagement.repositories;

import com.dpm.dailyPerformanceManagement.domain.Skills;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface SkillsRepo extends JpaRepository<Skills, Long> {
    List<Skills> findAllByDbdDateDpmBetween(Date startDate, Date endDate);
}
