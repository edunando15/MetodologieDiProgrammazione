package it.unicam.cs.followme.jrobot.model;

import it.unicam.cs.followme.jrobot.model.simulation.Circle;
import it.unicam.cs.followme.jrobot.model.simulation.Rectangle;
import it.unicam.cs.followme.jrobot.model.simulation.Robot;
import it.unicam.cs.followme.jrobot.model.simulation.SimulationEnvironment;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class SimulationEnvironmentTest {

    Position origin = new Position();
    Rectangle rectangle1 = new Rectangle("Test", origin, 2.0, 2.0);
    Circle circle1 = new Circle("Test", origin, 1.0);
    Robot<Direction> robot1 = new Robot<>(new Direction(0.5, 0.0), 1.0, "Test");
    Robot<Direction> robot2 = new Robot<>(new Direction(0.1, 0.0), 2.0, "Test");
    Robot<Direction> robot3 = new Robot<>(new Direction(0.0, -0.6), 1.0, "Material_");
    Robot<Direction> robot4 = new Robot<>(new Direction(0.2, 0.0), 0.6, "Material_");
    Map<MotionlessArea, Position> areas = Map.of(
            rectangle1, rectangle1.getCentralPosition(),
            circle1, circle1.getCentralPosition()
    );

    @Test
    public void getAreasAtTest() {
        SimulationEnvironment<MotionlessArea, MovableItem<Direction>> environment =
                new SimulationEnvironment<>(areas, new HashMap<>());
        List<MotionlessArea> areasList = new ArrayList<>();
        assertEquals(0, environment.getAreasAt(new Position(10.0, 10.0)).size());
        assertEquals(1, environment.getAreasAt(new Position(-0.84, 0.84)).size());
        areasList = (environment.getAreasAt(new Position(-0.84, 0.84)));
        assertEquals(FigureType.RECTANGLE, areasList.get(0).getFigureType());
        assertEquals(2, environment.getAreasAt(new Position(0.2, 0.2)).size());
    }

    @Test
    public void updateMovableItemPosition() {
        HashMap<MovableItem<Direction>, Position> items = new HashMap<>();
        items.put(robot1, origin);
        items.put(robot2, new Position(1.0, 1.0));
        SimulationEnvironment<MotionlessArea, MovableItem<Direction>> e =
                new SimulationEnvironment<>(new HashMap<>(), items);
        e.updateMovableItemPosition(robot1, new Position(5.0, 5.0));
        assertEquals(new Position(5.0, 5.0), e.getItemPositionOf(robot1));
        e.updateMovableItemPosition(robot2, origin);
        assertEquals(origin, e.getItemPositionOf(robot2));
    }

    @Test
    public void getMovableItemsAtTest() {
        List<Robot<Direction>> result = new ArrayList<>();
        HashMap<MovableItem<Direction>, Position> items = new HashMap<>();
        items.put(robot1, origin);
        items.put(robot2, new Position(1.0, 1.0));
        SimulationEnvironment<MotionlessArea, MovableItem<Direction>> e =
                new SimulationEnvironment<>(new HashMap<>(), items);
        assertEquals(0, e.getMovableItemsAt(new Position(-1.0, -1.0)).size());
        assertEquals(1, e.getMovableItemsAt(new Position()).size());
    }

    @Test
    public void getItemPositionOfTest() {
        HashMap<MovableItem<Direction>, Position> items = new HashMap<>();
        items.put(robot1, origin);
        items.put(robot2, new Position(1.0, 1.0));
        SimulationEnvironment<MotionlessArea, MovableItem<Direction>> e =
                new SimulationEnvironment<>(new HashMap<>(), items);
        assertNull(e.getItemPositionOf(new Robot<>(new Direction(0.7, 0.0), 00., "")));
        assertEquals(origin, e.getItemPositionOf(robot1));
        e.updateMovableItemPosition(robot2, new Position());
        assertEquals(origin, e.getItemPositionOf(robot2));
    }

    @Test
    public void signalLabelTest() {
        SimulationEnvironment<MotionlessArea, Robot<Direction>> environment = new SimulationEnvironment<>();
        environment.addItem(robot1, origin);
        environment.addItem(robot2, new Position(1.0, 1.0));
        environment.addItem(robot3, new Position(-1.0, -1.0));
        environment.addItem(robot4, new Position(-2.0, -2.0));
        assertTrue(environment.getItemsMap().keySet().stream().allMatch(Robot::isSignaling));
    }

}
