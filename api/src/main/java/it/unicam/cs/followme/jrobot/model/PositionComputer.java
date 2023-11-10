package it.unicam.cs.followme.jrobot.model;

/**
 * This functional interface is used to compute the next Position
 * of a Movable Item, using its current Position, speed and direction.
 */
@FunctionalInterface
public interface PositionComputer <A extends MotionlessArea, I extends MovableItem<Direction>> {

    /**
     * This method is used to compute the next Position of an element,
     * given its current Position, speed and direction.
     * @param item The considered item.
     * @param timeInterval The time interval, for example 1 second.
     * @param e The considered Environment.
     * @return The next Position of the item.
     */
    Position computeNextPosition(I item, Double timeInterval, Environment<A, I> e);

    /**
     * Calculates the module as Math.sqrt(x ^ 2 + y ^ 2).
     * The module is referred to a Direction.
     * @param item The considered Item.
     */
    default Double moduleCalculator(I item) {
        return Math.sqrt(Math.pow(item.getDirection().x(), 2) + Math.pow(item.getDirection().y(), 2));
    }

    /**
     * Given an absolute Position p, a Direction, a module and
     * the product between speed and time, it calculates the next
     * Position.
     * @param p The initial Position (x, y).
     * @param item The considered Item, with Direction (a, b).
     * @param timeInterval The considered time interval.
     * @return The Position (x + a/m * (v * t), y + b/m * (v * t)).
     * It's always defined, because the Direction can't have all
     * coordinates equal 0.
     */
    default Position computePosition(Position p, I item, Double timeInterval) {
        Double x = p.getX() + item.getDirection().x()/moduleCalculator(item) * item.getSpeed() * timeInterval;
        Double y = p.getY() + item.getDirection().y()/moduleCalculator(item) * item.getSpeed() * timeInterval;
        return new Position(x, y);
    }

}
