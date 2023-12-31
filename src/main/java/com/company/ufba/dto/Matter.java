package com.company.ufba.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Matter {
    private String period;
    private String code;
    private String name;
    private String hours;
    private String note;
    private String status;
    private String score;

    public Matter(List<String> infos) throws IndexOutOfBoundsException{
        this.period = infos.get(0);
        this.code = infos.get(1);
        this.name = infos.get(2);
        this.hours = infos.get(3);
        this.note = infos.get(4);
        this.score = infos.get(5);
        this.status = infos.get(6);
    }


}
