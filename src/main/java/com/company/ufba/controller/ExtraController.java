package com.company.ufba.controller;


import com.company.ufba.repositories.PointRepository;
import com.company.ufba.services.ExtraServices;
import com.google.gson.Gson;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.rsocket.annotation.ConnectMapping;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.*;

import java.util.logging.Logger;
@Component
public class ExtraController implements WebSocketHandler {
    Logger log = Logger.getLogger("WEBSOCKET");
    Gson gson = new Gson();
    @Autowired
    ExtraServices services;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info(session.getId());
        session.sendMessage(new TextMessage(gson.toJson(services.point())));
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        //TODO atualizar dados do cliente
        switch (message.getPayload().toString()) {
            case "BUS"->{session.sendMessage(new TextMessage(gson.toJson(services.bus())));}
            case "POINT"->{session.sendMessage(new TextMessage(gson.toJson(services.point())));}
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {

    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
