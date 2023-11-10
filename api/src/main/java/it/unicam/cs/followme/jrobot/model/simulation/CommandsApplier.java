package it.unicam.cs.followme.jrobot.model.simulation;

import it.unicam.cs.followme.jrobot.model.*;
import it.unicam.cs.followme.jrobot.parsing.CommandsIterator;
import it.unicam.cs.followme.utilities.FollowMeParserException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Instances of this class are used to apply commands in robot simulation.
 * @param <A> Type representing a Motionless Area.
 * @param <I> Type representing a Movable Item.
 */
public class CommandsApplier<A extends MotionlessArea, I extends MovableItem<Direction>> implements Applier<A, I> {

    /**
     * This variable is used to store the time Interval. Once it's set, it cannot be changed.
     */
    private Double timeInterval;

    public CommandsApplier(Double timeInterval) {
        if(timeInterval == null) throw new NullPointerException();
        if(timeInterval < 0.0) throw new IllegalArgumentException();
        this.timeInterval = timeInterval;
    }

    public CommandsApplier(List<CommandsIterator<A, I>> commandsIterators) { this(Applier.DEFAULT_TIME_INTERVAL); }

    @Override
    public Environment<A, I> apply(Environment<A, I> environment, List<CommandsIterator<A, I>> commandsIterators) throws FollowMeParserException {
        Map<I, Position> items = new HashMap<>(environment.getItemsMap()); // Copy of the map.
        NextPositionComputer<A, I> computer = new NextPositionComputer<>();
        for(CommandsIterator<A, I> iterator : commandsIterators) {
            if(iterator.hasNext()) {
                iterator.next(environment);
                computer.computeNextPosition(iterator.getItem(), timeInterval, environment);
            }
        }
        items.entrySet()
                .stream()
                .parallel()
                .forEach(entry -> entry.setValue(computer.computeNextPosition(entry.getKey(), timeInterval, environment)));
        return environment.getInstance(items);
    }

    public void setTimeInterval(double timeInterval) {
        if(timeInterval <= 0) throw new IllegalArgumentException("Illegal argument.");
        this.timeInterval = timeInterval;
    }
}


