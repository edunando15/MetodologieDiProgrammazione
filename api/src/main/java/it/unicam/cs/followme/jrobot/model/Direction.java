package it.unicam.cs.followme.jrobot.model;

/**
 * Instances of this class are used to represent a position
 * (in a bi-dimensional space) whose coordinates are
 * within [-1, 1].
 */
public record Direction(Double x, Double y) {

    /**
     * Constructs a Direction. The coordinates
     * must be within [-1, 1].
     *
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @throws NullPointerException     If any argument is null.
     * @throws IllegalArgumentException If the coordinates
     *                                  aren't within their bounds or if there's more than
     *                                  one coordinate whose value is 0.
     */
    public Direction {
        if (x < -1.0 || x > 1.0 || y < -1.0 || y > 1.0) throw new IllegalArgumentException("Coordinates" +
                "must be within their bounds [-1, 1].");
        if (x == 0.0 && y == 0.0) throw new IllegalArgumentException("At most one coordinate can be 0.");
    }

    /**
     * Two directions are equal if they have the same components.
     *
     * @param o The reference object with which to compare.
     * @return True if the two objects represent the same Direction,
     * false otherwise. The precision is limited to the 3rd digit
     * after the integer part.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (getClass() != o.getClass()) return false;
        Direction that = (Direction) o;
        long thisX = (long) (normalizeX() * 1000.0);
        long thisY = (long) (normalizeY() * 1000.0);
        long thatX = (long) (that.normalizeX() * 1000.0);
        long thatY = (long) (that.normalizeY() * 1000.0);
        return (thisX == thatX && thisY == thatY);
    }

    /**
     * This method is used to obtain the normalized x coordinate.
     *
     * @return The x coordinate divided by the module.
     */
    public Double normalizeX() {
        return x() / module();
    }

    /**
     * This method is used to obtain the normalized y coordinate.
     *
     * @return The y coordinate divided by the module.
     */
    public Double normalizeY() {
        return y() / module();
    }

    /**
     * This method is used to compute the module of a Direction.
     * It's calculated as Math.sqrt(x ^ 2 + y ^ 2).
     * @return The module of the Direction.
     */
    public Double module() {
        return Math.sqrt(Math.pow(x(), 2) + Math.pow(y(), 2));
    }
}
