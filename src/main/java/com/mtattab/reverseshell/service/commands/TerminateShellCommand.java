package com.mtattab.reverseshell.service.commands;

import com.mtattab.reverseshell.service.Command;






public class TerminateShellCommand implements Command {






    @Override
    public String executeCommand(String command) {
        System.exit(0);
        return "Exiting program";
    }




}



