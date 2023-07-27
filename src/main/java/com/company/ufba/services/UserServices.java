package com.company.ufba.services;

import com.company.ufba.dto.Response;
import org.jsoup.Jsoup;
import org.jsoup.helper.ValidationException;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Service
public class UserServices {
    final String urlNotes ="https://siac.ufba.br/SiacWWW/LogonSubmit.do";
    public List<?> NotesForStudent(Response response){
        try {
            Document doc = Jsoup.connect(urlNotes)
                    .newRequest()
                    .cookies(response.getCookies())
                    .headers(response.getHeaders())
                    .get();
            System.out.println(doc);
            return List.of();
        }catch (IOException | ValidationException e){
            Logger.getLogger("User").info(e.getMessage());
            return null;
        }


    }

}
