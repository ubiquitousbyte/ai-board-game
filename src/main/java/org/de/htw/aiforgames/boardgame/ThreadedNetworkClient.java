package org.de.htw.aiforgames.boardgame;

import lenz.htw.blocks.Move;
import lenz.htw.blocks.net.NetworkClient;

import java.awt.image.BufferedImage;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * This is just a simple convenience wrapper around the network client.
 * The client can run in a dedicated thread and therefore not block the main thread.
 */
public class ThreadedNetworkClient implements Runnable {

    private NetworkClient client;
    private final String serverAddress;
    private final String teamName;
    private final BufferedImage icon;

    private final Logger logger;

    public ThreadedNetworkClient(String serverAddress, String teamName, BufferedImage icon) {
        this.serverAddress = serverAddress;
        this.teamName = teamName;
        this.icon = icon;
        this.logger = Logger.getLogger(getClass().getName());
    }

    public void connect() { client = new NetworkClient(this.serverAddress, this.teamName, this.icon); }

    private void play() {
        while (true) {
            Move recv;
            while ((recv = this.client.receiveMove()) != null) {
                logger.log(Level.INFO, "Received move " + recv.toString());
            }
            Move send = new Move(this.client.getMyPlayerNumber(), 2, 3, 4);
        }
    }

    @Override
    public void run() {
        try {
            this.connect();
        }
        catch (Exception e) {
            logger.log(Level.WARNING, e.getMessage());
            return;
        }
        this.play();
    }
}
