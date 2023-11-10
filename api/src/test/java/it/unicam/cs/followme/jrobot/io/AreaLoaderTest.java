package it.unicam.cs.followme.jrobot.io;

import it.unicam.cs.followme.jrobot.model.*;
import it.unicam.cs.followme.jrobot.model.simulation.Robot;
import it.unicam.cs.followme.jrobot.model.simulation.SimulationEnvironment;
import it.unicam.cs.followme.jrobot.parsing.Handler;
import it.unicam.cs.followme.utilities.FollowMeParser;
import it.unicam.cs.followme.utilities.FollowMeParserException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AreaLoaderTest {

    SimulationEnvironment<MotionlessArea, Robot<Direction>> environment;
    Handler<MotionlessArea, MovableItem<Direction>> handler = new Handler<>();
    FollowMeParser parser = new FollowMeParser(handler);

    AreaLoader<MotionlessArea> creator = new MotionlessAreaLoader(parser);

    Map<Robot<Direction>, Position> items = new HashMap<>();

    @Test
    public void shouldCreateArea() throws FollowMeParserException, IOException {
        Map<MotionlessArea, Position> areas = creator.createAreasMap(Path.of("src\\test\\resources\\shapes.txt"));
        environment = new SimulationEnvironment<>(areas, items);
        MotionlessArea var = environment.getAreasAt(new Position(-0.5, -0.5)).get(0); // Rectangle.
        assertEquals(0, environment.getAreasAt(new Position(1.0, 1.0)).size());
        assertEquals(FigureType.RECTANGLE, environment.getAreasAt(new Position(4.5, 5.3)).get(0).getFigureType());
        assertEquals(FigureType.CIRCLE, environment.getAreasAt(new Position()).get(0).getFigureType());
        assertTrue(environment.getAreasAt(new Position(0.7, 0.5)).get(0).coversPosition(new Position(-0.2, -0.2)));
        assertEquals(Math.PI, environment.getAreasAt(new Position(-0.5, -0.5)).get(0).getSurface());
        assertEquals(2.0, environment.getAreasAt(new Position(6.0, 5.5)).get(0).getSurface());
    }

    @Test
    public void checkParametersOfCreatedAreaTest() throws FollowMeParserException, IOException {
        Map<MotionlessArea, Position> areas = creator.createAreasMap(Path.of("src\\test\\resources\\shapes.txt"));
        environment = new SimulationEnvironment<>(areas, items);
        MotionlessArea rectangle = environment.getAreasAt(new Position(6.0, 5.5)).get(0);
        MotionlessArea circle = environment.getAreasAt(new Position(-0.5, -0.6)).get(0);
        assertTrue(rectangle.getLabel().equals("SILVER_") &&
                rectangle.getCentralPosition().equals(new Position(5.0, 5.0)));
        assertTrue(circle.getLabel().equals("GOLD_") && circle.getCentralPosition().equals(new Position()));
    }

}
