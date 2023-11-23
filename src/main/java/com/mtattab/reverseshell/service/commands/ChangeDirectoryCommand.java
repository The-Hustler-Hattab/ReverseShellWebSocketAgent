package com.mtattab.reverseshell.service.commands;

import com.mtattab.reverseshell.service.Command;
import com.mtattab.reverseshell.util.DataManipulationUtil;
import com.mtattab.reverseshell.util.SystemCommandUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ChangeDirectoryCommand implements Command {
    @Override
    public String executeCommand(String command) {
        List<String> commands = new ArrayList<>( DataManipulationUtil.stringToList(command, " "));
//        edge cases
        if (commands.get(0).equalsIgnoreCase("cd")){
            commands.remove(0);
            return changeDirectory(String.join(" ", commands)) ;

        }
        return "there is a problem with the command" ;
    }

    public static String changeDirectory(String path) {
        File relativeDirectory = new File(SystemCommandUtil.currentWorkingDir, path);
        File absoluteDirectory = new File(path);


        if (relativeDirectory.exists() && relativeDirectory.isDirectory()) {
            SystemCommandUtil.currentWorkingDir = relativeDirectory;
            return "Directory changed successfully";
        } else if (absoluteDirectory.exists() && absoluteDirectory.isDirectory()) {
            SystemCommandUtil.currentWorkingDir = absoluteDirectory;
            return "Directory changed successfully";
        } else {

            return "Directory not found: " + path;
        }
    }



}
