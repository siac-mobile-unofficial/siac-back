package com.company.ufba.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;
@Getter @Setter @NoArgsConstructor
public class Response {
    private Map<String,String> cookies;

    private Map<String,String> headers;
}
