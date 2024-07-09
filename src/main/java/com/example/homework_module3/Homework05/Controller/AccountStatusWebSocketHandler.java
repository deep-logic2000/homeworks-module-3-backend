//package com.example.homework_module3.Homework05.Controller;
//
//import org.springframework.http.HttpHeaders;
//import org.springframework.stereotype.Component;
//import org.springframework.web.socket.CloseStatus;
//import org.springframework.web.socket.TextMessage;
//import org.springframework.web.socket.WebSocketSession;
//import org.springframework.web.socket.handler.TextWebSocketHandler;
//
//import java.net.InetSocketAddress;
//import java.util.ArrayList;
//import java.util.List;
//
//@Component
//public class AccountStatusWebSocketHandler extends TextWebSocketHandler {
//
//    private final List<WebSocketSession> sessions = new ArrayList<>();
//
//    @Override
//    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
//        sessions.add(session);
//        InetSocketAddress clientAddress = session.getRemoteAddress();
//        HttpHeaders handshakeHeaders = session.getHandshakeHeaders();
//        System.out.printf("Accepted connection from: {}:{}", clientAddress.getHostString(), clientAddress.getPort());
//    }
//
//    @Override
//    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
//        sessions.remove(session);
//    }
//
//    public void sendMessageToAll(String message) throws Exception {
//        for (WebSocketSession session : sessions) {
//            if (session.isOpen()) {
//                session.sendMessage(new TextMessage(message));
//            }
//        }
//    }
//}