package it.unicam.cs.followme.jrobot.model.simulation;


import it.unicam.cs.followme.jrobot.model.FigureType;
import it.unicam.cs.followme.jrobot.model.MotionlessArea;
import it.unicam.cs.followme.jrobot.model.Position;

/**
 * This class is used to represent a circle in a simulation.
 */
public final class Circle implements MotionlessArea {

    /**
     * This variable represents the label of the Circle.
     */
    private final String label;

    /**
     * This variable represents the Position of the Circle's center.
     */
    private final Position center;

    /**
     * This variable represents the Circle's radius.
     */
    private final Double radius;

    /**
     * This is the constructor for a Circle.
     * @param label The Circle's label.
     * @param center The Circle's center Position.
     * @param radius The Circle's radius.
     * @throws NullPointerException If any argument is null.
     * @throws IllegalArgumentException If the radius is negative.
     */
    public Circle(String label, Position center, Double radius) {
        if(label == null || center == null || radius == null) throw new NullPointerException("Null arguments.");
        if(radius < 0.0) throw new IllegalArgumentException("Negative radius.");
        this.label = label;
        this.center = center;
        this.radius = radius;
    }

    /**
     A Circle is described as (x - centerX) ^ 2 + (y - centerY) ^ 2 = r ^ 2.
     So, a point is inside the Circle if the inequality
     (x - centerX) ^ 2 + (y - centerY) ^ 2 <= r ^ 2 is satisfied.
     */
    @Override
    public boolean coversPosition(Position position) {
        Double otherX = position.getX();
        Double otherY = position.getY();
        Double thisX = center.getX();
        Double thisY = center.getY();
        return (Math.pow((otherX - thisX), 2) + Math.pow((otherY - thisY), 2)) <= Math.pow(radius, 2);
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public Position getCentralPosition() {
        return center;
    }

    @Override
    public Double getSurface() {
        return (Math.pow(radius, 2)) * Math.PI;
    }

    @Override
    public final FigureType getFigureType() {
        return FigureType.CIRCLE;
    }

    @Override
    public double[] getParameters() {
        return new double[]{center.getX(), center.getY(), radius};
    }

}
