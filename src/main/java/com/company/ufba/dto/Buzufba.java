package com.company.ufba.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data @NoArgsConstructor
public class Buzufba {
    private Type type;
    private Map<String,Double> locale;


}

