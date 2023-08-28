package com.company.ufba.controller;

import com.company.ufba.dto.Response;
import com.company.ufba.services.UserServices;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
@CrossOrigin(origins = "http://dev.com")
@RestController
@RequestMapping("user")
public class UserController {
    @PostMapping("notes")
    public ResponseEntity<?> findNotes(@Valid @RequestBody Response response){
        return ResponseEntity.accepted().body(new UserServices().NotesForStudent(response));
    }
    @PostMapping("/info")
    public ResponseEntity<?> findInfoUser(@Valid @RequestBody Response response){
        return ResponseEntity.ok(new UserServices().findUser(response));
    }
    @PostMapping("/curriculum")
    public ResponseEntity<?> findCurriculum(@Valid @RequestBody Response response){
        return  ResponseEntity.ok(new UserServices().findCurriculum(response));
    }
    @PostMapping("/classroom")
    public ResponseEntity<?> findClass(@Valid @RequestBody Response response){
        return ResponseEntity.ok(new UserServices().findClassroom(response));
    }
    @PostMapping(value = "/pdf",produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<?> findPDF(@Valid @RequestBody Response response){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Disposition","attachment; filename=Comprovante_Matricula.pdf");
        return ResponseEntity.ok().headers(headers).body(new UserServices().findPDF(response));
    }
}
