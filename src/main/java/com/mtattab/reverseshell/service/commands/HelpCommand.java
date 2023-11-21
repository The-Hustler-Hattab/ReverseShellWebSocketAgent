package com.mtattab.reverseshell.service.commands;

import com.mtattab.reverseshell.service.Command;
import com.mtattab.reverseshell.util.DataManipulationUtil;
import com.mtattab.reverseshell.util.SystemCommandProxyUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HelpCommand implements Command {

    private String commandName;
    private String description;
    private String requiredParams;
    private String example;




    @Override
    public String executeCommand(String command) {

        return help();
    }

    private String help() {
        ArrayList<HelpCommand> helpCommandArrayList = new ArrayList<>();

        helpCommandArrayList.add(HelpCommand.builder().commandName("upload")
                .description("upload files to s3 bucket")
                .requiredParams("filePath")
                .example("upload <file>")
                .build());
        helpCommandArrayList.add(HelpCommand.builder().commandName("rick-roll")
                .description("play rick roll music")
                .requiredParams("NA")
                .example("rick-roll")
                .build());
        helpCommandArrayList.add(HelpCommand.builder().commandName("screenshot")
                .description("take screen shoot of the screens")
                .requiredParams("NA")
                .example("screenshot")
                .build());
        helpCommandArrayList.add(HelpCommand.builder().commandName("camera-shot")
                .description("take camera shoot of all available camera")
                .requiredParams("NA")
                .example("camera-shot")
                .build());
        helpCommandArrayList.add(HelpCommand.builder().commandName("/update-malware")
                .description("pull the latest released agent from jfrog and attempts to establish persistence with the new agent")
                .requiredParams("NA")
                .example("/update-malware")
                .build());
        helpCommandArrayList.add(HelpCommand.builder().commandName("/tcp-connect")
                .description("connect to target machine using tcp unsecured socket")
                .requiredParams("ip and port ")
                .example("/tcp-connect 127.0.0.1 443")
                .build());
        helpCommandArrayList.add(HelpCommand.builder().commandName("powershell")
                .description("switch to powershell mode [Windows-only]")
                .requiredParams("NA")
                .example("powershell")
                .build());
        helpCommandArrayList.add(HelpCommand.builder().commandName("/exit")
                .description("Terminate shell")
                .requiredParams("NA")
                .example("/exit")
                .build());

        return helpCommandArrayList.toString();

    }

    @Override
    public String toString() {
        return "\n{" +
                "commandName= " + commandName + "\n" +
                ", description= " + description + "\n" +
                ", requiredParams= " + requiredParams + "\n" +
                ", example= " + example + "\n" +
                '}';
    }
}



