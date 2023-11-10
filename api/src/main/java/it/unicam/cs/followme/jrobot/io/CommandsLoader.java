package it.unicam.cs.followme.jrobot.io;

import it.unicam.cs.followme.jrobot.model.Direction;
import it.unicam.cs.followme.jrobot.model.MotionlessArea;
import it.unicam.cs.followme.jrobot.model.MovableItem;
import it.unicam.cs.followme.jrobot.parsing.CommandsIterator;
import it.unicam.cs.followme.utilities.FollowMeParserException;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * This interface is used to load commands for Movable items from a File.
 */
@FunctionalInterface
public interface CommandsLoader<A extends MotionlessArea, I extends MovableItem<Direction>> {

    /**
     * This method is used to create the List of iterators, given a File of commands
     * and a Handler.
     * @param file The commands' File.
     * @param items A List of Movable items.
     * @param timeInterval The time interval.
     * @return A List containing the iterators for the given commands.
     */
    List<CommandsIterator<A, I>> getIterators(File file, List<I> items, double timeInterval) throws FollowMeParserException, IOException;

}
