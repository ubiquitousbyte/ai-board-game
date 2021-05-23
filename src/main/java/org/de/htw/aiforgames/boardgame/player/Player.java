package org.de.htw.aiforgames.boardgame.player;

import org.de.htw.aiforgames.boardgame.game.Game;
import org.de.htw.aiforgames.boardgame.policies.GamePolicy;

import java.awt.image.BufferedImage;

public abstract class Player<S, A> implements Runnable {

    /**
     * Set the game that the player will play
     * @param game the game to play
     */
    public abstract void setGame(Game<S, A> game);

    /**
     * Set the game policy that the player will use as a strategy
     * @param policy the policy
     */
    public abstract void setGamePolicy(GamePolicy<S, A> policy);

    /**
     * Play the game by applying the game policy
     */
    abstract void play();

    /**
     * Returns a vector that represents this player's understanding/perception of the game.
     * In particular, this returns the coefficients of the features used by the evaluator.
     * Since the coefficients correspond to the priorities that the player assigns to the features, they can be
     * thought of as the player's perception of the game.
     * @return this player's understanding of the game
     */
    public abstract double[] getPerception();

    public abstract String getTeam();

    public abstract BufferedImage getIcon();
}
