package it.unicam.cs.followme.app;

import it.unicam.cs.followme.jrobot.Controller;
import it.unicam.cs.followme.jrobot.io.RandomRobotGeneratorBuilder;
import it.unicam.cs.followme.jrobot.model.Direction;
import it.unicam.cs.followme.jrobot.model.MotionlessArea;
import it.unicam.cs.followme.jrobot.model.MovableItem;
import it.unicam.cs.followme.jrobot.model.Position;
import it.unicam.cs.followme.utilities.FollowMeParserException;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * JAVAFX Controller for a simulation.
 */
public class SimulationController {

    private Parent root;

    public static final double HEIGHT = 600;

    public static final double WIDTH = 600;

    private File commandsFile = new File("src\\main\\resources\\commandsExample.txt");

    private File areasFile = new File("src\\main\\resources\\figuresExample.txt");

    private File robotsFile;

    private double timeInterval;

    private RandomRobotGeneratorBuilder builder = new RandomRobotGeneratorBuilder();

    @FXML
    private TextField timeIntervalField;

    @FXML
    private TextField numberOfRobotsField;

    @FXML
    private TextField xCoordinateRangeField;

    @FXML
    private TextField yCoordinateRangeField;

    @FXML
    private TextField speed;

    @FXML
    private TextField label;

    @FXML
    private Button uploadEnvironmentButton;

    @FXML
    private Button uploadCommandsButton;

    @FXML
    private Button saveButton;

    @FXML
    private Button startButton;

    @FXML
    private Button uploadRobotsButton;

    private Controller<MotionlessArea, MovableItem<Direction>> controller;

    private Map<MovableItem<Direction>, Position> items;

    public SimulationController() {
        items = new HashMap<>();
    }

    public void initialize() {
        disableButtons(startButton);
    }

    /**
     * This method is invoked when the save button is pressed.
     */
    @FXML
    public void onSaveButton() {
        try{
            initializeTime();
            controller = Controller.getDefaultController(timeInterval);
            initializeBuilder(); // Sets the parameters according to the form.
            initialiseController();
            controller.openCommands(commandsFile);
            disableButtons(saveButton, uploadCommandsButton, uploadEnvironmentButton, uploadRobotsButton);
            startButton.setDisable(false);
        } catch (NumberFormatException e) { writeException(e, "user form: not a number."); }
        catch (IOException e) { writeException(e, "writing random robots."); }
        catch (FollowMeParserException e) { writeException(e, "parser."); }
    }

    /**
     * This method is invoked when the start button is pressed.
     */
    @FXML
    public void onStartButton(Event event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/simulation.fxml"));
        root = loader.load();
        SimulationWindow simulationWindow = loader.getController();
        simulationWindow.setController(controller);
        Stage stage = (Stage)(((Node)(event.getSource())).getScene().getWindow());
        stage.setTitle("Simulation App");
        stage.setScene(new Scene(root, SimulationController.WIDTH, SimulationController.HEIGHT));
        stage.setResizable(true);
        stage.show(); // Changes the current window (stage).
    }

    /**
     * This method is invoked to set the time, in both controllers.
     */
    private void initializeTime() {
        timeInterval = Double.parseDouble(timeIntervalField.getText());
    }

    /**
     * This method is used to initialise the Builder. It sets all its
     * parameters according to the variables in this class.
     */
    private void initializeBuilder() {
        builder = new RandomRobotGeneratorBuilder();
        builder.setN(Integer.parseInt(numberOfRobotsField.getText()));
        builder.setXCoordinate(Double.parseDouble(xCoordinateRangeField.getText()));
        builder.setYCoordinate(Double.parseDouble(yCoordinateRangeField.getText()));
        builder.setSpeed(Double.parseDouble(speed.getText()));
        builder.setCondition(label.getText());
    }


    @FXML
    public void onUploadEnvironmentButton(Event event) throws FollowMeParserException {
        areasFile = openFile(event, "Environment");
    }

    @FXML
    public void onUploadCommandsButton(Event event) {
        commandsFile = openFile(event, "Commands");;
    }

    @FXML
    public void onUploadRobotsButton(Event event) {
        robotsFile = openFile(event, "Robots");
    }

    /**
     * This method is invoked when is needed to upload a file
     * for the simulation.
     */
    private File openFile(Event event, String type) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Open " + type + " File");
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Txt Files", "*.txt"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        return chooser.showOpenDialog(((Node) event.getSource()).getScene().getWindow());
    }

    /**
     * This method is used to write an Exception.
     * @param e The Exception thrown.
     * @param message An auxiliary message.
     */
    private void writeException(Exception e, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error in " + message);
        alert.setHeaderText(e.getMessage());
    }

    /**
     * This method is used to initialise the controller,
     * according to the files loaded.
     */
    private void initialiseController() throws IOException, FollowMeParserException {
        if(robotsFile != null) controller.openEnvironment(areasFile, robotsFile);
        else controller.openEnvironment(areasFile, builder);
    }

    /**
     * This method is used to disable the given buttons.
     */
    private void disableButtons(Button ... buttons) {
        for(Button button : buttons) button.setDisable(true);
    }

}
