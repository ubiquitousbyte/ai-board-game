package org.de.htw.aiforgames.boardgame.game;

import java.util.List;

/**
 * A game identified by a state type S and an action type A
 *
 * @param <S> the state type
 * @param <A> the action type
 */
public interface Game<S, A> {

    /**
     * NOTE: This is effectively the root node of our game tree
     * The initial state that defines how the game is set up at the start
     * @return the initial state
     */
    S startState();

    /**
     * Defines which player has the move in the state
     * @param state the state
     * @return 0 for red, 1 for green, 2 for blue
     */
    int getPlayer(S state);

    /**
     * NOTE: These are the edges in our game tree
     * @param state the state
     * @return the set of legal moves for a state
     */
    List<A> getActions(S state);

    /**
     * A terminal test which is true when the game is over and false otherwise
     * @param state the state to check
     * @return true if the state is a terminal state, false otherwise
     */
    boolean isTerminal(S state);

    /**
     * Transition from one state to another via a given action and return the resulting state
     * @param state the state to transition from
     * @param action the action to transition via
     * @return the state transitioned to
     */
    S transition(S state, A action);

    /**
     * Compute the utility of the state from the current player's viewpoint
     * @param state the terminal state
     * @return a numeric value representing the utility of the current player
     */
    int utility(S state);

    /**
     * Get the next player in the game sequence
     * This function does not consider players that have lost the game
     *
     * @param state the current game state
     * @return the player identifier
     */
    int getNextPlayer(S state);

}
