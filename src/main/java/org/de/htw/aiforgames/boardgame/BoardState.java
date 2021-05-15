package org.de.htw.aiforgames.boardgame;

import lenz.htw.blocks.Move;

import java.util.List;

public class BoardState implements Cloneable {

    // The current player
    private int player;
    // A 3x2 array holding the indices of the player's tokens
    private int[][] positions;
    // A 3x3 array holding the point vectors from the perspective of each player
    private int[][] points;
    // The game board
    private Board board;

    public BoardState(int player, int[][] positions) {
        this.board = new Board();
        this.player = player;
        this.positions = positions;
        this.points = new int[3][3];
    }

    public BoardState(int player, int[][] positions, int[][] points) {
        this.board = new Board();
        this.player = player;
        this.positions = positions;
        this.points = points;
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
    public int[][] getPoints() {
        int[][] p = points.clone();
        for(int i = 0; i < points.length; i++) {
            p[i] = points[i].clone();
        }
        return p;
    }

    /**
     * @param player the player
     * @return the player's point vector
     */
    public int[] getPlayerPoints(int player) { return points[player]; }

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
     * @param player the player whose making the move
     * @param m the move
     */
    public void movePlayer(int player, Move m) {
        int oldLeftPosition = positions[player][0];
        if (oldLeftPosition != m.first) {
            board.unmask(oldLeftPosition);
            positions[player][0] = m.first;
            board.mask(m.first);
            points[player][player]++;
        }
        int oldRightPosition = positions[player][1];
        if (oldRightPosition != m.second) {
            board.unmask(oldRightPosition);
            positions[player][1] = m.second;
            board.mask(m.second);
            points[player][player]++;
        }
        board.mask(m.delete);
    }

    @Override
    public BoardState clone()  {
        try{
            BoardState newState = (BoardState) super.clone();
            newState.positions = getPlayerPositions();
            newState.points = getPoints();
            newState.board = board.clone();
            return newState;
        }
        catch (CloneNotSupportedException e) {
            return null;
        }
    }
}