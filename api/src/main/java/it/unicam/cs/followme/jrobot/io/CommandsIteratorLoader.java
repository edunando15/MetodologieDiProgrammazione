package it.unicam.cs.followme.jrobot.io;

import it.unicam.cs.followme.jrobot.model.Direction;
import it.unicam.cs.followme.jrobot.model.MotionlessArea;
import it.unicam.cs.followme.jrobot.model.MovableItem;
import it.unicam.cs.followme.jrobot.parsing.CommandsHandler;
import it.unicam.cs.followme.jrobot.parsing.CommandsIterator;
import it.unicam.cs.followme.jrobot.parsing.CommandsIteratorBuilder;
import it.unicam.cs.followme.utilities.FollowMeParser;
import it.unicam.cs.followme.utilities.FollowMeParserException;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Instances of this class are used to load commands from a File for Movable items.
 */
public class CommandsIteratorLoader implements CommandsLoader<MotionlessArea, MovableItem<Direction>> {

    private final FollowMeParser parser;

    private final CommandsHandler handler;

    /**
     * Constructs a Commands Iterator Loader.
     * @param handler The Handler for the commands.
     */
    public CommandsIteratorLoader(CommandsHandler handler) {
        this.handler = handler;
        this.parser = new FollowMeParser(this.handler);
    }

    @Override
    public List<CommandsIterator<MotionlessArea, MovableItem<Direction>>>
    getIterators(File file, List<MovableItem<Direction>> items, double timeInterval) throws FollowMeParserException, IOException {
        FollowMeParser parser = new FollowMeParser(handler);
        parser.parseRobotProgram(file);
        CommandsIteratorBuilder<MotionlessArea, MovableItem<Direction>> builder = new CommandsIteratorBuilder<>(handler.getReadCommands());
        return builder.getCommandsIteratorsFor(items, timeInterval);
    }
}
