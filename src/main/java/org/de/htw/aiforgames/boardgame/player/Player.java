package org.de.htw.aiforgames.boardgame.player;

import org.de.htw.aiforgames.boardgame.game.Game;
import org.de.htw.aiforgames.boardgame.policies.GamePolicy;

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
}
