package org.de.htw.aiforgames.boardgame;

import java.util.Objects;

/**
 * A triangle represents a position in a 2d grid.
 * The triangle is considered equilateral and has a gold or blue color
 */
public class Triangle {

    // The id of the triangle in the grid.
    private final int id;
    // The x coordinate of the triangle
    private final int x;
    // The y coordinate of the triangle
    private final int y;
    // The triangle's color
    private final Color color;
    // Whether the triangle is marked as unreachable or not
    private boolean isMasked;

    // The possible color values, either gold or blue
    public enum Color { GOLD, BLUE }

    public Triangle(int id, int x, int y, Color color) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.color = color;
        this.isMasked = false;
    }

    /**
     * @return true if the triangle is masked, false otherwise
     */
    public boolean masked() { return this.isMasked; }

    /**
     * Renders the triangle unreachable
     */
    public void mask() { this.isMasked = true; }

    /**
     * Renders the triangle reachable
     */
    public void unmask() { this.isMasked = false; }

    /**
     * @return the unique identifier of the triangle
     */
    public int getId() { return id; }

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
    public Triangle computeLeft() { return new Triangle(id-1,x-1, y, getOppositeColor()); }

    /**
     * Computes the right neighbour of this triangle
     *
     * @return the right neighbour
     */
    public Triangle computeRight() { return new Triangle(id+1, x+1, y, getOppositeColor()); }

    /**
     * The bottom neighbour of this triangle
     *
     * @return the bottom neighbour
     */
    public Triangle computeBottom() {
        // TODO: Find a way to compute the index of the triangle from the coordinate??
        return (this.color == Color.GOLD) ? new Triangle(-1,x-1, y-1, Color.BLUE) : new Triangle(-1,x+1, y+1, Color.GOLD);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Triangle triangle = (Triangle) o;
        return id == triangle.id && x == triangle.x && y == triangle.y && color == triangle.color;
    }

    @Override
    public int hashCode() { return Objects.hash(id, x, y, color); }

    @Override
    public String toString() {
        return "Triangle{" +
                "id=" + id +
                ", x=" + x +
                ", y=" + y +
                ", color=" + color +
                ", masked=" + isMasked +
                '}';
    }
}
