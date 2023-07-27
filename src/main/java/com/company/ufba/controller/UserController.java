package com.company.ufba.controller;

import com.company.ufba.dto.Response;
import com.company.ufba.services.UserServices;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("user")
public class UserController {
    @PostMapping("notes")
    public ResponseEntity<?> findNotes(@Valid @RequestBody Response response){
        return ResponseEntity.accepted().body(new UserServices().NotesForStudent(response));
    }
}
