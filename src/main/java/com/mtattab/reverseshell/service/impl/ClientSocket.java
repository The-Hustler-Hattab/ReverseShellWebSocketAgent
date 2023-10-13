package com.mtattab.reverseshell.service.impl;

import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketAdapter;
import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.net.URI;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.concurrent.Future;

public class ClientSocket {

    public static void main(String[] args) throws NoSuchAlgorithmException, KeyManagementException {
        // Create a custom TrustManager that trusts all certificates
        SslContextFactory.Client ssl = new SslContextFactory.Client(); // ssl config
        ssl.setEndpointIdentificationAlgorithm(null); // disable endpoint identification algorithm.
        ssl.setTrustAll(true);
        WebSocketClient client = new WebSocketClient(ssl); // give ssl config to client




        try {

            client.start();
            URI serverUri = new URI("wss://127.0.0.1:8070/reverseShellClients"); // Replace with your server URL
            WebSocketClientExampleSocket socket = new WebSocketClientExampleSocket();

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



class WebSocketClientExampleSocket extends WebSocketAdapter {
    @Override
    public void onWebSocketText(String message) {
        System.out.println("Received message: " + message);
    }
}
