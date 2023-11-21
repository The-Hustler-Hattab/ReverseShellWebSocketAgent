package com.mtattab.reverseshell.service.commands;

import com.mtattab.reverseshell.service.Command;
import com.mtattab.reverseshell.util.LockMechanismUtil;


public class TerminateShellCommand implements Command {






    @Override
    public String executeCommand(String command) {
        LockMechanismUtil.stopScheduledTask();

        System.exit(0);

        return "Exiting program";
    }




}



