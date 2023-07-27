package com.company.ufba.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.jsoup.Connection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Service
public class TokenService {

    private String secret = "segredo de estado";

    public String generateToken(Connection.Response userResponse) throws JWTCreationException, UnknownHostException {
        final Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.create()
                .withIssuer("Auth-api: "+InetAddress.getLocalHost().getHostName())
                .withSubject(userResponse.headers().toString())
                .withExpiresAt(LocalDateTime.now()
                        .plusHours(1)
                        .toInstant(ZonedDateTime.now().getOffset()))
                .sign(algorithm);
    }
    public String validateToken(String token) throws JWTVerificationException,UnknownHostException {
        final Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.require(algorithm).withIssuer("Auth-api: "+InetAddress.getLocalHost().getHostName())
                .build().verify(token).getSubject();
    }
}
