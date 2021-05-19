package org.de.htw.aiforgames.boardgame;

import lenz.htw.blocks.Move;

import java.util.ArrayList;
import java.util.List;

public class BoardGame implements Game<BoardState, Move> {

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

    @Override
    public int getPlayer(BoardState state) { return state.getPlayer(); }

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

    @Override
    public BoardState transition(BoardState state, Move action) {
        Move m = new Move(action.player, action.delete, action.first, action.second);
        if (m.first == 255) {
            m.first = state.getLeftTokenIndex(getPlayer(state));
        }
        if (m.second == 255) {
            m.second = state.getRightTokenIndex(getPlayer(state));
        }
        if (m.first > m.second) {
            int tmp = m.first;
            m.first = m.second;
            m.second = tmp;
        }
        BoardState newState = new BoardState(state);
        int player = getPlayer(newState);
        newState.movePlayer(player, m);
        newState.setPlayer(getNextPlayer(newState));
        return newState;
    }

    @Override
    public int[] utility(BoardState state) {
        int[] result = new int[3];
        for(int i = 0; i < result.length; i++) {
            result[i] = state.getPlayerPoints(i)[i];
        }
        return result;
    }

    public int getNextPlayer(BoardState state) {
        int player = getPlayer(state);
        if (player == 0) return 1;
        if (player == 1) return 2;
        return 0;
    }
}
