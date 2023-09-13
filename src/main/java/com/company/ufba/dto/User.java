package com.company.ufba.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;


import java.util.HashMap;
import java.util.Map;

@Getter @Setter @NoArgsConstructor

public class User {

    private String register;
    private String password;
    @JsonIgnore
    private Map<String, String> loginInfo = new HashMap<>();
}