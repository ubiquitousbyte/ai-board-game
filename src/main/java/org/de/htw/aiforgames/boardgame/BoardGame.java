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
        return new BoardState(currentPlayer, positions);
    }

    @Override
    public int getPlayer(BoardState state) { return state.getPlayer(); }

    @Override
    public List<Move> getActions(BoardState state) {
        int player = getPlayer(state);
        List<Integer> unmaskedPositions = state.getUnmaskedPositions();
        List<Move> actions = new ArrayList<>();

        Triangle[] leftN = state.getLeftTokenNeighbours(player);
        for (Triangle left : leftN) {
            if (left == null || left.masked()) {
                continue;
            }
            int rightTokenIndex = state.getRightTokenIndex(player);
            for(Integer deletePosition : unmaskedPositions) {
                Move m = new Move(player, deletePosition, left.getId(), rightTokenIndex);
                actions.add(m);
            }
        }

        Triangle[] rightN = state.getRightTokenNeighbours(player);
        for (Triangle right : rightN) {
            if (right == null || right.masked()) {
                continue;
            }
            int leftTokenIndex = state.getLeftTokenIndex(player);
            for (Integer deletePosition : unmaskedPositions) {
                Move m = new Move(player, deletePosition, leftTokenIndex, right.getId());
                actions.add(m);
            }
        }
        return actions;
    }

    @Override
    public boolean isTerminal(BoardState state) {
        return state.getUnmaskedPositions().isEmpty();
    }

    @Override
    public BoardState transition(BoardState state, Move action) {

        return null;
    }

    @Override
    public int utility(BoardState state, int player) {
        return 0;
    }
}
