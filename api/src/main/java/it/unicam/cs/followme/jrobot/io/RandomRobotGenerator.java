package it.unicam.cs.followme.jrobot.io;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Random;

/**
 * Instances of this class are used to create a text file containing
 * random parameters of an arbitrary number of robots.
 */
public class RandomRobotGenerator {

    /**
     * This variable stores the Path of the file in which the robots' parameters
     * are written.
     */
    private String path;

    public static final String DEFAULT_PATH = "src\\main\\resources\\randomRobots.txt";

    /**
     * This variable is used to store the number of generated robots.
     */
    private final int numberOfRobots;

    private final double xCoordinate;

    private final double yCoordinate;

    private final double speed;

    private final String condition;
    /**
     * Constructs a Random Robot Generator.
     * @param builder The Builder.
     * @param path A String containing the Path.
     */
    public RandomRobotGenerator(RandomRobotGeneratorBuilder builder, String path) {
        if(builder == null) throw new NullPointerException("Null argument.");
        this.path = path;
        numberOfRobots = builder.getN();
        xCoordinate = builder.getXCoordinate();
        yCoordinate = builder.getYCoordinate();
        speed = builder.getSpeed();
        condition = builder.getCondition();
    }

    /**
     * Constructs a generator with the default path.
     */
    public RandomRobotGenerator(RandomRobotGeneratorBuilder builder) {
        this(builder, RandomRobotGenerator.DEFAULT_PATH);
    }

    /**
     * This method is used to generate robots.
     * @throws IOException If any error occur during reading.
     */
    public final void generateRandomRobots() throws IOException {
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(this.path));
            for(int i = 0; i < numberOfRobots; i++) {
                writer.write("ROBOT " + directionCoordinate() + " " + directionCoordinate() + " " +
                        generateRandomBetween(0.0, speed) + " " + condition + " " + generateRandomBetween(-xCoordinate, xCoordinate) +
                        " " + generateRandomBetween(-yCoordinate, yCoordinate) + "\n");
            }
            writer.close();
        } catch (IOException e) { throw new IOException("Error writing file."); }
    }

    /**
     * This method is used to obtain a random number within the specified
     * range.
     * @param v1 The first number.
     * @param v2 The second number.
     * @return A random number in [v1, v2].
     */
    public static Double generateRandomBetween(Double v1, Double v2) {
        Random randomGenerator = new Random();
        Double delta = (v1 >= v2) ? (v1 - v2) : (v2 - v1);
        return randomGenerator.nextDouble() * delta + Math.min(v1, v2);
    }

    private double directionCoordinate() {
        return generateRandomBetween(-1.0, 1.0);
    }

    /**
     * This method is used to obtain the path of the File in which
     * the random robots' parameters are written.
     * @return The Path of the file
     */
    public final Path getPath() {
        return Path.of(this.path);
    }

}
