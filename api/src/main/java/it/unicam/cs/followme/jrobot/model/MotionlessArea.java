package it.unicam.cs.followme.jrobot.model;

/**
 * This interface represents an Area in a simulation.
 * An Area cannot move.
 * of the Area.
 */
public interface MotionlessArea {

    /**
     * This method is used to tell if the area covers a certain Position.
     * @param position The Position that is desired to know if is covered
     * from the Area.
     * @return True if the Area covers the given Position, false otherwise.
     */
    boolean coversPosition(Position position);

    /**
     * This method is used to get the label of this Area.
     * @return The label of this Area.
     */
    String getLabel();

    /**
     * This method is used to obtain the figure's center
     * Position.
     * @return The figure's center Position.
     */
    Position getCentralPosition();

    /**
     * This method is used to obtain the surface occupied by an Area.
     * @return The surface of the Area, expressed in square meters.
     */
    Double getSurface();

    /**
     * This method is used to obtain the Figure Type of the Area.
     * @return The Figure Type of the Area.
     */
    FigureType getFigureType();

    /**
     * This method is used to obtain the parameters of the Area.
     * @return The parameters of the area, in a decided order.
     */
    double[] getParameters();

}
