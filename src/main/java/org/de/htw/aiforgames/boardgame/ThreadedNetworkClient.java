package org.de.htw.aiforgames.boardgame;

import lenz.htw.blocks.Move;
import lenz.htw.blocks.net.NetworkClient;

import java.awt.image.BufferedImage;
import java.util.Arrays;
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


    @Override
    public void run() {
        Game<BoardState, Move> game = new BoardGame();
        GamePolicy<BoardState, Move> gamePolicy = new AlphaBetaPolicy();
        try {
            connect();
        }
        catch (Exception e) {
            logger.log(Level.WARNING, e.getMessage());
            return;
        }
        BoardState currentState = game.startState();
        Move recv;
        while (true) {
            while ((recv = client.receiveMove()) != null) {
                logger.log(Level.INFO, client.getMyPlayerNumber() + " received " + recv);
                currentState = game.transition(currentState, recv);
            }
            currentState.setPlayer(client.getMyPlayerNumber());
            GamePolicy.Decision<Move> decision = gamePolicy.apply(game, currentState, 3);
            if (decision.action == null) {
                client.sendMove(new Move(currentState.getPlayer(), -1, -1, -1));
                break;
            }
            else {
                client.sendMove(decision.action);
            }
        }
    }
}
