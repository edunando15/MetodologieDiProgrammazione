package it.unicam.cs.followme.jrobot.model.simulation;

import it.unicam.cs.followme.jrobot.io.RandomRobotGenerator;
import it.unicam.cs.followme.jrobot.model.*;

import java.util.ArrayList;
import java.util.List;

public class SimulationSpace<I extends MovableItem<Direction>> implements Space<I> {
    @Override
    public <A extends MotionlessArea>
    List<I> getNeighboursOf(Position position, Double distance, Environment<A, I> environment, String label) {
        return environment.getItemsMap().keySet().stream()
                .parallel()
                .filter(k -> {
                    Double d = computeDistanceBetween(position, environment.getItemPositionOf(k));
                    return k.isSignaling() && k.getCondition().equals(label)
                            && (d > 0 && d <= distance);
                })
                .toList();
    }

    public <A extends MotionlessArea>
    List<I> getNeighboursOf(I item, Double distance, Environment<A, I> environment, String label) {
        return getNeighboursOf(environment.getItemPositionOf(item), distance, environment, label);
    }

    @Override
    public <A extends MotionlessArea>
    List<Position> getNeighboursPositionsOf(I item, Double distance, Environment<A, I> environment, String label) {
        List<Position> result = new ArrayList<>();
        List<I> neighbours = getNeighboursOf(item, distance, environment, label);
        for(I neighbour : neighbours) {
            result.add(environment.getItemPositionOf(neighbour));
        }
        return result;
    }

    @Override
    public <A extends MotionlessArea> List<I> getMovableItemsSignaling(Environment<A, I> environment, String label) {
        return environment.getItemsMap().keySet().stream() // Stream containing Movable items.
                .parallel()
                .filter(MovableItem::isSignaling) // Consider only those which are signaling.
                .filter(item -> item.getCondition().equals(label)) // Filter the condition.
                .toList();
    }

    @Override
    public <A extends MotionlessArea>
    Position getAveragePositionOfItems(I item, Double distance, Environment<A, I> environment, String label) {
        List<Position> neighboursPositions = getNeighboursPositionsOf(item, distance, environment, label);
        Double avgX = 0.0;
        Double avgY = 0.0;
        for(Position position : neighboursPositions) {
            avgX += position.getX();
            avgY += position.getY();
        }
        return (neighboursPositions.isEmpty()) ?
                new Position(RandomRobotGenerator.generateRandomBetween(-distance, distance), RandomRobotGenerator.generateRandomBetween(-distance, distance))
                : new Position(avgX / neighboursPositions.size(), avgY / neighboursPositions.size());
    }

    /**
     * This method is used to calculate the distance between two positions.
     * @param p1 The first Position (x1, y1).
     * @param p2 The second Position (x2, y2).
     * @return The distance between the two positions, calculated
     * as sqrt((x2 - x1) ^ 2 + (y2 - y1) ^ 2).
     */
    private Double computeDistanceBetween(Position p1, Position p2) {
        Double d1 = Math.pow((p2.getX() - p1.getX()), 2);
        Double d2 = Math.pow((p2.getY() - p1.getY()), 2);
        return Math.sqrt(d1 + d2);
    }

}
