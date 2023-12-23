package com.company.ufba.services;

import com.company.ufba.dto.PasswordRecovery;
import com.company.ufba.dto.User;

import com.company.ufba.utils.Exception.custom.LoginException;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.helper.ValidationException;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Service
public class AuthorizationServices {
    private Logger DEV = Logger.getLogger("DEV");
    private final String urlData = "https://siac.ufba.br/SiacWWW/Welcome.do";
    private final String urlLogin = "https://siac.ufba.br/SiacWWW/LogonSubmit.do";
    private final String urlPassword = "https://sius.ufba.br/sius/AlterarSenhaSistemaWebPublico/Alterar.do";
    private final String[] urlPasswordEmail = {};
    private final String UrlPasswordData = "https://sius.ufba.br/sius/Welcome.do?action=senha&result=siac";

    public Map<String, String> login(User user) {
        try {
            Connection.Response responseData = Jsoup.connect(urlData).execute();
            Connection.Response responseLogin = Jsoup.connect(urlLogin).newRequest().timeout(3000).cookies(responseData.cookies()).data(user.getLoginInfo()).execute().method(Connection.Method.POST);
            Document doc = responseLogin.parse();
            if (!doc.forms().isEmpty())
                throw new LoginException(doc.forms().get(0).getElementsByTag("div").first().text());
            return responseLogin.headers();
        } catch (IOException | ValidationException e) {
            Logger.getLogger("Login").info("Login error: " + e.getMessage());
            return null;
        }

    }

    public PasswordRecovery passwordRecovery(PasswordRecovery pass) {
        String result;
        try {
            Connection.Response responseData = Jsoup.connect(UrlPasswordData).execute();
            Document doc = Jsoup.connect(urlPassword).newRequest().header("Origin", "https://sius.ufba.br").cookies(responseData.cookies()).data(new HashMap<>(pass.allData())).post();
            //System.out.println(doc);

            if (!doc.getElementsByClass("alertbox-white").isEmpty()) {
                result = doc.getElementsByClass("alertbox-white").first().text();
                pass
                        .setStatus(result);
                return pass;
            }

            if (!doc.getElementsByTag("h3").isEmpty()) {
                pass.setStatus(doc.getElementsByTag("h3").first().text());
                return pass;
            }
            return pass;
        } catch (IOException e) {
            return null;
        }
    }

    public PasswordRecovery passwordRecovery(String cpf) {
        try {
            Connection.Response responseData = Jsoup.connect(UrlPasswordData).execute();
            Document doc = Jsoup.connect(urlPasswordEmail[0]).get();
        } catch (IOException e) {
        }

        return new PasswordRecovery();
    }

}