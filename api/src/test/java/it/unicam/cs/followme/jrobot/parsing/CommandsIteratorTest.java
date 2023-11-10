package it.unicam.cs.followme.jrobot.parsing;

import it.unicam.cs.followme.jrobot.model.*;
import it.unicam.cs.followme.jrobot.model.simulation.Circle;
import it.unicam.cs.followme.jrobot.model.simulation.Rectangle;
import it.unicam.cs.followme.jrobot.model.simulation.Robot;
import it.unicam.cs.followme.jrobot.model.simulation.SimulationEnvironment;
import it.unicam.cs.followme.utilities.FollowMeParser;
import it.unicam.cs.followme.utilities.FollowMeParserException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class CommandsIteratorTest {

    MovableItem<Direction> robot1 = new Robot<>(new Direction(0.5, 0.5), 5.0, "TEST");
    MovableItem<Direction> robot2 = new Robot<>(new Direction(0.0, 1.0), 1.0, "TEST");
    MotionlessArea circle = new Circle("TEST", new Position(), 1.0);
    MotionlessArea rectangle = new Rectangle("TEST", new Position() ,1.0, 2.0);
    Map<MovableItem<Direction>, Position> items = new HashMap<>();
    Map<MotionlessArea, Position> areas = new HashMap<>();
    Environment<MotionlessArea, MovableItem<Direction>> environment;
    CommandsIteratorBuilder<MotionlessArea, MovableItem<Direction>> builder;
    Handler<MotionlessArea, MovableItem<Direction>> handler = new Handler<>();
    FollowMeParser parser = new FollowMeParser(handler);
    List<MovableItem<Direction>> robots;
    List<CommandsIterator<MotionlessArea, MovableItem<Direction>>> iterators;
    CommandsIteratorBuilder<MotionlessArea, MovableItem<Direction>> builder2;
    Handler<MotionlessArea, MovableItem<Direction>> handler2 = new Handler<>();
    FollowMeParser parser2 = new FollowMeParser(handler2);
    MovableItem<Direction> robot3 = new Robot<>(new Direction(1.0, 0.0), 5.0, "TEST");
    MovableItem<Direction> robot4 = new Robot<>(new Direction(0.0, 1.0), 1.0, "TEST");
    MovableItem<Direction> robot5 = new Robot<>(new Direction(-1.0, 0.0), 3.0, "TEST");
    Environment<MotionlessArea, MovableItem<Direction>> environment2;
    Map<MovableItem<Direction>, Position> items2 = new HashMap<>();
    List<MovableItem<Direction>> robots2;
    List<CommandsIterator<MotionlessArea, MovableItem<Direction>>> iterators2;

    @BeforeEach
    public void initialiseFirstFileVariables() throws FollowMeParserException, IOException {
        areas.put(circle, circle.getCentralPosition());
        areas.put(rectangle, rectangle.getCentralPosition());
        items.put(robot1, new Position());
        items.put(robot2, new Position(5.0, 5.0));
        environment = new SimulationEnvironment<>(areas, items);
        parser.parseRobotProgram(Path.of("src\\test\\resources\\commands.txt"));
        builder = new CommandsIteratorBuilder<>(handler.getReadCommands());
        robots = List.of(robot1, robot2);
        iterators = builder.getCommandsIteratorsFor(robots, 1.0);
    }

    @BeforeEach
    public void initialiseSecondFileVariables() throws FollowMeParserException, IOException {
        items2.put(robot3, new Position(1.0, 1.0));
        items2.put(robot4, new Position(-1.0, -1.0));
        items2.put(robot5, new Position());
        environment2 = new SimulationEnvironment<>(areas, items2);
        parser2.parseRobotProgram(Path.of("src\\test\\resources\\commands2.txt"));
        builder2 = new CommandsIteratorBuilder<>(handler2.getReadCommands());
        robots2 = List.of(robot3, robot4, robot5);
        iterators2 = builder2.getCommandsIteratorsFor(robots2, 1.0);
    }

    @Test
    public void moveTest() throws FollowMeParserException {
        assertTrue(iterators.get(0).hasNext());
        iterators.get(0).next(environment);
        MovableItem<Direction> item = iterators.get(0).getItem();
        assertTrue(item.getDirection().equals(new Direction(1.0, 1.0)) && item.getSpeed().equals(1.0));

    }

    @Test
    public void untilTest() throws FollowMeParserException {
        iterators.get(0).next(environment); // MOVE command.
        assertTrue(iterators.get(0).hasNext());
        iterators.get(0).next(environment); // UNTIL TEST command. Should skip the command.
        assertTrue(iterators.get(0).getItem().getSpeed().equals(0.0) && iterators.get(0).getItem().getDirection().equals(new Direction(1.0, 1.0)));
    }

    @Test
    public void repeatTest() throws FollowMeParserException {
        iterators.get(0).next(environment); // MOVE command.
        iterators.get(0).next(environment); // UNTIL TEST command, skipped.
        assertTrue(iterators.get(0).hasNext());
        iterators.get(0).next(environment); // REPEAT 5 command.
        for(int i = 0; i < 4; i++) { // REPEAT command.
            assertTrue(iterators.get(0).getItem().getDirection().equals(new Direction(-1.0, -1.0)) && iterators.get(0).getItem().getSpeed().equals(15.0));
            assertTrue(iterators.get(0).hasNext());
            iterators.get(0).next(environment);
        }
    }

    @Test
    public void foreverTest() throws FollowMeParserException {
        for(int i = 0; i < 7; i++) iterators.get(0).next(environment); // Goes to DO FOREVER command.
        assertTrue(iterators.get(0).hasNext());
        iterators.get(0).next(environment); // FOREVER command.
        for(int i = 0; i < 100; i++) { // It's a FOREVER command.
            assertTrue(iterators.get(0).getItem().getDirection().equals(new Direction(1.0, 1.0)) && iterators.get(0).getItem().getSpeed().equals(11.0));
            iterators.get(0).next(environment);
        }
    }

    @Test
    public void followTest() throws FollowMeParserException {
        for(int i = 0; i < 3; i++) assertTrue(iterators2.get(i).hasNext());
        for(int i = 0; i < 3; i++) iterators2.get(i).next(environment2); // FOLLOW command.
        for(int i = 0; i < 2; i++) assertEquals(0.5, iterators2.get(i).getItem().getSpeed());
        assertEquals(0.0, iterators2.get(2).getItem().getSpeed());
    }

    @Test
    public void continueTest() throws FollowMeParserException {
        for(int i = 0; i < 3; i++) {
            iterators2.get(i).next(environment2); // FOLLOW command.
            iterators2.get(i).next(environment2); // CONTINUE command.
        }
        for(int i = 0; i < 2; i++) assertEquals(0.5, iterators2.get(i).getItem().getSpeed());
        assertEquals(0.0, iterators2.get(2).getItem().getSpeed());
        for(int i = 0; i < 3; i++) iterators2.get(i).next(environment2); // Still CONTINUE command.
        for(int i = 0; i < 2; i++) assertEquals(0.5, iterators2.get(i).getItem().getSpeed());
        assertEquals(0.0, iterators2.get(2).getItem().getSpeed());
    }

    @Test
    public void signalTest() throws FollowMeParserException {
        for(int i = 0; i < 3; i++) {
            iterators2.get(i).next(environment2); // FOLLOW command.
            iterators2.get(i).next(environment2); // CONTINUE command.
            iterators2.get(i).next(environment2); // CONTINUE command.
            iterators2.get(i).next(environment2); // SIGNAL command.
        }
        for(int i = 0; i < 3; i++) assertTrue(iterators2.get(i).getItem().isSignaling());
    }

    @Test
    public void unsignalTest() throws FollowMeParserException {
        for(int i = 0; i < 3; i++) {
            iterators2.get(i).next(environment2); // FOLLOW command.
            iterators2.get(i).next(environment2); // CONTINUE command.
            iterators2.get(i).next(environment2); // CONTINUE command.
            iterators2.get(i).next(environment2); // SIGNAL command.
            iterators2.get(i).next(environment2); // UNSIGNAL command.
        }
        for(int i = 0; i < 3; i++) assertFalse(iterators2.get(i).getItem().isSignaling());
    }

    @Test
    public void stopTest() throws FollowMeParserException {
        for(int i = 0; i < 3; i++) {
            iterators2.get(i).next(environment2); // FOLLOW command.
            iterators2.get(i).next(environment2); // CONTINUE command.
            iterators2.get(i).next(environment2); // CONTINUE command.
            iterators2.get(i).next(environment2); // SIGNAL command.
            iterators2.get(i).next(environment2); // UNSIGNAL command.
            iterators2.get(i).next(environment2); // MOVE command.
        }
        for(int i = 0; i < 3; i++) {
            iterators2.get(i).next(environment2); // STOP command.
            assertEquals(0.0, iterators2.get(i).getItem().getSpeed());
        }
    }

}
