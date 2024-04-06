package com.dpm.dailyPerformanceManagement.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity(name = "dataByDate")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class DataByDate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Temporal(TemporalType.DATE)
    @Column(name = "date")
    private Date dateDpm;
    @OneToMany(mappedBy = "dbd", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Delivery> deliveries;
    @OneToMany(mappedBy = "dbd", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Inventory> inventories;
    @OneToMany(mappedBy = "dbd", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Kaizen> kaizens;
    @OneToMany(mappedBy = "dbd", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Productivity> productivities;
    @OneToMany(mappedBy = "dbd", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Quality> qualities;
    @OneToMany(mappedBy = "dbd", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Safety> safeties;
    @OneToMany(mappedBy = "dbd", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Skills> skillsList;

}
