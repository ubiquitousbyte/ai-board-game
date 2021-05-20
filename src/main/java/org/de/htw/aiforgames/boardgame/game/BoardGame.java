package org.de.htw.aiforgames.boardgame.game;

import lenz.htw.blocks.Move;

import java.util.ArrayList;
import java.util.List;

public class BoardGame implements Game<BoardState, Move> {

    /**
     * NOTE: This is effectively the root node of our game tree
     * The initial state that defines how the game is set up at the start
     * @return the initial state
     */
    @Override
    public BoardState startState() {
        // The initial player is the red player
        int currentPlayer = 0;
        // The initial positions are hard-coded.
        int[][] positions = new int[][]{
                {8, 15},
                {4, 9},
                {29, 31}
        };
        BoardState state = new BoardState(currentPlayer, positions);
        state.maskPositions();
        return state;
    }

    /**
     * Defines which player has the move in the state
     * @param state the state
     * @return 0 for red, 1 for green, 2 for blue
     */
    @Override
    public int getPlayer(BoardState state) { return state.getPlayer(); }

    /**
     * NOTE: These are the edges in our game tree
     * @param state the state
     * @return the set of legal moves for a state
     */
    @Override
    public List<Move> getActions(BoardState state) {
        int player = getPlayer(state);
        List<Integer> unmaskedPositions = state.getUnmaskedPositions();
        List<Move> actions = new ArrayList<>();

        Triangle[] leftN = state.getLeftTokenNeighbours(player);
        int rightTokenIndex = state.getRightTokenIndex(player);
        for (Triangle left : leftN) {
            if (left == null || left.masked()) {
                continue;
            }
            for(Integer deletePosition : unmaskedPositions) {
                if (deletePosition != left.getId() && deletePosition != rightTokenIndex) {
                    Move m = new Move(player, deletePosition, left.getId(), rightTokenIndex);
                    actions.add(m);
                }
            }
        }

        Triangle[] rightN = state.getRightTokenNeighbours(player);
        int leftTokenIndex = state.getLeftTokenIndex(player);
        for (Triangle right : rightN) {
            if (right == null || right.masked()) {
                continue;
            }
            for (Integer deletePosition : unmaskedPositions) {
                if (deletePosition != right.getId() && deletePosition != leftTokenIndex) {
                    Move m = new Move(player, deletePosition, leftTokenIndex, right.getId());
                    actions.add(m);
                }
            }
        }
        return actions;
    }

    /**
     * A terminal test which is true when the game is over and false otherwise
     * @param state the state to check
     * @return true if the state is a terminal state, false otherwise
     */
    @Override
    public boolean isTerminal(BoardState state) {
        int player = getPlayer(state);
        for (Triangle t : state.getLeftTokenNeighbours(player)) {
            if (t != null) {
                if (! t.masked()) {
                    return false;
                }
            }
        }
        for(Triangle t : state.getRightTokenNeighbours(player)) {
            if (t != null) {
                if (! t.masked()) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Transition from one state to another via a given action and return the resulting state
     * @param state the state to transition from
     * @param action the action to transition via
     * @return the state transitioned to
     */
    @Override
    public BoardState transition(BoardState state, Move action) {
        Move m = new Move(action.player, action.delete, action.first, action.second);
        if (m.first == 255) {
            m.first = state.getLeftTokenIndex(action.player);
        }
        if (m.second == 255) {
            m.second = state.getRightTokenIndex(action.player);
        }
        if (m.first > m.second) {
            int tmp = m.first;
            m.first = m.second;
            m.second = tmp;
        }
        BoardState newState = new BoardState(state);
        newState.setPlayer(action.player);
        newState.movePlayer(m);
        return newState;
    }

    /**
     * Compute the utility of the state from the current player's viewpoint
     * @param state the terminal state
     * @return a numeric value representing the utility of the current player
     */
    @Override
    public int utility(BoardState state) { return state.getPlayerPoints(getPlayer(state)); }

    /**
     * Get the next player in the game sequence
     * This function does not consider players that have lost the game
     *
     * @param state the current game state
     * @return the player identifier
     */
    @Override
    public int getNextPlayer(BoardState state) {
        int player = getPlayer(state);
        if (player == 0) return 1;
        else if (player == 1) return 2;
        else return 0;
    }
}
