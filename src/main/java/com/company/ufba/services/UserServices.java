package com.company.ufba.services;

import com.company.ufba.dto.*;
import org.jsoup.Jsoup;
import org.jsoup.helper.ValidationException;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.CookieManager;
import java.net.HttpCookie;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class UserServices {
    final String urlNotes ="https://siac.ufba.br/SiacWWW/ConsultarCoeficienteRendimento.do";
    final String urlInfo ="https://siac.ufba.br/SiacWWW/ConsultarComprovanteMatricula.do";
    final String urlCurriculumOB = "https://siac.ufba.br/SiacWWW/ConsultarDisciplinasObrigatorias.do";
    final String getUrlCurriculumOP = "https://siac.ufba.br/SiacWWW/ConsultarDisciplinasOptativas.do";
    final String urlPDF = "https://siac.ufba.br/SiacWWW/GerarComprovanteMatricula.do";
    final String local ="PDF/";
    private Document doc;

    public List<?> ListOfData(Elements element){
        AtomicReference<String> aux = new AtomicReference<>();
        AtomicReference<List<String>> auxM = new AtomicReference<>();
        return element.stream()
                .filter(item-> !item.text().isEmpty())
                .map(item->item.select("td").eachText())
                .filter(item->!item.get(1).equals("Trancamento de Matrícula Especial"))
                .filter(item-> !item.get(item.size()-1).equals("TR"))
                .map(item->{
                    item.removeIf(String::isEmpty);
                    item.removeIf("--"::equals);
                    try {
                        Integer.parseInt(String.valueOf(item.get(0).charAt(0)));
                        aux.set(item.get(0));
                    } catch (NumberFormatException e) {
                      item.add(0, aux.get());
                      if(item.removeIf(Objects::isNull)){
                            if (item.size()==5){
                                item.addAll(0,auxM.get());
                            }else{
                                auxM.set(List.of(item.get(0),item.get(1),item.get(2),item.get(3)));
                            }
                      }
                    }
                    return item;
                })
                .map(item->{
                    switch (item.size()){
                        case 9:
                            return new Classroom(item);
                        case 5:
                            case 4:
                            return new Curriculum(item);
                        case 7:
                            return new Matter(item);
                        default:
                            Logger.getLogger("ListOfData: ").info("No Identify: "+item);
                            return "No Identify";
                }
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
           String[] date = doc.getElementsByTag("tbody")
                    .get(4).select("td").get(0).text().split("-");
            var element = doc.getElementsByTag("tbody").get(4).select("td");
             List<String> user =  element.stream()
                            .map(item->item.ownText())
                            .filter(s -> !s.isEmpty())
                            .collect(Collectors.toList());

                return new UserData(user.get(0),user.get(1),user.get(2),user.get(3),user.get(4),date[1].trim());
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
    public List<Classroom> findClassroom(Response response){
        try {
            doc = Jsoup.connect(urlInfo)
                    .newRequest()
                    .cookies(response.getCookies())
                    .headers(response.getHeaders())
                    .get();

            var element = doc.getElementsByClass("simple2").get(0).select("tbody").select("tr");
            element.remove(element.last());
          return (List<Classroom>) ListOfData(element);
        } catch (IOException e) {
            return null;
        }

    }

    public Resource findPDF(Response response) {
        String finalLocal = local+response.getCookies().get("JSESSIONID")+ "_file.pdf";
        response.getHeaders().remove("Connection");
        response.getHeaders().remove("Transfer-Encoding");
        try {
            HttpCookie cookie = new HttpCookie("JSESSIONID",response.getCookies().get("JSESSIONID"));
            cookie.setPath("/");
            cookie.setVersion(1);
            CookieManager CM = new CookieManager();
            CM.getCookieStore().add(new URI("https://siac.ufba.br/"),cookie);
            HttpClient client = HttpClient.newBuilder().cookieHandler(CM).build();
            HttpRequest.Builder request = HttpRequest.newBuilder(new URI(urlPDF));
            response.getHeaders().forEach(request::setHeader);
            HttpRequest req = request.build();
            HttpResponse res = client.send(req, HttpResponse.BodyHandlers.ofFile(Paths.get(finalLocal)));
        if (res.statusCode() == HttpStatus.REQUEST_TIMEOUT.value()){
            Files.deleteIfExists(Path.of(finalLocal));
            Logger.getLogger("DELET").info("DELETADO");
        };
        if (res.statusCode() == HttpStatus.OK.value()){return new UrlResource(Path.of(finalLocal).toUri());
        }
        }catch (IOException | InterruptedException | URISyntaxException e){
            Logger.getLogger("PDF").info(e.getMessage());
        }
    return null;
    }
}
