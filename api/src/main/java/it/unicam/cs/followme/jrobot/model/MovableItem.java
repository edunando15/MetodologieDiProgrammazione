package it.unicam.cs.followme.jrobot.model;

/**
 * This interface is used to represent an Item in a simulation.
 * An Item is able to move.
 * @param <D> Type representing a Direction.
 */
public interface MovableItem<D extends Direction> {

    /**
     * This method is used to obtain the direction of an Item.
     * @return The Position representing the direction of the Item.
     */
    D getDirection();

    /**
     * This method is used to set the direction of an Item.
     * @param direction The movement direction of the Item.
     * @throws NullPointerException If the given argument is null.
     */
    void setDirection(D direction) throws NullPointerException;

    /**
     * This method is used to obtain the speed of an Item,
     * expressed in m/s.
     * @return The Item's speed.
     */
    Double getSpeed();

    /**
     * This method is used to set the speed of an Item.
     * @param speed The speed expressed in m/s.
     * @throws NullPointerException If the given argument is null.
     * @throws IllegalArgumentException If the given argument
     * is negative.
     */
    void setSpeed(Double speed) throws NullPointerException, IllegalArgumentException;

    /**
     * This method is used to set the condition of an Item.
     * @param condition The new condition of the Item.
     * @throws NullPointerException If the given argument
     * is null.
     */
    void setCondition(String condition) throws NullPointerException, IllegalArgumentException;

    /**
     * This method is used to obtain the condition of the Robot.
     * @return The actual condition of the Robot.
     */
    String getCondition();

    /**
     * This method is used to tell to a Movable Item that
     * it must signal its condition to other items.
     */
    void signal();

    /**
     * This method is used to tell to a Movable Item that
     * it must un-signal its condition to other items.
     */
    void unSignal();

    /**
     * This method is used to tell if an Item is signaling
     * its condition.
     * @return True if the Item is signaling its condition,
     * false otherwise.
     */
    boolean isSignaling();

    /**
     * Returns a copy of this object.
     * @return A new Movable Item with the
     * same characteristics.
     */
    MovableItem<D> getInstance();

    /**
     * Returns a new Item with the parameters
     * taken from the given Item.
     * @param item The Item to copy.
     * @return A new copy of the given Item.
     */
    MovableItem<D> getInstance(MovableItem<D> item);

}
