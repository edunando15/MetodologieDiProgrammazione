package it.unicam.cs.followme.jrobot.model;

import it.unicam.cs.followme.jrobot.model.simulation.Circle;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CircleTest {

    Position origin = new Position();
    Circle circle = new Circle("Safe", origin, 1.0); // Goniometric circumference.
    Random randomGenerator = new Random();

    Double r = Math.sqrt(randomGenerator.nextDouble());
    Double theta = randomGenerator.nextDouble() * 2 * Math.PI;

    @Test
    public void coversPosition() {
        assertTrue(circle.coversPosition(origin));
        assertTrue(circle.coversPosition(new Position(1.0, 0.0))); // Point inside the Circle.
        assertTrue(circle.coversPosition(new Position(-1.0, 0.0))); // Point inside the Circle.
        assertTrue(circle.coversPosition(new Position(0.0, 1.0))); // Point inside the Circle.
        assertTrue(circle.coversPosition(new Position(0.0, -1.0))); // Point inside the Circle.
        assertTrue(circle.coversPosition(new Position(
                r * Math.cos(theta), r * Math.sin(theta)
        ))); // Random point within the radius.
        assertFalse(circle.coversPosition(new Position(1.0, 1.0))); // Point outside the Circle.
        assertFalse(circle.coversPosition(new Position(-1.0, 1.0))); // Point outside the Circle.
    }

}
