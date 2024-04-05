package com.dpm.dailyPerformanceManagement.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;


@Entity(name = "action_plan")
@Data
public class ActionPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String issueDescription;
    private String causes;
    private String contermeasures;
    private String resp;
    @Temporal(TemporalType.DATE)
    private Date dueDate;
    private String status;
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
