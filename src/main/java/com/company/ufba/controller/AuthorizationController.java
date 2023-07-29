package com.company.ufba.controller;

import com.company.ufba.dto.User;
import com.company.ufba.services.AuthorizationServices;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("auth")
public class AuthorizationController {

    @PostMapping("login")
    public ResponseEntity<?> userLogin( @Valid @RequestBody User user){
        user.setLoginInfo(Map.of("cpf", user.getRegister()
                                ,"senha",user.getPassword()
                                ,"x","43"
                                ,"y","4"));
        return ResponseEntity.ok(new AuthorizationServices().login(user));
    }

    @GetMapping("/teste")
    public String teste(){
        return "teste";
    }
}
