package it.unicam.cs.followme.jrobot.model;

import java.util.List;

/**
 * This interface is used to represent the space of a simulation.
 * It is different from an <code>Environment</code>, because
 * its responsibility is to give the neighbours of an Item
 * at a particular Position, within a certain distance.
 * @param <I> Type representing a MovableItem.
 */
public interface Space<I extends MovableItem<Direction>> {

    /**
     * This method is used to obtain the neighbours of a Position,
     * in a particular Environment, within a certain distance that
     * are signaling a particular label.
     * All the elements in the same Position won't be considered.
     * @param <A> Type representing a Motion Less Area in the Environment.
     * @param position The Position of the Item.
     * @param distance The distance within search.
     * @param environment The considered Environment.
     * @param label The considered label.
     * @return A List containing the neighbours of an Item.
     */
    <A extends MotionlessArea>
    List<I> getNeighboursOf(Position position, Double distance, Environment<A, I> environment, String label);

    /**
     * This method is used to obtain the neighbours of an Item,
     * in a particular Environment, within a certain distance that
     * are signaling a particular label.
     * In the result, all the elements in the same Position
     * of the Item are excluded, including the Item itself.
     * @param <A> Type representing a Motion Less Area in the Environment.
     * @param item The considered Item.
     * @param distance The distance within search.
     * @param environment The considered Environment.
     * @param label The considered label.
     * @return The List containing the neighbours of an Item.
     */
    <A extends MotionlessArea>
    List<I> getNeighboursOf(I item, Double distance, Environment<A, I> environment, String label);

    /**
     * This method is used to obtain the list containing the positions of an
     * item's neighbours.
     * @param <A> Type representing a Motion Less Area in the Environment.
     * @param item The considered Item.
     * @param distance The distance within which to consider the neighbours.
     * @param environment The considered Environment.
     * @param label The considered label.
     * @return A List containing the positions of neighbours.
     */
    <A extends MotionlessArea>
    List<Position> getNeighboursPositionsOf(I item, Double distance, Environment<A, I> environment, String label);

    /**
     * This method is used to obtain a List containing
     * all the elements signaling a particular condition
     * in the environment.
     * @param environment The considered Environment.
     * @param label The considered label.
     * @return A List containing the items signaling
     * the given condition.
     * @param <A> Type representing a Motion Less Area.
     * @throws NullPointerException If any argument is null.
     */
    <A extends MotionlessArea>
    List<I> getMovableItemsSignaling(Environment<A, I> environment, String label);

    /**
     * This method is used to get the average Position of
     * item's neighbours signaling the given condition,
     * withing a certain distance, in a particular Environment.
     * @param <A> Type representing a Motionless Area.
     * @param item The considered Item.
     * @param distance The considered distance.
     * @param environment The considered Environment.
     * @param label The considered label.
     * @return The average Position of items signaling the given
     * condition within a certain distance in the given Environment.
     */
    <A extends MotionlessArea>
    Position getAveragePositionOfItems(I item, Double distance, Environment<A, I> environment, String label);

}
