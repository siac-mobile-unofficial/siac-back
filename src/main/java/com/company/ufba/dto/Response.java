package com.company.ufba.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class Response {
    private Map<String, String> cookies;
    private Map<String, String> headers =
            Map.of("Server", "Apache-Coyote/1.1",
                    "Content-Type", "text/html;charset=ISO-8859-1",
                    "Keep-Alive", "timeout=60",
                    "Content-Encoding", "gzip",
                    "X-Powered-By", "Servlet 2.4; JBoss-4.0.5.GA (build: CVSTag=Branch_4_0 date=200610162339)/Tomcat-5.5"
            );

    public Response(String cookie) {
        this.cookies = Map.of("JSESSIONID", cookie);
    }


}
