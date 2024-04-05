package com.dpm.dailyPerformanceManagement.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "inventory")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double realValue;
    private double targetValue;
    private String name;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dbd_id")
    private DataByDate dbd;
}
