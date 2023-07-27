package com.company.ufba.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.HashMap;
import java.util.Map;

@Getter @Setter @NoArgsConstructor

public class User {

    private String register;
    private String password;

    private Map<String, String> loginInfo = new HashMap<>();
}