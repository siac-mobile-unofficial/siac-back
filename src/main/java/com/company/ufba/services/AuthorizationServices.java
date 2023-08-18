package com.company.ufba.services;

import com.company.ufba.dto.User;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.helper.ValidationException;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

@Service
public class AuthorizationServices {

    private final String urlData = "https://siac.ufba.br/SiacWWW/Welcome.do";
    private final  String urlLogin = "https://siac.ufba.br/SiacWWW/LogonSubmit.do";
    private final String urlNotes ="https://siac.ufba.br/SiacWWW/ConsultarCoeficienteRendimento.do";

    public List<?> login(User user) {
        try {

           Connection.Response responseData = Jsoup.connect(urlData).execute();
           Connection.Response responseLogin = Jsoup.connect(urlLogin)
                   .newRequest().timeout(5000)
                   .cookies(responseData.cookies())
                   .data(user.getLoginInfo())
                   .execute().method(Connection.Method.POST);
                   if (responseLogin.parse().forms().size() >0){
                       return null;
                   }
//           Logger.getLogger("Nova sessão").info("Cookies: "+responseLogin.cookies().values().toString());

           return List.of(responseLogin.cookies(),responseLogin.headers());
        } catch (IOException | ValidationException e) {
            Logger.getLogger("Login").info("Login error: " + e.getMessage());
            return null;
        }

    }



}