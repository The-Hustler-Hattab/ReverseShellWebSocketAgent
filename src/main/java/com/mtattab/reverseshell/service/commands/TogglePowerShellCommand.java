package com.mtattab.reverseshell.service.commands;

import com.mtattab.reverseshell.model.CommandRestOutput;
import com.mtattab.reverseshell.service.Command;
import com.mtattab.reverseshell.util.SystemCommandProxyUtil;

public class TogglePowerShellCommand implements Command {
    @Override
    public CommandRestOutput executeCommand(String command) {
        if (SystemCommandProxyUtil.isWindows && togglePowerShell(command) != null) {
            return CommandRestOutput.builder().output("Switched to powershell successfuly")
                    .build() ;


        }
        return null;
    }

    private static String togglePowerShell(String commands){
        if (commands.toLowerCase().contains("powershell")){
            SystemCommandProxyUtil.runPowerShell= !SystemCommandProxyUtil.runPowerShell;
            return "Switched to Powershell mode";

        }
        return null;
    }
}
