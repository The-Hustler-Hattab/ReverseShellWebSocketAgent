package com.mtattab.reverseshell.service.impl;

import com.mtattab.reverseshell.model.ServerRestCommunicationModel;
import com.mtattab.reverseshell.service.WebsocketReverseShellService;
import com.mtattab.reverseshell.util.DataManipulationUtil;
import com.mtattab.reverseshell.util.OSUtil;
import lombok.Getter;
import lombok.Setter;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketAdapter;


import java.net.URI;
@Getter
@Setter

public class WebsocketReverseShellServiceImpl extends WebSocketAdapter implements WebsocketReverseShellService {



    @Override
    public void onWebSocketText(String message) {
        System.out.println("Received message: " + message);
    }

//    @Override
//    public void onWebSocketConnect(Session sess) {
//
//    }

//    @Override
//    public void onOpen(ServerHandshake serverHandshake) {
//        System.out.println(serverHandshake);
//        ServerRestCommunicationModel intialCommunactionMessage=  OSUtil.getComputerInfo();
//        intialCommunactionMessage.setReply("[+] ReverseShell has been established");
//        send(
//                DataManipulationUtil.convertObjToJson(intialCommunactionMessage
//                )
//        );
//
//    }






}
