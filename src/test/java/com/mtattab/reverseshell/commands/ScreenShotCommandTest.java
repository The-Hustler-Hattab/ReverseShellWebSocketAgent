package com.mtattab.reverseshell.commands;

import com.mtattab.reverseshell.service.commands.ScreenShotCommand;
import org.junit.Test;

public class ScreenShotCommandTest {

    @Test
    public void screenShotTest(){
        ScreenShotCommand.captureMultiMonitorScreenshot("screenshot.png");


    }
}
