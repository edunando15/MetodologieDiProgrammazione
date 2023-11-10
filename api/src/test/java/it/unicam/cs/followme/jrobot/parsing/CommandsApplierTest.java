package it.unicam.cs.followme.jrobot.parsing;

import it.unicam.cs.followme.jrobot.model.*;
import it.unicam.cs.followme.jrobot.model.simulation.*;
import it.unicam.cs.followme.utilities.FollowMeParser;
import it.unicam.cs.followme.utilities.FollowMeParserException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommandsApplierTest {

    Handler<MotionlessArea, MovableItem<Direction>> handler = new Handler<>();
    FollowMeParser parser = new FollowMeParser(handler);
    Environment<MotionlessArea, MovableItem<Direction>> environment;
    Robot<Direction> robot1 = new Robot<>(new Direction(1.0, 0.0), 1.0, "TEST");
    Robot<Direction> robot2 = new Robot<>(new Direction(0.5, 0.0), 1.0, "TEST");
    List<MovableItem<Direction>> robots = List.of(robot1, robot2);
    Map<MovableItem<Direction>, Position> items = new HashMap<>();
    MotionlessArea circle = new Circle("TEST", new Position(), 1.0);
    MotionlessArea rectangle = new Rectangle("TEST", new Position(5.0, 5.0), 1.0, 2.0);
    Map<MotionlessArea, Position> areas = new HashMap<>();
    CommandsIteratorBuilder<MotionlessArea, MovableItem<Direction>> builder;
    CommandsApplier<MotionlessArea, MovableItem<Direction>> commandsApplier;
    List<CommandsIterator<MotionlessArea, MovableItem<Direction>>> iterators;

    @BeforeEach
    public void initialiseVariables() throws FollowMeParserException, IOException {
        robot1.signal();
        robot2.signal();
        areas.put(rectangle, rectangle.getCentralPosition());
        areas.put(circle, circle.getCentralPosition());
        items.put(robot1, new Position());
        items.put(robot2, new Position(5.0, 5.0));
        environment = new SimulationEnvironment<>(areas, items);
        parser.parseRobotProgram(Path.of("src\\test\\resources\\commands3.txt"));
        builder = new CommandsIteratorBuilder<>(handler.getReadCommands());
        iterators = builder.getCommandsIteratorsFor(robots, Applier.DEFAULT_TIME_INTERVAL); // 2 iterators.
        commandsApplier = new CommandsApplier<>(iterators);
    }

    @Test
    public void applyMovementTest() throws FollowMeParserException {
        Environment<MotionlessArea, MovableItem<Direction>> resultEnvironment = commandsApplier.apply(environment, iterators); // FOLLOW command.
        Position final1 = resultEnvironment.getItemPositionOf(robot1); // Should be around (1, 1).
        assertTrue(final1.getX() >= 0.98 && final1.getX() <= 1.02 && final1.getY() >= 0.98 && final1.getY() <= 1.02);
        Position final2 = resultEnvironment.getItemPositionOf(robot2); // Should be around (4, 4).
        assertTrue(final2.getX() >= 3.98 && final2.getX() <= 4.02 && final2.getY() >= 3.98 && final2.getY() <= 4.02);
        Environment<MotionlessArea, MovableItem<Direction>> resultEnvironment2 = commandsApplier.apply(resultEnvironment, iterators); // MOVE command.
        assertTrue(resultEnvironment2.getItemPositionOf(robot1).getX().equals(final1.getX() + 1.0)
                && resultEnvironment2.getItemPositionOf(robot1).getY().equals(final1.getY()));
        assertTrue(resultEnvironment2.getItemPositionOf(robot2).getX().equals(final2.getX() + 1.0)
                && resultEnvironment2.getItemPositionOf(robot2).getY().equals(final2.getY()));
    }

}
