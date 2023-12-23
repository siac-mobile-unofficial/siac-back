package com.company.ufba.controller;


import com.company.ufba.dto.Buzufba;
import com.company.ufba.services.ExtraServices;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
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
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {

        Buzufba buzufba = gson.fromJson(message.getPayload().toString(), Buzufba.class);
        System.out.println(buzufba.toString());
        //TODO atualizar dados do cliente
        switch (buzufba.getType()) {
            case BUS->{session.sendMessage(new TextMessage(gson.toJson(services.bus())));}
            case POINT->{session.sendMessage(new TextMessage(gson.toJson(services.pointMaxRanger(buzufba))));}
            default -> {}
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
