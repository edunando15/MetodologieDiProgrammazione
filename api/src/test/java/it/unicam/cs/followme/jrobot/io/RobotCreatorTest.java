package it.unicam.cs.followme.jrobot.io;

import it.unicam.cs.followme.jrobot.model.Direction;
import it.unicam.cs.followme.jrobot.model.MovableItem;
import it.unicam.cs.followme.jrobot.model.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RobotCreatorTest {

    Map<MovableItem<Direction>, Position> items;

    @BeforeEach
    public void generateRobotsFromFileTest() throws IOException {
        MovableItemLoader<MovableItem<Direction>> reader = new ItemLoader();
        items = reader.parse(Path.of("src\\test\\resources\\robots.txt"));
    }

    @Test
    public void checkRobotsParametersTest() {
        List<Map.Entry<MovableItem<Direction>, Position>> list =  items.entrySet().stream().toList();
        assertEquals(1, list.size());
        MovableItem<Direction> parsedRobot = list.get(0).getKey();
        Position robotPosition = list.get(0).getValue();
        assertEquals(new Direction(1.0, 0.0), parsedRobot.getDirection());
        assertEquals(1.0, parsedRobot.getSpeed());
        assertEquals("TeSt01_", parsedRobot.getCondition());
        assertEquals(new Position(5.0, 5.0), robotPosition);
    }

}
