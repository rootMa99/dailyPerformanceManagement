package com.dpm.dailyPerformanceManagement.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity(name = "productivity")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Productivity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double realValue;
    private double targetValue;
    private String name;
    private String type;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dbd_id")
    private DataByDate dbd;
    @OneToMany(mappedBy = "productivity", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ActionPlan> actionPlans;
    @OneToMany(mappedBy = "productivity", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Pareto> paretos;
}
