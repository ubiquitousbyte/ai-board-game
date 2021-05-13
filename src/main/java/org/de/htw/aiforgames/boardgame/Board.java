package org.de.htw.aiforgames.boardgame;

/**
 * The playing board represented as a collection of 34 triangles
 */
public class Board {

    // The total number of elements in the grid
    private static final int SIZE = 34;
    private static final int MAX_X = 9;
    private static final int MAX_Y = 5;
    private static final int MIN_X = 0;
    private static final int MIN_Y = 1;

    // A 1-d representation of the board
    private final Triangle[] board;

    public Board() {
        board = new Triangle[SIZE+1];
        int boardIndex = 0;
        for(int offset = 3, startY = 1; offset <= 11; offset +=2, startY++) {
            Triangle t = new Triangle(0, startY, Triangle.Color.BLUE);
            board[boardIndex] = t;
            boardIndex++;
            for(Triangle right = t.computeRight(); right.getX() < offset; right = right.computeRight()) {
                board[boardIndex] = right;
                boardIndex++;
            }
        }
        // These two indices are not part of the grid
        board[24] = null;
        board[34] = null;
    }

    /**
     * @param index the index
     * @return the triangle at the given index.
     */
    public Triangle getTriangle(int index) {
        if (index < 1 || index > SIZE) return null;
        return board[index-1];
    }

    /**
     * Returns the left neighbour of the triangle located at the given index position
     * @param index the index of the triangle whose left neighbour is returned
     * @return the left neighbour
     */
    public Triangle getLeftNeighbour(int index) {
        Triangle current = getTriangle(index);
        if (current == null) return null;
        Triangle leftNeighbour = getTriangle(index-1);
        if (leftNeighbour == null || leftNeighbour.getY() != current.getY()) return null;
        return leftNeighbour;
    }

    /**
     * Returns the right neighbour of the triangle located at the given index position
     * @param index the index of the triangle whose right neighbour is returned
     * @return the right neighbour
     */
    public Triangle getRightNeighbour(int index) {
        Triangle current = getTriangle(index);
        if (current == null) return null;
        Triangle rightNeighbour = getTriangle(index+1);
        if (rightNeighbour == null || rightNeighbour.getY() != current.getY()) return null;
        return rightNeighbour;
    }

    /**
     * Returns the bottom neighbour of the triangle located at the given index position
     * @param index the index of the triangle whose bottom neighbour is returned
     * @return the bottom neighbour
     */
    public Triangle getBottomNeighbour(int index) {
        Triangle current = getTriangle(index);
        if (current == null) return null;
        // Don't search for the neighbour inside the board, as this operation has a O(n) time complexity. Instead, just compute it.
        Triangle bottomNeighbour = current.computeBottom();
        if (bottomNeighbour.getX() < MIN_X || bottomNeighbour.getY() < MIN_Y) return null;
        if (bottomNeighbour.getX() > MAX_X || bottomNeighbour.getY() > MAX_Y) return null;
        return bottomNeighbour;
    }
}
