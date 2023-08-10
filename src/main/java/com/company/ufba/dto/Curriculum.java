package com.company.ufba.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class Curriculum {

    private String semester;
    private String type;
    private String code;
    private String name;
    private String requirement;

    public Curriculum(List<String> data){
        semester = data.get(0);
        type = data.get(1);
        code = data.get(2);
        name = data.get(3);
        requirement = data.get(4);
    }
}
