package com.dpm.dailyPerformanceManagement.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "SfpiNames")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class SfKpiNames {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String kpiName;
    private String alias;
    private String type;
}
