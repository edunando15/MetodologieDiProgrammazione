package it.unicam.cs.followme.jrobot.io;

/**
 * Instances of this class are used to build a Random Robot Generator.
 */
public class RandomRobotGeneratorBuilder {

    /**
     * The default number of robots generated: 5.
     */
    public static final int DEFAULT_NUMBER_OF_ROBOTS = 5;

    private int n;

    private double speed;

    private double xCoordinate;

    private double yCoordinate;

    private String condition;

    /**
     * @param n The number of robots that
     * is needed to generate.
     */
    public void setN(int n) {
        if(n <= 0) throw new IllegalArgumentException("Illegal argument.");
        this.n = n;
    }

    /**
     * Sets the number of robots at 5.
     */
    public void setN() {
        setN(DEFAULT_NUMBER_OF_ROBOTS);
    }

    public int getN() {
        return n;
    }

    public double getSpeed() {
        return speed;
    }

    /**
     * The generated robot will have a speed between 0 and
     * the given speed.
     * @throws IllegalArgumentException If the speed is negative.
     */
    public void setSpeed(double speed) {
        if(speed < 0.0) throw new IllegalArgumentException("Illegal argument.");
        this.speed = speed;
    }

    public double getXCoordinate() {
        return xCoordinate;
    }

    /**
     * The generated robots
     * will be in a random initial Position with x coordinate
     * in the range [-xPosition, xPosition].
     */
    public void setXCoordinate(double xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public double getYCoordinate() {
        return yCoordinate;
    }

    /**
     * The generated robots will
     * be in a random initial Position with y coordinate
     * in the range [-yPosition, yPosition].
     */
    public void setYCoordinate(double yCoordinate) {
        this.yCoordinate = yCoordinate;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        if(condition == null) throw new NullPointerException("Null argument.");
        this.condition = condition;
    }
}
