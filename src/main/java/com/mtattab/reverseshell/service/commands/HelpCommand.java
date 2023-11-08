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
                        .example("upload file.txt")
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
        helpCommandArrayList.add(HelpCommand.builder().commandName("powershell")
                .description("switch to powershell mode [Windows-only]")
                .requiredParams("NA")
                .example("powershell")
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


