package it.unicam.cs.followme.jrobot.model;

import java.util.Optional;

/**
 * This functional interface is used to compute the Direction
 * of an Item, given an initial Position and a final Position.
 * If the Direction has more than 1 coordinate different from 0,
 * it produces 2 directions, which will bring the item to the
 * final destination.
 */
@FunctionalInterface
public interface DirectionComputer {

    /**
     * This method is used to compute a Direction between
     * 2 positions.
     * @param initialPosition The initial Position.
     * @param finalPosition the final Position.
     * @return An {@code Optional} Direction if the
     * Direction exists.
     */
    Optional<Direction> computeDirectionOf(Position initialPosition, Position finalPosition);

    DirectionComputer DEFAULT_DIRECTION_COMPUTER = (initialPosition, finalPosition) -> {
        Double x = finalPosition.getX() - initialPosition.getX();
        Double y = finalPosition.getY() - initialPosition.getY();
        while(x < -1.0 || x > 1.0 || y < -1.0 || y > 1.0) {
            x /= 10;
            y /= 10;
        }
        return x.equals(0.0) && y.equals(0.0) ? Optional.empty() : Optional.of(new Direction(x, y));
    };

}
