package it.unicam.cs.followme.jrobot.model;

import it.unicam.cs.followme.jrobot.model.simulation.Rectangle;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * The tests were written using a drawing as conceptual model.
 */
public class RectangleTest {

    Position firstQuadrant = new Position(10.0, 10.0);
    Position secondQuadrant = new Position(-10.0, 10.0);

    Random randomGenerator = new Random(1);

    @Test
    public void coversFirstQuadrantPositionTest() {
        Rectangle rectangle = new Rectangle("Safe", firstQuadrant, 1.0, 1.0); // It's a square.
        assertFalse(rectangle.coversPosition(new Position())); // Origin.
        assertTrue(rectangle.coversPosition(
                new Position(randomGenerator.nextDouble() + 9.5, randomGenerator.nextDouble() + 9.5))); // Random point inside the Rectangle.
        assertFalse(rectangle.coversPosition(
                new Position(randomGenerator.nextDouble() + 10.5, randomGenerator.nextDouble() + 10.5))); // Random point outside the Rectangle.
        assertFalse(rectangle.coversPosition(
                new Position(randomGenerator.nextDouble() + 9.5, randomGenerator.nextDouble() + 10.5))); // Random point with a component "inside" the Rectangle.
        assertFalse(rectangle.coversPosition(
                new Position(randomGenerator.nextDouble() + 10.5, randomGenerator.nextDouble() + 9.5))); // Random point with a component "inside" the Rectangle.
        assertTrue(rectangle.coversPosition(rectangle.getCentralPosition())); // Center.
        assertTrue(rectangle.coversPosition(new Position(9.5, 9.5))); // Geometric vertex.
        assertTrue(rectangle.coversPosition(new Position(9.5, 10.5))); // Geometric vertex.
        assertTrue(rectangle.coversPosition(new Position(10.5, 10.5))); // Geometric vertex.
        assertTrue(rectangle.coversPosition(new Position(9.5, 10.5))); // Geometric vertex.
    }

    @Test
    public void coversSecondQuadrantPositionTest() {
        Rectangle rectangle = new Rectangle("Safe", secondQuadrant, 1.0, 2.0); // It's a real Rectangle.
        assertFalse(rectangle.coversPosition(new Position())); // Origin.
        assertTrue(rectangle.coversPosition(
                new Position(-10.5 + randomGenerator.nextDouble(), randomGenerator.nextDouble() + 9.0))); // Random point inside the Rectangle.
        assertFalse(rectangle.coversPosition(
                new Position(-randomGenerator.nextDouble() - 11.0, randomGenerator.nextDouble() + 9.0))); // Random point outside the Rectangle.
        assertTrue(rectangle.coversPosition(rectangle.getCentralPosition())); // Center.
        assertTrue(rectangle.coversPosition(new Position(-10.5, 9.0))); // Geometric vertex.
        assertTrue(rectangle.coversPosition(new Position(-10.5, 11.0))); // Geometric vertex.
        assertTrue(rectangle.coversPosition(new Position(-9.5, 11.0))); // Geometric vertex.
        assertTrue(rectangle.coversPosition(new Position(-9.5, 9.0))); // Geometric vertex.
    }

}
