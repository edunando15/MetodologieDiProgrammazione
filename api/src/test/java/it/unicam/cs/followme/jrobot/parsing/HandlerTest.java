package it.unicam.cs.followme.jrobot.parsing;

import it.unicam.cs.followme.jrobot.model.Direction;
import it.unicam.cs.followme.jrobot.model.MotionlessArea;
import it.unicam.cs.followme.jrobot.model.MovableItem;
import it.unicam.cs.followme.jrobot.util.Pair;
import it.unicam.cs.followme.utilities.FollowMeParser;
import it.unicam.cs.followme.utilities.FollowMeParserException;
import it.unicam.cs.followme.utilities.RobotCommand;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HandlerTest {

    CommandsIteratorBuilder<MotionlessArea, MovableItem<Direction>> builder;
    CommandsHandler handler = new Handler<>();
    FollowMeParser parser = new FollowMeParser(handler);

    @Test
    public void shouldCreateOrderedCommandsTest() throws FollowMeParserException, IOException {
        parser.parseRobotProgram(Path.of("src\\test\\resources\\commands.txt"));
        builder = new CommandsIteratorBuilder<>(handler.getReadCommands());
        List<Pair<RobotCommand, String[]>> commands = builder.getCommands();
        assertEquals(RobotCommand.MOVE, commands.get(0).first());
        assertEquals(RobotCommand.UNTIL, commands.get(1).first());
        assertEquals(RobotCommand.MOVE, commands.get(2).first());
        assertEquals(RobotCommand.DONE, commands.get(3).first());
    }

    @Test
    public void shouldCreateOrderedCommandsTest2() throws FollowMeParserException, IOException {
        parser.parseRobotProgram(Path.of("src\\test\\resources\\commands.txt"));
        builder = new CommandsIteratorBuilder<>(handler.getReadCommands());
        List<Pair<RobotCommand, String[]>> commands = builder.getCommands();
        assertEquals(RobotCommand.REPEAT, commands.get(4).first());
        assertEquals(RobotCommand.MOVE, commands.get(5).first());
        assertEquals(RobotCommand.DONE, commands.get(6).first());
        assertEquals(RobotCommand.FOREVER, commands.get(7).first());
        assertEquals(RobotCommand.MOVE, commands.get(8).first());
        assertEquals(RobotCommand.DONE, commands.get(9).first());
    }

}
