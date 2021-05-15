package org.de.htw.aiforgames.boardgame;

import org.de.htw.aiforgames.boardgame.utils.ImageReader;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class App {

    public static void main(String[] args) {
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
        for (String teamName: teamNames) {
            ThreadedNetworkClient client = new ThreadedNetworkClient(serverAddress, teamName, icon);
            Thread t = new Thread(client);
            t.start();

        }
    }
}
