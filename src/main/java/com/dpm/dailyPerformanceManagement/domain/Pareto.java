package com.dpm.dailyPerformanceManagement.domain;


import jakarta.persistence.*;
import lombok.Data;

@Entity(name = "Pareto")
@Data
public class Pareto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String motif;
    private double percentage;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inventory_id")
    private Inventory inventory;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kaizen_id")
    private Kaizen kaizen;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productivity_id")
    private Productivity productivity;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quality_id")
    private Quality quality;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "safety_id")
    private Safety safety;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "skills_id")
    private Skills skills;

}
