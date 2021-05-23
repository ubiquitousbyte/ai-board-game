package org.de.htw.aiforgames.boardgame;

import org.de.htw.aiforgames.boardgame.evolution.DefaultStrategy;
import org.de.htw.aiforgames.boardgame.utils.ImageReader;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class App {

    public static void main(String[] args) throws InterruptedException {
        String serverAddress = "127.0.0.1";
        String[] teamNames = {"Happy Ninjas", "Unhappy Ninjas", "Miserable Ninjas"};
        BufferedImage icon;
        try {
            icon = ImageReader.getInstance().readFromClasspath("favicon.png");
        }
        catch (IOException e) {
            System.err.println(e.getMessage());
            return;
        }

        List<double[]> firstGeneration = new ArrayList<>(3);
        firstGeneration.add(DefaultStrategy.randomCoefficients(15));
        firstGeneration.add(DefaultStrategy.randomCoefficients(15));
        firstGeneration.add(DefaultStrategy.randomCoefficients(15));

        DefaultStrategy world = new DefaultStrategy(serverAddress, icon, firstGeneration);
        Thread t = new Thread(world);
        t.start();
    }
}
