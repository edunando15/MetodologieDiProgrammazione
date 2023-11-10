package it.unicam.cs.followme.jrobot.io;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class RandomRobotGeneratorTest {

    RandomRobotGeneratorBuilder builder;
    RandomRobotGenerator generator;
    ItemLoader reader;

    @BeforeEach
    public void initialiseVariables() {
        reader = new ItemLoader();
        builder = new RandomRobotGeneratorBuilder();
        builder.setN(7);
        builder.setSpeed(1.0);
        builder.setCondition("first_test");
        builder.setXCoordinate(5.0);
        builder.setYCoordinate(5.0);
        generator = new RandomRobotGenerator(builder);
    }

    @Test
    public void generateRobots() throws IOException {
        generator.generateRandomRobots(); // Random robots are generated.
        reader.parse(generator.getPath()); // If no exception is thrown, the file was read correctly.
        // The generated File is in "api\\src\\main\\resources\\randomRobots.txt".
    }

}
