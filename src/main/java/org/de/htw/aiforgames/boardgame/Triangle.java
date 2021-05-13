package org.de.htw.aiforgames.boardgame;

import java.util.Objects;

/**
 * A triangle represents a position in a 2d grid.
 * The triangle is considered equilateral and has a gold or blue color
 */
public class Triangle {

    // The x coordinate of the triangle
    private final int x;
    // The y coordinate of the triangle
    private final int y;
    // The triangle's color
    private final Color color;

    // The possible color values, either gold or blue
    public enum Color {
        GOLD,
        BLUE
    }

    public Triangle(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    /**
     * @return the x coordinate of the triangle
     */
    public int getX() { return x; }

    /**
     * @return the y coordinate of the triangle
     */
    public int getY() { return y; }

    /**
     * @return the triangle's color
     */
    public Color getColor() { return color; }

    /**
     * @return the opposite color of this triangle.
     */
    public Color getOppositeColor() { return (color == Color.GOLD) ? Color.BLUE : Color.GOLD; }

    /**
     * Computes the left neighbour of this triangle
     *
     * @return the left neighbour
     */
    public Triangle computeLeft() {
        return new Triangle(x-1, y, getOppositeColor());
    }

    /**
     * Computes the right neighbour of this triangle
     *
     * @return the right neighbour
     */
    public Triangle computeRight() {
        return new Triangle(x+1, y, getOppositeColor());
    }

    /**
     * The bottom neighbour of this triangle
     *
     * @return the bottom neighbour
     */
    public Triangle computeBottom() {
        if (this.color == Color.GOLD) {
            return new Triangle(x-1, y-1, Color.BLUE);
        }
        return new Triangle(x+1, y+1, Color.GOLD);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Triangle triangle = (Triangle) o;
        return x == triangle.x && y == triangle.y && color == triangle.color;
    }

    @Override
    public int hashCode() { return Objects.hash(x, y, color); }

    @Override
    public String toString() {
        return "Triangle{" +
                "x=" + x +
                ", y=" + y +
                ", color=" + color +
                '}';
    }
}
