package com.company.ufba.buzufba;

import jakarta.persistence.*;
import lombok.Data;


@Entity @Data
@Table(name = "bus", schema = "buzufba")
public class BusEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "name")
    private String name;
    @OneToOne
    @PrimaryKeyJoinColumn(name = "router")
    private RouterEntity router;
    @ManyToOne
    @JoinColumn(name = "point")
    private PointEntity point;

    }
