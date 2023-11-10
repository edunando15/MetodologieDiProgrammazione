package it.unicam.cs.followme.jrobot.model;

import java.util.Objects;

/**
 * Instances of this class are used to represent a position in a bi-dimensional simulation.
 * It is assumed that a Position has two Double coordinates.
 */
public class Position {

    protected final Double x;

    protected final Double y;

    /**
     * Constructs a Position with the given coordinates.
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @throws NullPointerException If any argument is null.
     */
    public Position(Double x, Double y) throws NullPointerException {
        if(x == null || y == null) throw new NullPointerException("Null arguments.");
        this.x = x;
        this.y = y;
    }

    /**
     * Constructs a Position with x and y coordinates set to 0.
     */
    public Position() {
        this(0.0, 0.0);
    }

    /**
     * This method is used to get the x coordinate.
     * @return The x coordinate.
     */
    public Double getX() { return x; }

    /**
     * This method is used to get the y coordinate.
     * @return The y coordinate.
     */
    public Double getY() { return y; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if(getClass() != o.getClass()) return false;
        Position that = (Position) o; // It's a Position for sure.
        return getX().equals(that.getX()) && getY().equals(that.getY());
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    /**
     * This method is used to obtain a copy of this object.
     * @return A new Position, equal to this.
     */
    public Position getInstance() {
        return new Position(getX(), getY());
    }

}
