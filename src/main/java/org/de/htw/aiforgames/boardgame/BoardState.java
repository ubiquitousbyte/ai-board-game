package org.de.htw.aiforgames.boardgame;

import java.util.List;

public class BoardState {

    // The current player
    private final int player;
    // A 3x2 array holding the indices of the player's tokens
    private final int[][] positions;
    // The game board
    private final Board board;

    public BoardState(int player, int[][] positions) {
        this.board = new Board();
        this.player = player;
        this.positions = positions;
    }

    /**
     * @return the current player
     */
    public int getPlayer() { return player; }

    /**
     * @param player the player id
     * @return the position of the player's left token
     */
    public int getLeftTokenIndex(int player) { return positions[player][0]; }

    /**
     * @param player the player id
     * @return the position of the player's right token
     */
    public int getRightTokenIndex(int player) { return positions[player][1]; }

    /**
     * @param player the player id
     * @return the triangles that are nearest to the player's left token
     */
    public Triangle[] getLeftTokenNeighbours(int player) {
        int index = getLeftTokenIndex(player);
        return board.getNeighbours(index);
    }

    /**
     * @param player the player id
     * @return the triangles that are nearest to the player's right token
     */
    public Triangle[] getRightTokenNeighbours(int player) {
        int index = getRightTokenIndex(player);
        return board.getNeighbours(index);
    }

    /**
     * @return the indices of the free positions on the board
     */
    public List<Integer> getUnmaskedPositions() { return board.getUnmaskedPositions(); }

}