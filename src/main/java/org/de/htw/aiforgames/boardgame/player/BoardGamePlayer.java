package org.de.htw.aiforgames.boardgame.player;

import lenz.htw.blocks.Move;
import lenz.htw.blocks.net.NetworkClient;
import org.de.htw.aiforgames.boardgame.game.BoardState;
import org.de.htw.aiforgames.boardgame.policies.GamePolicy;

import java.awt.image.BufferedImage;

public class BoardGamePlayer extends NetPlayer<BoardState, Move> {

    private final String serverAddress;
    private final String teamName;
    private final BufferedImage icon;
    private NetworkClient client;

    public BoardGamePlayer(String server, String team, BufferedImage image) {
        this.serverAddress = server;
        this.teamName = team;
        this.icon = image;
    }

    /**
     * Connect the network player to the game server
     */
    @Override
    void connect() { client = new NetworkClient(serverAddress, teamName, icon); }

    @Override
    void play() {
        BoardState currentState = game.startState();
        Move recv;
        while (! game.isTerminal(currentState)) {
            while ((recv = client.receiveMove()) != null) {
                currentState = game.transition(currentState, recv);
            }
            currentState.setPlayer(client.getMyPlayerNumber());
            GamePolicy.Decision<Move> decision = policy.apply(game, currentState, 3);
            if (decision.getAction() == null) {
                client.sendMove(new Move(currentState.getPlayer(), -1, -1, -1));
                break;
            }
            else {
                client.sendMove(decision.getAction());
            }
        }
    }

    @Override
    public String getTeam() {
        return teamName;
    }

    @Override
    public BufferedImage getIcon() {
        return icon;
    }
}
