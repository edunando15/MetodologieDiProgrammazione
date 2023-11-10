package it.unicam.cs.followme.jrobot.model.simulation;

import it.unicam.cs.followme.jrobot.model.Direction;
import it.unicam.cs.followme.jrobot.model.MovableItem;

/**
 * This class is used to represent a Robot in a simulation.
 * @param <D> Type representing the direction of the robot.
 */
public class Robot<D extends Direction> implements MovableItem<D> {

    /**
     * This variable is used to tell if a Robot is signaling
     * its condition or not.
     */
    private boolean isSignaling;

    /**
     * This variable represents the current condition of the Robot.
     */
    private String condition;

    /**
     * This variable represents the current direction of the Robot.
     */
    private D direction;

    /**
     * This variable represent the current speed of the Robot.
     */
    private Double speed;

    /**
     * This is the main constructor for a robot.
     * @param initialDirection The initial direction of the Robot.
     * @param speed The initial speed of the Robot.
     * @param initialCondition The initial condition of the Robot.
     * @throws NullPointerException If any argument is null.
     * @throws IllegalArgumentException If the speed is negative.
     */
    public Robot(D initialDirection, Double speed, String initialCondition) throws NullPointerException, IllegalArgumentException {
        setDirection(initialDirection);
        setSpeed(speed);
        setCondition(initialCondition);
        this.isSignaling = true;
    }

    @Override
    public D getDirection() {
        return direction;
    }

    @Override
    public void setDirection(D direction) throws NullPointerException {
        if(direction == null) throw new NullPointerException("Null argument.");
        this.direction = direction;
    }

    @Override
    public Double getSpeed() {
        return speed;
    }

    @Override
    public void setSpeed(Double speed) throws NullPointerException, IllegalArgumentException {
        if(speed == null) throw new NullPointerException("Null argument.");
        if(speed < 0) throw new IllegalArgumentException("Negative speed isn't allowed.");
        this.speed = speed;
    }

    @Override
    public String getCondition() {
        return condition;
    }

    @Override
    public void signal() {
        isSignaling = true;
    }

    @Override
    public void unSignal() {
        isSignaling = false;
    }

    @Override
    public boolean isSignaling() {
        return isSignaling;
    }

    @Override
    public Robot<D> getInstance() {
        return new Robot<>(this.getDirection(), this.getSpeed(), this.getCondition());
    }

    @Override
    public void setCondition(String condition) {
        if(condition == null) throw new NullPointerException("Null argument.");
        if(!checkCondition(condition)) throw new IllegalArgumentException();
        this.condition = condition;
    }

    @Override
    public Robot<D> getInstance(MovableItem<D> robot) {
        return new Robot<>(robot.getDirection(), robot.getSpeed(), robot.getCondition());
    }

    private boolean checkCondition(String condition) {
        char[] string = condition.toCharArray();
        for(int i = 0; i < string.length; i++) {
            if(!Character.isLetterOrDigit(string[i]) && string[i] != '_') return false;
        }
        return true;
    }

}
