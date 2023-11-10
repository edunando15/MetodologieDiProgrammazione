package it.unicam.cs.followme.jrobot.model;

import it.unicam.cs.followme.jrobot.model.simulation.Robot;
import it.unicam.cs.followme.jrobot.model.simulation.SimulationEnvironment;
import it.unicam.cs.followme.jrobot.model.simulation.SimulationSpace;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SimulationSpaceTest {

    HashMap<Robot<Direction>, Position> items = new HashMap<>();
    Position origin = new Position();
    Robot<Direction> robot1 = new Robot<>(new Direction(-0.5, 0.0), 1.0, "Test");
    Robot<Direction> robot2 = new Robot<>(new Direction(-0.5, 0.0), 1.0, "Test");
    Robot<Direction> robot3 = new Robot<>(new Direction(0.1, 0.0), 1.0, "Test");
    Robot<Direction> robot4 = new Robot<>(new Direction(0.1, 0.0), 2.0, "Test");

    @Test
    public void getNeighboursOfTest() {
        SimulationSpace<Robot<Direction>> space = new SimulationSpace<>();
        items.put(robot1, origin);
        items.put(robot2, new Position(5.0, 5.0));
        SimulationEnvironment<MotionlessArea, Robot<Direction>> environment =
                new SimulationEnvironment<>(new HashMap<>(), items);
        environment.makeItemsSignal("Test");
        assertEquals(0, space.getNeighboursOf(robot1, 0.0, environment, "Test").size());
        assertEquals(1, space.getNeighboursOf(robot1, 10.0, environment, "Test").size());
        environment.addItem(robot3, new Position(0.0, 5.0));
        environment.addItem(robot4, new Position(10.0, 5.0));
        environment.makeItemsSignal("Test");
        assertEquals(2, space.getNeighboursOf(robot2, 5.0, environment, "Test").size());
    }

    @Test
    public void getNeighboursOf2Test() {
        SimulationSpace<Robot<Direction>> space = new SimulationSpace<>();
        SimulationEnvironment<MotionlessArea, Robot<Direction>> environment =
                new SimulationEnvironment<>(new HashMap<>(), new HashMap<>());
        environment.addItem(robot1, origin);
        environment.addItem(robot2, new Position(5.0, 5.0));
        environment.addItem(robot3, new Position(0.0, 5.0));
        environment.addItem(robot4, new Position(10.0, 5.0));
        environment.makeItemsSignal("Test");
        Position c1 = environment.getItemPositionOf(robot1);
        Position c2 = new Position();
        assertEquals(c1, c2);
        assertEquals(4, space.getNeighboursOf(new Position(2.0, 2.0), 100.0, environment, "Test").size());
    }

    @Test
    public void shouldNotBeNeighboursTest() {
        SimulationSpace<Robot<Direction>> space = new SimulationSpace<>();
        SimulationEnvironment<MotionlessArea, Robot<Direction>> environment =
                new SimulationEnvironment<>(new HashMap<>(), new HashMap<>());
        environment.addItem(robot1, new Position());
        environment.addItem(robot2, new Position(5.0, 5.0)); // They are signaling.
        assertEquals(1, space.getNeighboursOf(robot1, 100.0, environment, "Test").size());
        environment.getMovableItemsAt(new Position()).get(0).unSignal(); // robot1 isn't signaling anymore.
        assertEquals(0, space.getNeighboursOf(robot2, 100.0, environment, "Test").size());
    }

    @Test
    public void getMovableItemsSignalingTest() {
        SimulationSpace<Robot<Direction>> space = new SimulationSpace<>();
        SimulationEnvironment<MotionlessArea, Robot<Direction>> environment =
                new SimulationEnvironment<>(new HashMap<>(), new HashMap<>());
        environment.addItem(robot1, new Position()); // (0, 0).
        environment.addItem(robot3, new Position(5.0, 5.0)); // (5, 5).
        assertEquals(2, space.getMovableItemsSignaling(environment, "Test").size());
    }

}
