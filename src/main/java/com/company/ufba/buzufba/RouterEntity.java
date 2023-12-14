package com.company.ufba.buzufba;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Objects;

@Entity @Data
@Table(name = "router", schema = "buzufba")
public class RouterEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "name")
    private String name;


}
