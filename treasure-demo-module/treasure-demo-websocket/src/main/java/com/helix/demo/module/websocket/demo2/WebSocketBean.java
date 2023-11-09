package com.helix.demo.module.websocket.demo2;

import org.springframework.web.socket.WebSocketSession;

public class WebSocketBean {

    private WebSocketSession webSocketSession;
    private int clientId;

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }
    
    public WebSocketSession getWebSocketSession() {
        return webSocketSession;
    }

    public void setWebSocketSession(WebSocketSession webSocketSession) {
        this.webSocketSession = webSocketSession;
    }
}