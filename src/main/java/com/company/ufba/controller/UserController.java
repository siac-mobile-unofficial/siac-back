package com.company.ufba.controller;

import com.company.ufba.dto.Response;
import com.company.ufba.dto.User;
import com.company.ufba.services.UserServices;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("user")
public class UserController {


    @PostMapping("notes")
    public ResponseEntity<?> findNotes(@Valid @RequestBody Response response){
        return ResponseEntity.accepted().body(new UserServices().NotesForStudent(response));
    }
//    @GetMapping("/info")
//    public ResponseEntity<?> findInfoUser(@Valid @RequestBody Response response){
//        return ResponseEntity.ok(new UserServices().findUser(response));
//    }
@GetMapping("/info")
public ResponseEntity<?> findInfoUser(@RequestHeader("header")Map<String,String>header){
        var r = new Response();
        r.setHeaders(header);
        r.setCookies(Map.of("JSESSIONID",header.get("cookie")));
    return ResponseEntity.ok(new UserServices().findUser(r));
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
    @PostMapping("/lock")
    public ResponseEntity<?> findLock(@Valid @RequestBody Response response){
        new UserServices().findLockRegister(response);
    return null;
    }
    @PostMapping(value="/history",produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<?> findHistory(@Valid @RequestBody Response response){
        return ResponseEntity.ok(new UserServices().findPDFHistory(response));
    }
}
