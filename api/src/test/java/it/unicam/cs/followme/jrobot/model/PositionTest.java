package it.unicam.cs.followme.jrobot.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class PositionTest {

    @Test
    public void testGetPosition() {
        Position position = new Position(3.4, 6.0);
        assertEquals(3.4, position.getX());
        assertEquals(6.0, position.getY());
    }

    @Test
    public void testEquals() {
        Position c1 = new Position();
        Position c2 = new Position();
        Position c3 = new Position(9.0, 10.0);
        Position c4 = new Position(10.0, 9.0);
        Position c5 = new Position(10.0, 9.0);
        assertEquals(c1, c2);
        assertEquals(c2, c1);
        assertNotEquals(c1, c3);
        assertNotEquals(c3, c1);
        assertNotEquals(c3, c4);
        assertNotEquals(c4, c3);
        assertEquals(c4, c5);
        assertEquals(c5, c4);
        assertEquals(c1, c1);
        assertEquals(c4, c4);
        assertNotEquals(null, c1);
        assertNotEquals(null, c4);
    }

    @Test
    public void getInstanceTest() {
        Position c = new Position();
        assertEquals(0, c.getX());
        assertEquals(0, c.getY());
        assertEquals(c, c.getInstance());
        assertEquals(c.getX(), c.getInstance().getX());
        assertEquals(c.getY(), c.getInstance().getY());
    }


}
