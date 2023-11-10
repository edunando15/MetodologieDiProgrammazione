package it.unicam.cs.followme.jrobot.model;

import java.util.List;
import java.util.Map;

/**
 * This interface is used to represent the environment of a robot simulation.
 * Its responsibility is to collect items in itself, associating Positions to
 * Items. It's possible to update the Position of Movable items, but it's
 * impossible to update the Position of Motion Less areas.
 * @param <A> Type representing a motionless Area.
 * @param <I> Type representing a movable Item.
 */
public interface Environment <A extends MotionlessArea, I extends MovableItem<Direction>> {

    /**
     * This method is used to obtain a Map that associates
     * a Position to the center of an Area.
     * @return A copy of the Map associating positions to areas.
     */
    Map<A, Position> getAreasMap();

    /**
     * This method is used to obtain a Map that associates
     * a Position to an Item.
     * @return A copy of the Map associating positions to items.
     */
    Map<I, Position> getItemsMap();

    /**
     * This method is used to add an Area to the Environment.
     * @param area The Area to add.
     * @throws NullPointerException If any argument is null.
     * @throws IllegalArgumentException If the Area's Position
     * already exists in the Environment.
     */
    void addArea(A area) throws NullPointerException, IllegalArgumentException;

    /**
     * This method is used ot add an Item to the Environment.
     * @param item The Item to add.
     * @param itemPosition The Item's Position.
     * @throws NullPointerException If any argument is null.
     * @throws IllegalArgumentException If the Item's Position
     * already exists in the Environment.
     */
    void addItem(I item, Position itemPosition) throws NullPointerException, IllegalArgumentException;

    /**
     * This method is used to update the Position of a Movable Item.
     * @param item The Item whose Position must be updated.
     * @param newItemPosition The new Position of the Item.
     * @throws NullPointerException If any argument is null.
     */
    void updateMovableItemPosition(I item, Position newItemPosition) throws NullPointerException;

    /**
     * This method is used to obtain the areas that cover the specified Position.
     * @param position The considered Position.
     * @return The List containing the areas that cover the specified Position.
     */
    List<A> getAreasAt(Position position);

    /**
     * This method is used to obtain the items at a certain Position in
     * the Environment.
     * @param position The considered Position.
     * @return A List containing the items at the specified Position.
     */
    List<I> getMovableItemsAt(Position position);

    /**
     * This method is used to obtain the Position in the Environment
     * of a particular Item.
     * @param item The considered Item.
     * @return The Position of the Item.
     */
    Position getItemPositionOf(I item);

    /**
     * All the items that have the given label as condition
     * will signal it.
     * @param label A label.
     * @throws NullPointerException If the label is null.
     */
    void makeItemsSignal(String label) throws NullPointerException;

    /**
     * All the items that have the given label as condition
     * will un-signal it.
     * @param label A label.
     * @throws NullPointerException If the label is null.
     */
    void makeItemsUnSignal(String label);

    /**
     * This method is used to create a new Environment starting from an
     * existing one.
     * @param items The items map.
     * @return A new Environment composed by the given argument.
     */
    Environment<A, I> getInstance(Map<I, Position> items);

}
