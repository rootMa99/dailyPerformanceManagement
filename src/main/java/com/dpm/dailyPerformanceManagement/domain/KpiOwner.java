package com.dpm.dailyPerformanceManagement.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity(name = "kpi_owner")
@Data
public class KpiOwner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String kpiOwn;
    private String name;
    private String coName;
    @OneToOne(mappedBy = "kpiOwner")
    private Files files;

}
