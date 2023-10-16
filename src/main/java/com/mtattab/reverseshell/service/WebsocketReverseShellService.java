package com.mtattab.reverseshell.service;

import org.eclipse.jetty.websocket.client.WebSocketClient;

public interface WebsocketReverseShellService {

    public void startReverseShell();
    public WebSocketClient createClient();
}
