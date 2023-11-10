package com.mtattab.reverseshell.commands;

import com.mtattab.reverseshell.service.commands.CameraScreenShotCommand;
import com.mtattab.reverseshell.service.commands.ScreenShotCommand;
import org.junit.Test;

public class CameraScreenShotCommandTest {


    @Test
    public void screenShotTest(){
        CameraScreenShotCommand cameraScreenShotCommand = new CameraScreenShotCommand();

        cameraScreenShotCommand.executeCommand("");


    }

}
