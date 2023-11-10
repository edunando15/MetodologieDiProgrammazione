package it.unicam.cs.followme.app;

import it.unicam.cs.followme.jrobot.Controller;
import it.unicam.cs.followme.jrobot.model.Direction;
import it.unicam.cs.followme.jrobot.model.MotionlessArea;
import it.unicam.cs.followme.jrobot.model.MovableItem;
import it.unicam.cs.followme.jrobot.model.Position;
import it.unicam.cs.followme.utilities.FollowMeParserException;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This class is used to represent the stage in which the simulation starts.
 */
public class SimulationWindow {

    public static final int WIDTH = 500;

    public static final int HEIGHT = 500;

    /**
     * List containing the current items.
     */
    private List<Circle> currentItems;

    Map<MotionlessArea, Position> areas;

    @FXML
    private Button startButton;

    @FXML
    private Button zoomInButton;

    @FXML
    private Button zoomOutButton;

    @FXML
    private Group group;
    private NumberAxis xAxis;

    private NumberAxis yAxis;

    private Controller<MotionlessArea, MovableItem<Direction>> controller;

    @FXML
    private Button nextButton;

    /**
     * This method is used to initialize the variables.
     */
    public void initialize() {
        xAxis = new NumberAxis(-10.0, 10.0, 1.0);
        yAxis = new NumberAxis(-10.0, 10.0, 1.0);
        nextButton.setDisable(true);
        initializeXNumberAxis(xAxis);
        initializeYNumberAxis(yAxis);
        group.setLayoutX(group.getLayoutX() + 20);
        group.setLayoutY(group.getLayoutY() + 50);
    }

    @FXML
    public void onStartButton() throws InterruptedException {
        currentItems = new ArrayList<>();
        writeRobotsPositions(controller.getItemsMap());
        writeAreas();
        startButton.setDisable(true);
        nextButton.setDisable(false);
    }

    /**
     * This method is used to initialize the "x" axis in order
     * to center it in the window.
     */
    private void initializeXNumberAxis(NumberAxis axis) {
        axis.setMinorTickCount(10);
        axis.setSide(Side.BOTTOM);
        axis.setPrefSize(WIDTH, HEIGHT);
        axis.setLayoutX(-WIDTH/2.0);
        axis.setLayoutY(0);
        group.getChildren().add(axis);
    }

    /**
     * This method is used to initialize the "y" axis in order
     * to center it in the window.
     */
    private void initializeYNumberAxis(NumberAxis axis) {
        axis.setMinorTickCount(10);
        axis.setSide(Side.LEFT);
        axis.setPrefSize(WIDTH, HEIGHT);
        axis.setLayoutX(-WIDTH);
        axis.setLayoutY(-HEIGHT/2.0);
        group.getChildren().add(axis);
    }

    public void setController(Controller<MotionlessArea, MovableItem<Direction>> controller) {
        this.controller = controller;
    }

    @FXML
    public void onNextButton(Event event) throws FollowMeParserException {
        try {
            if(controller.hasNext()) {
                group.getChildren().removeAll(currentItems);
                currentItems.clear();
                writeRobotsPositions(controller.next().getItemsMap());
            }
        } catch (IllegalStateException e) {
            createErrorDialogWindow();
            nextButton.setDisable(true);
        }
    }

    /**
     * This method is invoked when the user wants to zoom in:
     * the bounds must be changed and everything must be
     * written again.
     */
    @FXML
    public void onZoomInButton(Event event) {
        resizeAxis(-5.0, 5.0, -5.0, 5.0);
        rewriteAll();
    }

    @FXML
    public void onZoomOutButton(Event event) {
        resizeAxis(5.0, -5.0, 5.0, -5.0);
        rewriteAll();
    }

    private void writeRobotsPositions(Map<MovableItem<Direction>, Position> items) {
        for(Map.Entry<MovableItem<Direction>, Position> entry : items.entrySet()) {
            Circle c = new Circle( convertXCoordinate(entry.getValue().getX()), convertYCoordinate(entry.getValue().getY()), 2.5, Color.BLACK);
            currentItems.add(c);
        }
        group.getChildren().addAll(currentItems);
    }

    private void writeAreas() {
        areas = controller.getAreasMap();
        for(Map.Entry<MotionlessArea, Position> area : areas.entrySet()) {
            switch (area.getKey().getFigureType()) {
                case CIRCLE: createCircle(area.getKey().getParameters()); break;
                case RECTANGLE: createRectangle(area.getKey().getParameters()); break;
            }
        }
    }

    /**
     * This method is used to create a circle.
     * @param args The circle's parameters.
     */
    private void createCircle(double[] args) {
        Circle circle = new Circle(convertXCoordinate(args[0]), convertYCoordinate(args[1]), convertXCoordinate(args[2]), Color.YELLOW);
        circle.setOpacity(0.7);
        group.getChildren().add(circle);
    }

    /**
     * This method is used to create a rectangle.
     * @param args The rectangle's parameters.
     */
    private void createRectangle(double[] args) {
        Rectangle rectangle = new Rectangle(convertXCoordinate(args[2]), -convertYCoordinate(args[3]), Color.RED);
        rectangle.setX(convertXCoordinate(args[0] - args[2] / 2)); // x - length / 2.
        rectangle.setY(convertYCoordinate(args[1] + args[3] / 2)); // y + height / 2.
        rectangle.setOpacity(0.7);
        group.getChildren().add(rectangle);
    }

    /**
     * This method is used to convert an x coordinate into a coordinate
     * available for the view.
     */
    private double convertXCoordinate(double x) {
        double length = xAxis.getUpperBound() - xAxis.getLowerBound(); // Ax length.
        double proportion = xAxis.getWidth() / length; // Proportion.
        return x * proportion;
    }

    /**
     * This method is used to convert a y coordinate into a coordinate
     * available for the view.
     */
    private double convertYCoordinate(double y) {
        double length = yAxis.getUpperBound() - yAxis.getLowerBound(); // Ax length.
        double proportion = yAxis.getWidth() / length;
        return -y * proportion;
    }

    private void createErrorDialogWindow() {
        Stage error = new Stage();
        error.initModality(Modality.WINDOW_MODAL);
        VBox vbox = new VBox(new Text("The Simulation Ended"));
        vbox.setLayoutX(10.0);
        vbox.setLayoutY(10.0);
        vbox.setAlignment(Pos.CENTER);
        error.setScene(new Scene(vbox));
        error.show();
    }

    /**
     * This method is used to resize the axis dimensions, as needed.
     * @param deltaUpperX The increment for "x" axis upper bound.
     * @param deltaLowerX The increment for "x" axis lower bound.
     * @param deltaUpperY The increment for "y" axis upper bound.
     * @param deltaLowerY The increment for "y" axis lower bound.
     */
    private void resizeAxis(double deltaUpperX, double deltaLowerX, double deltaUpperY, double deltaLowerY) {
        xAxis.setUpperBound(xAxis.getUpperBound() + deltaUpperX);
        xAxis.setLowerBound(xAxis.getLowerBound() + deltaLowerX);
        yAxis.setUpperBound(yAxis.getUpperBound() + deltaUpperY);
        yAxis.setLowerBound(yAxis.getLowerBound() + deltaLowerY);
    }

    /**
     * This method is used to rewrite all the items.
     */
    private void rewriteAll() {
        group.getChildren().removeAll(group.getChildren());
        group.getChildren().add(xAxis);
        group.getChildren().add(yAxis);
        currentItems.clear();
        areas.clear();
        writeRobotsPositions(controller.getItemsMap());
        writeAreas();
    }

}
