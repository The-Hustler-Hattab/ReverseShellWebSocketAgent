package com.mtattab.reverseshell.util;

import lombok.experimental.UtilityClass;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * the purpose of this class is to prevent the agent from starting while another agent in the system is active
 */
@UtilityClass
public class LockMechanismUtil {



    public static void startLockMechanism(){

        try {
//            check if the lock file changes within the last 15 seconds
            if (!checkFileChanges( 10, 1)){
                startLock();
            }else {
                System.out.println("lock file is changing. Process is alive. Exiting...");
                System.exit(0);
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void startLock() throws IOException {

        Path lockFilePath = Paths.get(OSUtil.getSystemTmpDir() + Constants.LOCK_FILE_NAME );
        System.out.println(lockFilePath);

        createLockFileIfItDoesntExsits(lockFilePath);

        scheduleFileWrite(lockFilePath);


    }



    private static void createLockFileIfItDoesntExsits(Path path) throws IOException {

        // Check if the file exists
        boolean fileExists = Files.exists(path);


        if (fileExists) {
            System.out.println("The file exists.");
        } else {
            createFile(path);
            System.out.println("The file does not exist.");
        }
    }

    private static void createFile(Path path) throws IOException {
        try {
            Files.createFile(path);
            System.out.println("File created successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("An error occurred while creating the file: " + e.getMessage());
            throw e;
        }

    }


    private static void scheduleFileWrite(Path path){

        // Create a ScheduledExecutorService
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

        // Schedule the task to run every 4 seconds
        executorService.scheduleAtFixedRate(() -> {
            // Generate a random 20-digit number
            long randomNumber = generateRandomNumber();

            // Write the number to the file
            writeNumberToFile(path, Long.toString(randomNumber));
        }, 0, 1, TimeUnit.SECONDS);


    }

    private static long generateRandomNumber() {
        Random random = new Random();
        // Generate a random 20-digit number
        return 1_000_000_000_000_000_000L + random.nextLong() % 9_000_000_000_000_000_000L;
    }

    private static void writeNumberToFile(Path path, String number) {
        try (FileWriter writer = new FileWriter(path.toFile())) {
            // Write the number to the file, overriding existing content
            writer.write(number);
            System.out.println("Number written to file: " + number);
        } catch (IOException e) {
            System.err.println("Error writing to the file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static boolean checkFileChanges( int numChecks, int intervalSeconds) {
        Path path = Paths.get(OSUtil.getSystemTmpDir() + Constants.LOCK_FILE_NAME );

        try {
            // Get the initial hash of the file
            String initialHash = getFileHash(path);

            for (int i = 0; i < numChecks; i++) {

                // Get the current hash of the file
                String currentHash = getFileHash(path);

                if (!initialHash.equals(currentHash)) {
                    return true; // File changed
                }

                // Wait for the specified interval before the next check
                TimeUnit.SECONDS.sleep(intervalSeconds);
            }
        } catch (Exception e) {
            System.err.println("Error checking file changes: " + e.getMessage());
        }

        return false; // File did not change within the specified number of checks
    }

    private static String getFileHash(Path path) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        try (var fileStream = Files.newInputStream(path);
             var digestStream = new DigestInputStream(fileStream, digest)) {
            while (digestStream.read() != -1) {
                // Read the file stream to update the digest
            }
        }
        byte[] hashBytes = digest.digest();
        return bytesToHex(hashBytes);
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02x", b));
        }
        return result.toString();
    }


}
