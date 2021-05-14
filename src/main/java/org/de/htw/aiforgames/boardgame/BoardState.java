package org.de.htw.aiforgames.boardgame;

import lenz.htw.blocks.Move;

import java.util.List;

public class BoardState {

    private final int player;
    private final int[][] positions;
    private final Board board;

    public BoardState(int player, int[][] positions) {
        this.board = new Board();
        this.player = player;
        this.positions = positions;
    }

    public int getPlayer() { return player; }

    public int getLeftTokenIndex(int player) { return positions[player][0]; }

    public int getRightTokenIndex(int player) { return positions[player][1]; }

    public void setLeftTokenIndex(int player, int index) {
        positions[player][0] = index;
    }

    public void setRightTokenIndex(int player, int index) { positions[player][1] = index; }

    public Triangle[] getLeftTokenNeighbours(int player) {
        int index = getLeftTokenIndex(player);
        return board.getNeighbours(index);
    }

    public Triangle[] getRightTokenNeighbours(int player) {
        int index = getRightTokenIndex(player);
        return board.getNeighbours(index);
    }

    public Triangle[] getNeighbours(int player) {
        Triangle[] result = new Triangle[6];
        Triangle[] leftNeighbours = getLeftTokenNeighbours(player);
        Triangle[] rightNeighbours = getRightTokenNeighbours(player);
        System.arraycopy(leftNeighbours, 0, result, 0, leftNeighbours.length);
        System.arraycopy(rightNeighbours, 0, result, 3, rightNeighbours.length);
        return result;
    }

    public List<Integer> getUnmaskedPositions() { return board.getUnmaskedPositions(); }

    public void mask(Move m) {
        board.mask(m.delete);
        board.mask(m.first);
        board.mask(m.second);
    }
}