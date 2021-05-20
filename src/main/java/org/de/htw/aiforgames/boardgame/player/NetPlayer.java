package org.de.htw.aiforgames.boardgame.player;

import org.de.htw.aiforgames.boardgame.game.Game;
import org.de.htw.aiforgames.boardgame.policies.GamePolicy;

import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class NetPlayer<S, A> extends Player<S, A> {

    protected Game<S, A> game;
    protected GamePolicy<S, A> policy;
    private final Logger logger = Logger.getLogger(getClass().getName());

    /**
     * Set the game that the player will play
     * @param game the game to play
     */
    @Override
    public void setGame(Game<S, A> game) {
        this.game = game;
    }

    /**
     * Set the game policy that the player will use as a strategy
     * @param policy the policy
     */
    @Override
    public void setGamePolicy(GamePolicy<S, A> policy) {
        this.policy = policy;
    }

    /**
     * Connect the network player to the game server
     */
    abstract void connect();

    @Override
    public void run() {
        if (this.game == null) {
            throw new IllegalStateException("The player must have a game to play.");
        }
        if (this.policy == null) {
            throw new IllegalStateException("The player must have a game policy to follow.");
        }
        try {
            connect();
        }
        catch (Exception e) {
            logger.log(Level.WARNING, e.getMessage());
            return;
        }
        play();
    }
}
