package com.company.ufba.services;

import com.company.ufba.dto.*;
import com.company.ufba.utils.Exception.custom.RegisterException;
import com.company.ufba.utils.tools.Delete;
import com.company.ufba.utils.tools.Roles;
import org.jsoup.Jsoup;
import org.jsoup.helper.ValidationException;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.File;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Logger;
import java.util.stream.Collectors;
@Service
public class UserServices {
    Logger log = Logger.getLogger("DEV_USER_SERVICES");
    final String urlNotes ="https://siac.ufba.br/SiacWWW/ConsultarCoeficienteRendimento.do";
    final String urlInfo ="https://siac.ufba.br/SiacWWW/ConsultarComprovanteMatricula.do";
    final String urlCurriculumOB = "https://siac.ufba.br/SiacWWW/ConsultarDisciplinasObrigatorias.do";
    final String getUrlCurriculumOP = "https://siac.ufba.br/SiacWWW/ConsultarDisciplinasOptativas.do";
    final String urlPDF = "https://siac.ufba.br/SiacWWW/GerarComprovanteMatricula.do";
    final String urlNotePDF = "https://siac.ufba.br/SiacWWW/ImprimirHistoricoEscolar.do";
    final String urlHistorySchool = "https://siac.ufba.br/SiacWWW/GerarHistorico.do";
    final Map<String,String> urlLockRegistration =  Map.of("lock","https://siac.ufba.br/SiacWWW/SolicitarTrancamentoMatricula.do"
    ,"positionLock","https://siac.ufba.br/SiacWWW/VerificarPosicaoTrancamento.do");
    final String urlSIAC = "https://siac.ufba.br/";
    final  static String local ="PDF/";
    private Document doc;

    static {
        if (!new File(local).exists()) new File(local).mkdir();
    }
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
                            return "No Identify";
                }
                })
           .collect(Collectors.toList());
    }/*metodo só funciona com as tag tr, pois consome as linhas da tabela*/
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
        System.out.println(response.getCookies().toString());
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
                Logger.getLogger("Teste").info(user.toString());
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
    public List<Classroom> findClassroom(Response response)  {
        try {
            doc = Jsoup.connect(urlInfo)
                    .newRequest()
                    .cookies(response.getCookies())
                    .headers(response.getHeaders())
                    .get();
                var element = doc.getElementsByClass("simple2").get(0).select("tbody").select("tr");
                element.remove(element.last());
                return (List<Classroom>) ListOfData(element);
            }catch (IndexOutOfBoundsException | IOException e){
            throw new RegisterException("Aluno sem inscrição semestral nos componentes curriculares.");
            }


    }

    public Resource findPDF(Response response) {
        try {
            HttpCookie cookie = new HttpCookie("JSESSIONID",response.getCookies().get("JSESSIONID"));
            cookie.setPath("/");
            cookie.setVersion(1);
            CookieManager CM = new CookieManager();
            CM.getCookieStore().add(new URI("https://siac.ufba.br/"),cookie);
            HttpClient client = HttpClient.newBuilder().cookieHandler(CM).build();
            HttpRequest.Builder request = HttpRequest.newBuilder(new URI(selectPDF(response.getRole())));
            response.getHeaders().forEach(request::setHeader);
            HttpRequest req = request.build();
            HttpResponse<byte[]> res = client.send(req, HttpResponse.BodyHandlers.ofByteArray());
        if (res.statusCode() == HttpStatus.OK.value())  return new ByteArrayResource( res.body());
        }catch (IOException | InterruptedException | URISyntaxException e){
            Logger.getLogger("PDF").info(e.getMessage());
        }
    return null;
    }
    public Resource findPDFNotes(){

        return null;
    }
//    public Resource findPDFHistory(Response response){
//
//        CookieManager CM = new CookieManager();
//        HttpCookie cookie = new HttpCookie("JSESSIONID",response.getCookies().get("JSESSIONID"));
//        cookie.setPath("/");
//        cookie.setVersion(1);
//        try {
//            CM.getCookieStore().add(new URI(urlSIAC),cookie);
//        HttpClient client = HttpClient.newBuilder().cookieHandler(CM).build();
//        HttpRequest.Builder request = HttpRequest.newBuilder(new URI(urlHistorySchool));
//        response.getHeaders().forEach(request::setHeader);
//        HttpRequest req = request.build();
//        HttpResponse<byte[]> res = client.send(req, HttpResponse.BodyHandlers.ofByteArray());
//        log.info(String.valueOf(res.statusCode()));
//        if (res.statusCode() == HttpStatus.OK.value()) return new ByteArrayResource(res.body());
//
//        return null;
//        }catch (URISyntaxException | IOException | InterruptedException e){}
//        return null;
//    }
    public List<?> findLockRegister(Response response){
        try {
            doc = Jsoup.connect(urlLockRegistration.get("lock"))
                    .cookies(response.getCookies())
                    .headers(response.getHeaders()).get();
            System.out.println(doc);
            return null;
        }catch (IOException e){
            return null;
        }

    }
    public String selectPDF(Roles role){
        switch (role){
            case HISTORICO -> {
                return urlHistorySchool;
            }
            case MATRICULA -> {
                return urlPDF;
            }
            default -> {
                return null;//adicionar pdf padrão
            }
        }
    }
}
