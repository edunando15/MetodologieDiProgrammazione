package it.unicam.cs.followme.jrobot.parsing;

import it.unicam.cs.followme.jrobot.model.Direction;
import it.unicam.cs.followme.jrobot.model.MotionlessArea;
import it.unicam.cs.followme.jrobot.model.MovableItem;
import it.unicam.cs.followme.jrobot.util.Pair;
import it.unicam.cs.followme.utilities.RobotCommand;

import java.util.ArrayList;
import java.util.List;

/**
 * Instances of this class are used to build a Commands Iterator for Movable items.
 * Basically, it associates to a Movable Item its commands.
 */
public class CommandsIteratorBuilder<A extends MotionlessArea, I extends MovableItem<Direction>> {

    private final List<Pair<RobotCommand, String[]>> commands;

    /**
     * Constructs a Commands Iterator Builder.
     * @param commands The commands.
     */
    public CommandsIteratorBuilder(List<Pair<RobotCommand, String[]>> commands) {
        this.commands = commands;
    }

    /**
     * This method is used to build a Commands Iterator for
     * a particular item.
     * @param item A Movable Item.
     * @param timeInterval The considered time interval.
     * @return A new Commands Iterator, with the given item.
     */
    public CommandsIterator<A, I> getCommandsIteratorFor(I item, double timeInterval) {
        return new CommandsIterator<>(commands, item, timeInterval);
    }

    /**
     * This method is used to build a List of Commands iterators.
     * Each Commands Iterator is associated to a particular Item.
     * @param items A List of Movable items.
     * @param timeInterval The considered time interval.
     * @return A List of Commands iterators, each one associated
     * to an item.
     */
    public List<CommandsIterator<A, I>> getCommandsIteratorsFor(List<I> items, double timeInterval) {
        List<CommandsIterator<A, I>> result = new ArrayList<>();
        for(I item : items) { result.add(getCommandsIteratorFor(item, timeInterval)); }
        return result;
    }

    /**
     * This method is used to obtain the commands that have been parsed
     * by a Parser and ordered by a Handler.
     * @return The commands of this Builder.
     */
    public List<Pair<RobotCommand, String[]>> getCommands() {
        return commands;
    }

}
