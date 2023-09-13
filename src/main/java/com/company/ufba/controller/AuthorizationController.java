package com.company.ufba.controller;

import com.company.ufba.dto.PasswordRecovery;
import com.company.ufba.dto.User;
import com.company.ufba.services.AuthorizationServices;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



import java.util.Map;

@CrossOrigin(origins = "http://dev.com")
@RestController
@RequestMapping("/auth")
public class AuthorizationController {

    @PostMapping("login")
    public ResponseEntity<?> userLogin( @Valid @RequestBody User user){
        user.setLoginInfo(Map.of("cpf", user.getRegister()
                                ,"senha",user.getPassword()
                                ,"x","43"
                                ,"y","4"));
        var result = new AuthorizationServices().login(user);
        return result != null? ResponseEntity.ok(result):ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(user);
    }
    public ResponseEntity<?> userRecovery(@Valid @RequestBody PasswordRecovery pass){
        return ResponseEntity.ok(new AuthorizationServices().passwordRecovery(pass));
    }
    public ResponseEntity<?> passwordRecovery(){
        return null;
    }
}
