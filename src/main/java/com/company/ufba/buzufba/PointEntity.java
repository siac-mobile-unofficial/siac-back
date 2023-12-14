package com.company.ufba.buzufba;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Objects;

@Entity @Data
@Table(name = "point", schema = "buzufba")
public class PointEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "name")
    private String name;
    @OneToOne
    @PrimaryKeyJoinColumn(name = "locale")
    private CoordinatesEntity locale;


}
