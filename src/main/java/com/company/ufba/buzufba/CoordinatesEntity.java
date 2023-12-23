package com.company.ufba.buzufba;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Objects;
import java.util.Set;

@Entity @Data
@Table(name = "coordinates", schema = "buzufba")
public class CoordinatesEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @ManyToOne
    @JoinColumn(name = "router")
    private RouterEntity router;
    @Basic
    @Column(name = "ispoint")
    private boolean ispoint;
    @Basic
    @Column(name = "latitude")
    private Double latitude;
    @Basic
    @Column(name = "longitude")
    private Double longitude;

   }
