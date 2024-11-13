package com.dpm.dailyPerformanceManagement.repositories;

import com.dpm.dailyPerformanceManagement.domain.ActionPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ActionPlanRepo extends JpaRepository<ActionPlan, Long> {
    Optional<ActionPlan> findById(Long id);
    List<ActionPlan> findAllByOpenDateBetween(Date startDate, Date endDate);
    ActionPlan save(ActionPlan ap);
}
