package it.unicam.cs.followme.jrobot.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class DirectionTest {

    Direction positiveX1 = new Direction(0.1, 0.0);
    Direction positiveX2 = new Direction(0.2, 0.0);
    Direction negativeX1 = new Direction(-0.3, 0.0);
    Direction negativeX2 = new Direction(-0.4, 0.0);
    Direction positiveY1 = new Direction(0.0, 0.5);
    Direction positiveY2 = new Direction(0.0, 0.6);
    Direction negativeY1 = new Direction(0.0, -0.6);
    Direction negativeY2 = new Direction(0.0, -0.7);

    @Test
    public void equalsShouldReturnTrueTest() {
        assertEquals(positiveX1, positiveX2);
        assertEquals(negativeX1, negativeX2);
        assertEquals(positiveY1, positiveY2);
        assertEquals(negativeY1, negativeY2);
        assertEquals(new Direction(0.0, 1.0), positiveY2);
    }

    @Test
    public void equalsShouldReturnFalseTest() {
        assertNotEquals(positiveX1, negativeX1);
        assertNotEquals(positiveY1, negativeY2);
        assertNotEquals(negativeX1, positiveY2);
        assertNotEquals(positiveX1, negativeY1);
        assertNotEquals(new Direction(0.0, -1.0), positiveY1);
    }

    @Test
    public void equalsShouldReturnTrue2() {
        assertEquals(new Direction(0.5, -0.5), new Direction(0.95, -0.95));
        assertEquals(new Direction(0.3, -0.25), new Direction(0.9, -0.75));
        assertEquals(new Direction(-0.2, -0.9), new Direction(-0.2, -0.9));
        assertEquals(new Direction(-0.23, 0.2), new Direction(-0.92, 0.8));
    }

}
