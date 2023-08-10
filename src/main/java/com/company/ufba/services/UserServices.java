package com.company.ufba.services;

import com.company.ufba.dto.Curriculum;
import com.company.ufba.dto.Matter;
import com.company.ufba.dto.Response;
import com.company.ufba.dto.UserData;
import org.jsoup.Jsoup;
import org.jsoup.helper.ValidationException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class UserServices {
    final String urlNotes ="https://siac.ufba.br/SiacWWW/ConsultarCoeficienteRendimento.do";
    final String urlInfo ="https://siac.ufba.br/SiacWWW/ConsultarComprovanteMatricula.do";
    final String urlCurriculumOB = "https://siac.ufba.br/SiacWWW/ConsultarDisciplinasObrigatorias.do";
    final String getUrlCurriculumOP = "https://siac.ufba.br/SiacWWW/ConsultarDisciplinasOptativas.do";
    private Document doc;

    public List<?> ListOfData(Elements element){
        AtomicReference<String> aux = new AtomicReference<>();
        return element.stream()
                .filter(item-> !item.text().isEmpty())
                .map(item->item.select("td").eachText())
                .map(item->{
                    item.removeIf(s ->  s.isEmpty());
                    try {
                        int firstCharValue = Integer.parseInt(String.valueOf(item.get(0).charAt(0)));
                        aux.set(item.get(0));
                    } catch (NumberFormatException e) {
                      item.add(0, aux.get());
                    }
                    return item;
                })
                .filter(item->!item.get(1).equals("Trancamento de Matrícula Especial"))
                .map(item->{if(item.get(1).startsWith("O")){return new Curriculum(item);}
                            else{return new Matter(item);}
                })
                .collect(Collectors.toList());
    }/*metodo só funciona com as tag tr, pois consome as linhas da tabeka*/
    public List<Matter> NotesForStudent(Response response){
        try {
             doc = Jsoup.connect(urlNotes)
                    .newRequest()
                    .cookies(response.getCookies())
                    .headers(response.getHeaders())
                    .get();
                var element = doc.getElementsByTag("tbody").get(5).select("tr");
                element.remove(element.last());
                return (List<Matter>)ListOfData(element);


        }catch (IOException | ValidationException | NullPointerException e){
            Logger.getLogger("User").info(e.getMessage());
            return null;
        }


    }
    public UserData findUser(Response   response) {
        try {
            doc = Jsoup.connect(urlInfo)
                    .newRequest()
                    .cookies(response.getCookies())
                    .headers(response.getHeaders())
                    .get();

            var element = doc.getElementsByTag("tbody").get(4).select("td");

         List<String> user =  element.stream()
                        .map(item->item.ownText())
                        .filter(s -> !s.isEmpty())
                        .collect(Collectors.toList());
            return new UserData(user.get(0),user.get(1),user.get(2),user.get(3),user.get(4));
        }catch (IOException e){
            Logger.getLogger("User").info(e.getMessage());
            return null;
        }

    }
    public List<Curriculum> findCurriculum(Response response){
        try {
            doc = Jsoup.connect(urlCurriculumOB)
                    .newRequest()
                    .cookies(response.getCookies())
                    .headers(response.getHeaders())
                    .get();
            var element = doc.getElementsByClass("simple").select("tbody").select("tr");
            var value = new AtomicReference<String>();
        return (List<Curriculum>)ListOfData(element);
        }catch (IOException e){
            Logger.getLogger("Curriculum").info("Error: "+e.getMessage());
            return null;
        }


    }

}
