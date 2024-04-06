package com.dpm.dailyPerformanceManagement.repositories;

import com.dpm.dailyPerformanceManagement.domain.Quality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface QualityRepo extends JpaRepository<Quality, Long> {
    List<Quality> findAllByDbdDateDpmBetween(Date startDate, Date endDate);
}
