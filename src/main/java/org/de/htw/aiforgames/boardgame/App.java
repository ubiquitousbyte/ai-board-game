package org.de.htw.aiforgames.boardgame;

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
        List<Thread> threads = new ArrayList<>();
        for (String teamName: teamNames) {
            ThreadedNetworkClient client = new ThreadedNetworkClient(serverAddress, teamName, icon);
            Thread t = new Thread(client);
            t.start();
            threads.add(t);
        }

        for (Thread t : threads) {
            t.join();
        }

    }
}
