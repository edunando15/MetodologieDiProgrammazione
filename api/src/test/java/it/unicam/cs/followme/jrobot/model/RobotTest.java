package it.unicam.cs.followme.jrobot.model;

import it.unicam.cs.followme.jrobot.model.simulation.Robot;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RobotTest {

    @Test
    public void conditionTest() {
        assertDoesNotThrow(() -> new Robot<Direction>(new Direction(0.0, 0.1), 0.0, "TeSt123__"));
        assertDoesNotThrow(() -> new Robot<Direction>(new Direction(0.0, 0.1), 0.0, ""));
        assertDoesNotThrow(() -> new Robot<Direction>(new Direction(0.0, 0.1), 0.0, "_"));
        assertThrows(IllegalArgumentException.class, () -> new Robot<Direction>(new Direction(0.1, 0.1), 0.0, "??"));
        assertThrows(IllegalArgumentException.class, () -> new Robot<Direction>(new Direction(0.0, 0.1), 0.0, "!£$%&/()=?"));
    }


}
