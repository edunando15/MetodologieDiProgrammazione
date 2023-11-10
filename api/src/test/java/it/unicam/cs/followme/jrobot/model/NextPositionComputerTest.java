package it.unicam.cs.followme.jrobot.model;

import it.unicam.cs.followme.jrobot.model.simulation.NextPositionComputer;
import it.unicam.cs.followme.jrobot.model.simulation.Robot;
import it.unicam.cs.followme.jrobot.model.simulation.SimulationEnvironment;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NextPositionComputerTest {

    Position origin = new Position();
    HashMap<Robot<Direction>, Position> items = new HashMap<>();
    Robot<Direction> robot1 = new Robot<>(new Direction(1.0, 0.0), 1.0, "Test");
    Robot<Direction> robot2 = new Robot<>(new Direction(0.5, 0.0), 1.0, "Test");
    HashMap<MotionlessArea, Position> areas = new HashMap<>();
    SimulationEnvironment<MotionlessArea, Robot<Direction>> environment =
            new SimulationEnvironment<>(areas, items);
    NextPositionComputer<MotionlessArea, Robot<Direction>> computer = new NextPositionComputer<>();

    @Test
    public void computeNextPositionTest() {
        environment.addItem(robot1, new Position());
        environment.addItem(robot2, new Position());
        Position expectedPosition1 = new Position(1.0, 0.0);
        Position expectedPosition2 = new Position(2.0, 0.0);
        assertEquals(expectedPosition1, computer.computeNextPosition(robot1, 1.0, environment)); // (0, 0) -> (1, 0).
        environment.updateMovableItemPosition(robot1, computer.computeNextPosition(robot1, 1.0, environment)); // (1, 0).
        assertEquals(expectedPosition1, computer.computeNextPosition(robot2, 1.0, environment)); // (0, 0) -> (1, 0).
        environment.updateMovableItemPosition(robot2,  computer.computeNextPosition(robot2, 1.0, environment)); // (1, 0).
        assertEquals(expectedPosition2, computer.computeNextPosition(robot1, 1.0, environment)); // (1, 0) -> (2, 0).
        environment.updateMovableItemPosition(robot1, computer.computeNextPosition(robot1, 1.0, environment)); // (2, 0).
    }

    @Test
    public void computeNextPositionTest2() {
        robot1.setDirection(new Direction(-1.0, 0.0));
        environment.addItem(robot1, new Position(2.0, 0.0));
        assertEquals(environment.getItemPositionOf(robot1), computer.computeNextPosition(robot1, 0.0, environment));
        robot1.setSpeed(2.0); // 2.0 m/s.
        environment.updateMovableItemPosition(robot1, origin); // (0, 0).
        Position result = computer.computeNextPosition(robot1, 1.0, environment); // Expected (-2, 0).
        assertEquals(new Position(-2.0, 0.0), result);
        result = computer.computeNextPosition(robot1, 2.0, environment); // The Position hasn't been updated in the Environment.
        robot1.setDirection(new Direction(-0.5, 0.0)); // It's the same.
        assertEquals(new Position(-4.0, 0.0), result); // Expected (-4, 0).
    }

    @Test
    public void computeNextPositionTest3() {
        robot2.setDirection(new Direction(0.5, 0.0));
        robot2.setSpeed(1.0);
        environment.addItem(robot2, new Position(4.0, 5.0)); // (4, 5).
        Position update = computer.computeNextPosition(robot2, 1.0, environment);
        environment.updateMovableItemPosition(robot2, update);
        assertEquals(environment.getItemPositionOf(robot2), new Position(5.0, 5.0)); // (5, 5).
        robot2.setDirection(new Direction(1.0, 0.0));
        Position update2 = computer.computeNextPosition(robot2, 1.0, environment);
        environment.updateMovableItemPosition(robot2, update2);
        assertEquals(environment.getItemPositionOf(robot2), new Position(6.0, 5.0)); // (6, 5).
    }

    @Test
    public void shouldRemainWhereItIsTest() {
        robot1.setSpeed(0.0);
        environment.addItem(robot1, origin); // (0, 0).
        Position update = computer.computeNextPosition(robot1, 1.0, environment);
        assertEquals(origin, update);
    }

}
