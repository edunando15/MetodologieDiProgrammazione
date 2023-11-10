package it.unicam.cs.followme.jrobot.model.simulation;

import it.unicam.cs.followme.jrobot.model.FigureType;
import it.unicam.cs.followme.jrobot.model.MotionlessArea;
import it.unicam.cs.followme.jrobot.model.Position;

/**
 * This class is used to represent a Rectangle Area in a simulation.
 */
public final class Rectangle implements MotionlessArea {

    /**
     * This variable is used to store the label of the Area.
     */
    private final String label;

    /**
     * This variable is used to represent the Position of the center of the Area.
     */
    public final Position position;

    /**
     * The height of the Rectangle.
     */
    private final Double height;

    /**
     * The length of the Rectangle.
     */
    private final Double length;

    /**
     * This is the constructor for a Rectangle in a simulation.
     * @param label    The label of the Rectangle.
     * @param position The Position of Rectangle's
     * center.
     * @param length The Rectangle's length.
     * @param height The Rectangle's width.
     * @throws NullPointerException If any argument is null.
     * @throws IllegalArgumentException If height or length are
     * negative.
     */
    public Rectangle(String label, Position position, Double length, Double height) {
        if(label == null || position == null || height == null || length == null) throw new NullPointerException("Null arguments.");
        if(height < 0.0 || length < 0.0) throw new IllegalArgumentException("Negative height/length.");
        this.label = label;
        this.position = position;
        this.height = height;
        this.length = length;
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public Position getCentralPosition() {
        return position;
    }

    @Override
    public Double getSurface() {
        return height * length;
    }

    @Override
    public final FigureType getFigureType() {
        return FigureType.RECTANGLE;
    }

    @Override
    public boolean coversPosition(Position position) {
        Double thisX = this.position.getX();
        Double thisY = this.position.getY();
        Double otherX = position.getX();
        Double otherY = position.getY();
        return (otherX >= thisX - length/2 && otherX <= thisX + length/2)
                && (otherY >= thisY - height/2 && otherY <= thisY + height/2);
    }

    public double[] getParameters() {
        return new double[]{position.getX(), position.getY(), length, height};
    }

}
