package com.company.ufba.services;

import com.company.ufba.dto.User;
import com.company.ufba.security.TokenService;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.helper.ValidationException;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Service
public class AuthorizationServices {

    private final String urlData = "https://siac.ufba.br/SiacWWW/Welcome.do";
    private final  String urlLogin = "https://siac.ufba.br/SiacWWW/LogonSubmit.do";
    private final String urlNotes ="https://siac.ufba.br/SiacWWW/ConsultarCoeficienteRendimento.do";

    public List<?> login(User user) {
        try {
           Connection.Response responseData = Jsoup.connect(urlData).get().connection().response();
           Connection.Response responseLogin = Jsoup.connect(urlLogin)
                   .newRequest().timeout(5000)
                   .cookies(responseData.cookies())
                   .data(user.getLoginInfo())
                   .post().connection().response();
           Document doc = Jsoup.connect(urlNotes)
                   .newRequest()
                   .cookies(responseLogin.cookies())
                   .headers(responseLogin.headers())
                   .get();
            System.out.println(doc.getElementsByClass("even").tagName("td"));
           Logger.getLogger("Nova sessão").info(responseLogin.cookies().values().toString());
           return List.of(responseLogin.cookies(),responseLogin.headers());
        } catch (IOException | ValidationException e) {
            Logger.getLogger("Login").info("Login error: " + e.getMessage());
            return null;
        }

    }



}