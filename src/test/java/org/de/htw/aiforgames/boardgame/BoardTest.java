package org.de.htw.aiforgames.boardgame;

import org.de.htw.aiforgames.boardgame.game.Board;
import org.de.htw.aiforgames.boardgame.game.Triangle;
import org.junit.Test;

import static org.junit.Assert.*;

public class BoardTest {

    @Test
    public void boardMustContainCorrectCoordinatesAtCorrectPositions() {
        Board board = new Board();
        Triangle t = board.getTriangle(1);
        assertEquals(t, new Triangle(1,0, 1, Triangle.Color.BLUE));
        t = board.getTriangle(2);
        assertEquals(t, new Triangle(2, 1, 1, Triangle.Color.GOLD));
        t = board.getTriangle(3);
        assertEquals(t, new Triangle(3, 2, 1, Triangle.Color.BLUE));
        t = board.getTriangle(4);
        assertEquals(t, new Triangle(4, 0, 2, Triangle.Color.BLUE));
        t = board.getTriangle(5);
        assertEquals(t, new Triangle(5, 1, 2, Triangle.Color.GOLD));
        t = board.getTriangle(6);
        assertEquals(t, new Triangle(6, 2, 2, Triangle.Color.BLUE));
        t = board.getTriangle(7);
        assertEquals(t, new Triangle(7, 3, 2, Triangle.Color.GOLD));
        t = board.getTriangle(8);
        assertEquals(t, new Triangle(8, 4, 2, Triangle.Color.BLUE));
        t = board.getTriangle(9);
        assertEquals(t, new Triangle(9, 0, 3, Triangle.Color.BLUE));
        t = board.getTriangle(10);
        assertEquals(t, new Triangle(10, 1, 3, Triangle.Color.GOLD));
        t = board.getTriangle(11);
        assertEquals(t, new Triangle(11, 2, 3, Triangle.Color.BLUE));
        t = board.getTriangle(12);
        assertEquals(t, new Triangle(12, 3, 3, Triangle.Color.GOLD));
        t = board.getTriangle(13);
        assertEquals(t, new Triangle(13, 4, 3, Triangle.Color.BLUE));
        t = board.getTriangle(14);
        assertEquals(t, new Triangle(14, 5, 3, Triangle.Color.GOLD));
        t = board.getTriangle(15);
        assertEquals(t, new Triangle(15, 6, 3, Triangle.Color.BLUE));
        t = board.getTriangle(16);
        assertEquals(t, new Triangle(16, 0, 4, Triangle.Color.BLUE));
        t = board.getTriangle(17);
        assertEquals(t, new Triangle(17, 1, 4, Triangle.Color.GOLD));
        t = board.getTriangle(18);
        assertEquals(t, new Triangle(18, 2, 4, Triangle.Color.BLUE));
        t = board.getTriangle(19);
        assertEquals(t, new Triangle(19, 3, 4, Triangle.Color.GOLD));
        t = board.getTriangle(20);
        assertEquals(t, new Triangle(20, 4, 4, Triangle.Color.BLUE));
        t = board.getTriangle(21);
        assertEquals(t, new Triangle(21, 5, 4, Triangle.Color.GOLD));
        t = board.getTriangle(22);
        assertEquals(t, new Triangle(22, 6, 4, Triangle.Color.BLUE));
        t = board.getTriangle(23);
        assertEquals(t, new Triangle(23, 7, 4, Triangle.Color.GOLD));
        t = board.getTriangle(24);
        assertEquals(t, new Triangle(24, 8, 4, Triangle.Color.BLUE));
        t = board.getTriangle(25);
        assertNull(t);
        t = board.getTriangle(26);
        assertEquals(t, new Triangle(26, 1, 5, Triangle.Color.GOLD));
        t = board.getTriangle(27);
        assertEquals(t, new Triangle(27, 2, 5, Triangle.Color.BLUE));
        t = board.getTriangle(28);
        assertEquals(t, new Triangle(28, 3, 5, Triangle.Color.GOLD));
        t = board.getTriangle(29);
        assertEquals(t, new Triangle(29, 4, 5, Triangle.Color.BLUE));
        t = board.getTriangle(30);
        assertEquals(t, new Triangle(30, 5, 5, Triangle.Color.GOLD));
        t = board.getTriangle(31);
        assertEquals(t, new Triangle(31, 6, 5, Triangle.Color.BLUE));
        t = board.getTriangle(32);
        assertEquals(t, new Triangle(32, 7, 5, Triangle.Color.GOLD));
        t = board.getTriangle(33);
        assertEquals(t, new Triangle(33, 8, 5, Triangle.Color.BLUE));
        t = board.getTriangle(34);
        assertEquals(t, new Triangle(34, 9, 5, Triangle.Color.GOLD));
    }

    @Test
    public void getLeftNeighbourShallReturnTheCorrectLeftNeighbourIfItExists() {
        Board board = new Board();
        Triangle left = board.getLeftNeighbour(12);
        assertEquals(left, new Triangle(11, 2, 3, Triangle.Color.BLUE));
    }

    @Test
    public void getLeftNeighbourShallReturnNullIfTheLeftNeighbourDoesNotExist() {
        Board board = new Board();
        Triangle left = board.getLeftNeighbour(1);
        assertNull(left);
    }

    @Test
    public void getLeftNeighbourShallReturnNullIfTheCurrentTriangleDoesNotExist() {
        Board b = new Board();
        assertNull(b.getLeftNeighbour(-1));
    }

    @Test
    public void getRightNeighbourShallReturnTheCorrectRightNeighbourIfItExists() {
        Board b = new Board();
        assertEquals(b.getRightNeighbour(23), new Triangle(24, 8, 4, Triangle.Color.BLUE));
    }

    @Test
    public void getRightNeighbourShallReturnNullIfTheRightNeighbourDoesNotExist() {
        assertNull(new Board().getRightNeighbour(15));
    }

    @Test
    public void getRightNeighbourShallReturnNullIfTheCurrentTriangleDoesNotExist() {
        assertNull(new Board().getRightNeighbour(-2));
    }

    @Test
    public void getBottomNeighbourShallReturnTheTriangleOnTheTopForABlueTriangle() {
        assertEquals(new Board().getBottomNeighbour(6), new Triangle(12,3, 3, Triangle.Color.GOLD));
    }

    @Test
    public void getBottomNeighbourShallReturnTheTriangleOnTheBottomForAGoldTriangle() {
        assertEquals(new Board().getBottomNeighbour(12), new Triangle(6, 2, 2, Triangle.Color.BLUE));
    }

    @Test
    public void getBottomNeighbourSHallReturnNullIfTheTriangleHasNoBottom() {
        assertNull(new Board().getBottomNeighbour(31));
    }
}
