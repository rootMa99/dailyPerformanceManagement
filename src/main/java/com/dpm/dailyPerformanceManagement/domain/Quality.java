package com.dpm.dailyPerformanceManagement.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity(name = "quality")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Quality {
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
    @OneToMany(mappedBy = "quality", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ActionPlan> actionPlans;
}
