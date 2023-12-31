package com.company.ufba.controller;

import com.company.ufba.dto.Response;
import com.company.ufba.repositories.BusRepository;

import com.company.ufba.services.ExtraServices;
import com.company.ufba.services.UserServices;
import com.company.ufba.utils.tools.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("user")
public class UserController {



    @GetMapping("notes")
    public ResponseEntity<?> findNotes(@CookieValue("JSESSIONID") String cookie) {
        return ResponseEntity.accepted().body(new UserServices().NotesForStudent(new Response(cookie)));
    }

    @GetMapping("/info")
    public ResponseEntity<?> findInfoUser(@CookieValue("JSESSIONID") String cookie) {
        return ResponseEntity.ok(new UserServices().findUser(new Response(cookie)));
    }

    @GetMapping("/curriculum")
    public ResponseEntity<?> findCurriculum(@CookieValue("JSESSIONID") String cookie) {
        return ResponseEntity.ok(new UserServices().findCurriculum(new Response(cookie)));
    }

    @GetMapping("/classroom")
    public ResponseEntity<?> findClass(@CookieValue("JSESSIONID") String cookie) {
        return ResponseEntity.ok(new UserServices().findClassroom(new Response(cookie)));
    }

    @GetMapping(value = "/register", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<?> findRegisterPDF(@CookieValue("JSESSIONID") String cookie) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Disposition", "attachment; filename=Comprovante_Matricula.pdf");
        return ResponseEntity.ok().headers(headers).body(new UserServices().findPDF(new Response(cookie,Roles.MATRICULA)));
    }

    @GetMapping("/lock")
    public ResponseEntity<?> findLock( @RequestBody Response response) {
        new UserServices().findLockRegister(response);
        return null;
    }

    @GetMapping(value = "/history", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<?> findHistoryPDF(@CookieValue("JSESSIONID") String cookie) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Disposition", "attachment; filename=Historico_Matricula.pdf");
        return ResponseEntity.ok().headers(headers).body(new UserServices().findPDF(new Response(cookie, Roles.HISTORICO)));
    }

//    @Autowired
//    BusRepository bus;
//    @GetMapping("teste")
//    public ResponseEntity<?> t(){
//
//        return ResponseEntity.ok( bus.findById(Long.parseLong("1")));
//    }
}
