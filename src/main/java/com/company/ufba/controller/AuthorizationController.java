package com.company.ufba.controller;

import com.company.ufba.dto.PasswordRecovery;
import com.company.ufba.dto.User;
import com.company.ufba.services.AuthorizationServices;
import com.company.ufba.utils.Exception.custom.LoginException;
import jakarta.validation.Valid;
import org.jsoup.HttpStatusException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Map;
import java.util.Objects;


@Controller
@RequestMapping(value = "/auth")
public class AuthorizationController {

    @PostMapping("login")
    public ResponseEntity<?> userLogin( @Valid @RequestBody User user)  {

        user.setLoginInfo(Map.of("cpf", user.getRegister()
                                ,"senha",user.getPassword()
                                ,"x","43"
                                ,"y","4"));
        HttpHeaders headers = new HttpHeaders();
        new AuthorizationServices().login(user).forEach(headers::set);
        return ResponseEntity.ok().headers(headers).build();
    }
    @PostMapping("password")
    public ResponseEntity<?> userRecovery(@Valid @RequestBody PasswordRecovery pass){
        PasswordRecovery recoveryAuthorization = new AuthorizationServices().passwordRecovery(pass);
        return recoveryAuthorization != null?
                ResponseEntity.ok(recoveryAuthorization):ResponseEntity.badRequest().build();
    }
    public ResponseEntity<?> passwordRecovery(){
        return null;
    }
    @GetMapping("teste")
    public ResponseEntity<?> teste(){
        throw new LoginException("teste");

    }
}
