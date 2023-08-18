package com.company.ufba.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Classroom {
    private String code;
    private String name;
    private String classroom;
    private String classcode;
    private String date;
    private String hours;
    private String classhours;
    private String local;
    private String teacher;
    public Classroom(List<String> values) {


        this.code = values.get(0);
        this.name = values.get(1);
        this.hours = values.get(2);
        this.classcode = values.get(3);
        this.classroom = values.get(4);
        this.date = values.get(5);
        this.classhours = values.get(6);
        this.local = values.get(7);
        this.teacher = values.get(8);
    }
}
