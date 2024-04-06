package com.dpm.dailyPerformanceManagement.repositories;

import com.dpm.dailyPerformanceManagement.domain.Files;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilesRepo extends JpaRepository<Files, Long> {
    Files findByFileId(String fileId);
}
