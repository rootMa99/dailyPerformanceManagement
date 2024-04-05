package com.dpm.dailyPerformanceManagement.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity(name = "skills")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Skills {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double realValue;
    private double targetValue;
    private String name;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dbd_id")
    private DataByDate dbd;
    @OneToMany(mappedBy = "skills", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ActionPlan> actionPlans;
}
