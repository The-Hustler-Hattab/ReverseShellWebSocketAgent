package com.mtattab.reverseshell.util;

import lombok.Cleanup;
import lombok.experimental.UtilityClass;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@UtilityClass
public class HTTPUtil {

    public static String sendGetRequest(String url) {
        HttpURLConnection connection = null;
        StringBuilder response = new StringBuilder();

        try {
            // Create a URL object
            URL obj = new URL(url);

            // Open a connection to the URL
            connection = (HttpURLConnection) obj.openConnection();
            // Set the request method to GET
            connection.setRequestMethod("GET");

            // Get the response code
            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            // Read the response from the input stream
            @Cleanup
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            // Return the response
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }finally {
            if (connection!=null){
                connection.disconnect();
            }

        }
        return response.toString();

    }

    public static Path downloadFile(String fileUrl, String destinationDirectory) {
        try {
            // Create URL object
            URL url = new URL(fileUrl);


            // Open a connection to the URL
            try (var inputStream = url.openStream()) {
                // Create the destination directory if it doesn't exist
                Files.createDirectories(Path.of(destinationDirectory));

                // Extract the file name from the URL
                String fileName = fileUrl.substring(fileUrl.lastIndexOf('/') + 1);

                // Set the destination file path
                Path destinationPath = Path.of(destinationDirectory, fileName);

                // Copy the input stream to the destination file
                Files.copy(inputStream, destinationPath, StandardCopyOption.REPLACE_EXISTING);

                System.out.println("File downloaded to: " + destinationPath);

                return destinationPath;

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to download the file from " + fileUrl);
            throw new RuntimeException(e);
        }
    }



}
