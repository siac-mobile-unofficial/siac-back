package com.company.ufba.services;

import com.company.ufba.dto.PasswordRecovery;
import com.company.ufba.dto.User;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.helper.ValidationException;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

@Service
public class AuthorizationServices {

    private final String urlData = "https://siac.ufba.br/SiacWWW/Welcome.do";
    private final  String urlLogin = "https://siac.ufba.br/SiacWWW/LogonSubmit.do";

    private final String urlPassword = "https://sius.ufba.br/sius/AlterarSenhaSistemaWebPublico/Alterar.do";
    private final String[] urlPasswordEmail = {};
    private final String UrlPasswordData = "https://sius.ufba.br/sius/Welcome.do?action=senha&result=siac";
    public List<?> login(User user) {
        try {

           Connection.Response responseData = Jsoup.connect(urlData).execute();
           Connection.Response responseLogin = Jsoup.connect(urlLogin)
                   .newRequest().timeout(5000)
                   .cookies(responseData.cookies())
                   .data(user.getLoginInfo())
                   .execute().method(Connection.Method.POST);
                   if (!responseLogin.parse().forms().isEmpty()){
                       return null;
                   }
//           Logger.getLogger("Nova sessão").info("Cookies: "+responseLogin.cookies().values().toString());

           return List.of(responseLogin.cookies(),responseLogin.headers());
        } catch (IOException | ValidationException e) {
            Logger.getLogger("Login").info("Login error: " + e.getMessage());
            return null;
        }

    }
    public PasswordRecovery passwordRecovery(PasswordRecovery pass){
        String result;
        try {
            Connection.Response responseData = Jsoup.connect(UrlPasswordData).execute();
            Document doc = Jsoup.connect(urlPassword)
                    .newRequest()
                    .header("Origin","https://sius.ufba.br")
                    .cookies(responseData.cookies())
                    .data(new HashMap<>(pass.allData())).post();
            //System.out.println(doc);

            if (!doc.getElementsByClass("alertbox-white").isEmpty()){
                result = doc.getElementsByClass("alertbox-white").first().text();
                pass.setStatus(result);
                return pass;
            }

            if (!doc.getElementsByTag("h3").isEmpty()) {
                pass.setStatus(doc.getElementsByTag("h3").first().text());
                return pass;
            }
            return pass;
        }catch (IOException e){
            return null;
        }
    }
    public PasswordRecovery passwordRecovery(String cpf){
        try {
            Connection.Response responseData = Jsoup.connect(UrlPasswordData).execute();
            Document doc =  Jsoup.connect(urlPasswordEmail[0]).get();
        }catch (IOException e){}

        return new PasswordRecovery();
    }

}