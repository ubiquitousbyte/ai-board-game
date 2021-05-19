package org.de.htw.aiforgames.boardgame;

import lenz.htw.blocks.Move;

import java.util.List;

public class BoardState {

    // The current player
    private int player;
    // A 3x2 array holding the indices of the player's tokens
    private final int[][] positions;
    // A 3x3 array holding the point vectors from the perspective of each player
    private final int[] points;
    // The game board
    private final Board board;

    public BoardState(int player, int[][] positions) {
        this.board = new Board();
        this.player = player;
        this.positions = positions;
        this.points = new int[3];
    }

    public BoardState(BoardState state) {
        this.player = state.player;
        this.positions = state.getPlayerPositions();
        this.points = state.getPoints();
        this.board = new Board(state.board);
    }

    /**
     * Masks all player positions
     */
    public void maskPositions() {
        for (int[] position : positions) {
            for (int i : position) {
                board.mask(i);
            }
        }
    }
    /**
     * @return a copy of this state's player positions
     */
    public int[][] getPlayerPositions() {
        int[][] pos = positions.clone();
        for(int i = 0; i < positions.length; i++) {
            pos[i] = positions[i].clone();
        }
        return pos;
    }

    /**
     * @return a copy of the points
     */
    public int[] getPoints() { return points.clone(); }

    /**
     * @param player the player
     * @return the player's point vector
     */
    public int getPlayerPoints(int player) { return points[player]; }

    /**
     * @return the current player
     */
    public int getPlayer() { return player; }

    public void setPlayer(int player) { this.player = player; }

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

    /**
     * Moves the player's tokens to the indices defined by the move and masks a field in the grid
     * @param m the move
     */
    public void movePlayer(Move m) {
        int oldLeftPosition = positions[player][0];
        int oldRightPosition = positions[player][1];
        if (oldLeftPosition != m.first) {
            positions[player][0] = m.first;
            board.mask(m.first);
            board.unmask(oldLeftPosition);
            if (oldLeftPosition != m.second) {
                points[player]++;
            }
        }
        if (oldRightPosition != m.second) {
            if (oldRightPosition != m.first) {
                board.unmask(oldRightPosition);
                points[player]++;
            }
            positions[player][1] = m.second;
            board.mask(m.second);
        }
        board.mask(m.delete);
    }
}