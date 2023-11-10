package it.unicam.cs.followme.jrobot.model;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DirectionComputerTest {

    DirectionComputer computer = DirectionComputer.DEFAULT_DIRECTION_COMPUTER;

    @Test
    public void computerDirectionTest1() {
        Position initialPosition1 = new Position(5.0, 5.0);
        Position finalPosition1 = new Position(); // It should be (-1, -1) or equivalent.
        assertEquals(new Direction(-1.0, -1.0), computer.computeDirectionOf(initialPosition1, finalPosition1).get());
        Position initialPosition2 = new Position(-4.0, 2.3);
        Position finalPosition2 = new Position(0.9, -4.6);
        assertEquals(new Direction(0.49, -0.69), computer.computeDirectionOf(initialPosition2, finalPosition2).get());
        Position initialPosition3 = new Position(); // (0, 0).
        Position finalPosition3 = new Position(-4.5, 6.0);
        assertEquals(new Direction(-0.45, 0.6), computer.computeDirectionOf(initialPosition3, finalPosition3).get());
    }

    @Test
    public void computeDirectionTest2() {
        Position origin1 = new Position(); // (0, 0).
        Position origin2 = new Position(); // (0, 0).
        assertEquals(computer.computeDirectionOf(origin1, origin2), Optional.empty());
        Position initialPosition = new Position(-5.0, 0.0);
        Position finalPosition = new Position();
        assertEquals(new Direction(1.0, 0.0), computer.computeDirectionOf(initialPosition, finalPosition).get());
    }

}
