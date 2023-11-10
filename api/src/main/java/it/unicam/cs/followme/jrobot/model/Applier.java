package it.unicam.cs.followme.jrobot.model;

import it.unicam.cs.followme.jrobot.parsing.CommandsIterator;
import it.unicam.cs.followme.utilities.FollowMeParserException;

import java.util.List;

/**
 * This Functional Interface is used to represent an Applier for a simulation.
 * An applier applies movements, signals and un-signals to an Environment.
 * @param <A> Type representing a Motionless Area.
 * @param <I> Type representing a Movable Item.
 */
public interface Applier<A extends MotionlessArea, I extends MovableItem<Direction>> {

    /**
     * This is the default time interval: 1 second.
     */
    double DEFAULT_TIME_INTERVAL = 1.0;

    /**
     * This method is used to apply the instructions
     * of items to an Environment.
     * @param environment The considered Environment.
     * @param commandsIterators The Commands iterators
     * for the considered commands.
     * @return A new Environment with the applied
     * instructions.
     */
    Environment<A, I> apply(Environment<A, I> environment, List<CommandsIterator<A, I>> commandsIterators) throws FollowMeParserException;

    /**
     * This method is used to change the time interval.
     * @param timeInterval The new time interval.
     * @throws IllegalArgumentException If the given
     * argument is negative or equal to 0.
     */
    void setTimeInterval(double timeInterval);

}
