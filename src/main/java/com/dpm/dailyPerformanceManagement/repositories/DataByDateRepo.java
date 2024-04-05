package com.dpm.dailyPerformanceManagement.repositories;

import com.dpm.dailyPerformanceManagement.domain.DataByDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface DataByDateRepo extends JpaRepository<DataByDate, Long> {
    DataByDate findByDateDpm(Date date);
    List<DataByDate> findAllByDateDpmBetween(Date startDate, Date endDate);
}
