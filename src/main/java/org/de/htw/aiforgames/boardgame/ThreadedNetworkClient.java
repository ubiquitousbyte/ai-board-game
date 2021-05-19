package org.de.htw.aiforgames.boardgame;

import lenz.htw.blocks.Move;
import lenz.htw.blocks.net.NetworkClient;

import java.awt.image.BufferedImage;

import java.util.logging.Logger;
import java.util.logging.Level;

import static java.lang.Thread.sleep;

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
        GamePolicy<BoardState, Move> gamePolicy = new MinimaxPolicy();
        try {
            this.connect();
        }
        catch (Exception e) {
            logger.log(Level.WARNING, e.getMessage());
            return;
        }
        BoardState currentState = game.startState();
        Move recv;
        while (! game.isTerminal(currentState)) {
            while ((recv = client.receiveMove()) != null) {
                currentState = game.transition(currentState, recv);
            }
            GamePolicy.Decision<Move> decision = gamePolicy.apply(game, currentState, 3);
            Move move = decision.action;
            if (move == null) {
                int statePlayer = currentState.getPlayer();
                int threadPlayer = client.getMyPlayerNumber();
                if (threadPlayer == statePlayer) {
                    break;
                }
                else {
                    int nextPlayer = game.getNextPlayer(currentState);
                    currentState.setPlayer(nextPlayer);
                }
            }
            else {
                client.sendMove(move);
            }
        }
        Move testament = new Move(currentState.getPlayer(), -1, -1, -1);
        client.sendMove(testament);
    }
}
