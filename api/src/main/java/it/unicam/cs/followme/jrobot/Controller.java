package it.unicam.cs.followme.jrobot;

import it.unicam.cs.followme.jrobot.io.*;
import it.unicam.cs.followme.jrobot.model.*;
import it.unicam.cs.followme.jrobot.model.simulation.CommandsApplier;
import it.unicam.cs.followme.jrobot.model.simulation.SimulationEnvironment;
import it.unicam.cs.followme.jrobot.parsing.CommandsHandler;
import it.unicam.cs.followme.jrobot.parsing.CommandsIterator;
import it.unicam.cs.followme.jrobot.parsing.Handler;
import it.unicam.cs.followme.utilities.FollowMeParser;
import it.unicam.cs.followme.utilities.FollowMeParserException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Instances of this class are used to control the execution of a simulation.
 */
public class Controller<A extends MotionlessArea, I extends MovableItem<Direction>> {

    /**
     * The simulation's time interval for each step.
     */
    private double timeInterval;

    private List<CommandsIterator<A, I>> iterators;

    /**
     * The Environment containing all the items in the simulation.
     */
    private Environment<A, I> environment;

    /**
     * The loader for the Environment.
     */
    private EnvironmentLoader<A, I> environmentLoader;

    /**
     * The movements' applier.
     */
    private Applier<A, I> applier;

    /**
     * The Commands Loader.
     */
    private CommandsLoader<A, I> commandsLoader;

    /**
     * Returns a Controller with the given arguments and default parameters.
     * @param timeInterval The considered time interval.
     * @return A Controller with default Handler and default Environment Loader.
     */
    public static Controller<MotionlessArea, MovableItem<Direction>> getDefaultController(double timeInterval) {
        CommandsHandler handler = new Handler<>();
        FollowMeParser parser = new FollowMeParser(handler);
        return new Controller<>(timeInterval,
                new SimulationEnvironmentLoader(new MotionlessAreaLoader(parser), new ItemLoader()),
                new CommandsIteratorLoader(handler),
                new CommandsApplier<>(timeInterval));
    }

    /**
     * Constructs a Controller with the given arguments.
     * @param timeInterval The considered time interval.
     * @param environmentLoader The Environment Loader.
     * @param commandsLoader The Commands Loader.
     * @param applier The Applier for commands.
     */
    public Controller(double timeInterval, EnvironmentLoader<A, I> environmentLoader, CommandsLoader<A, I> commandsLoader, Applier<A, I> applier) {
        this.environment = new SimulationEnvironment<>();
        this.iterators = new ArrayList<>();
        this.timeInterval = timeInterval;
        this.environmentLoader = environmentLoader;
        this.commandsLoader = commandsLoader;
        this.applier = applier;
    }

    /**
     * This method is used to change the time interval.
     * @param timeInterval The new time interval.
     * @throws IllegalArgumentException If the time
     * interval is negative or equal to 0.
     */
    public void setTimeInterval(double timeInterval) {
        this.applier.setTimeInterval(timeInterval);
    }

    /**
     * This method is used to compute the next state of the
     * Environment, according to its items and their iterators.
     * @return A new Environment containing the items at the next
     * step of the simulation.
     */
    public Environment<A, I> next() throws FollowMeParserException {
        this.environment = applier.apply(environment, iterators);
        return this.environment;
    }

    public Environment<A, I> getEnvironment() {
        return environment;
    }

    public Map<I, Position> getItemsMap() { return environment.getItemsMap(); }

    public Map<A, Position> getAreasMap() { return environment.getAreasMap(); }

    /**
     * This method is used to create an Environment from specs files.
     * @param areasFile The File containing the areas' specs.
     * @param itemsFile The File containing the items' specs.
     */
    public void openEnvironment(File areasFile, File itemsFile) throws FollowMeParserException, IOException {
        this.environment = environmentLoader.getEnvironment(areasFile, itemsFile);
    }

    /**
     * This method is used to create an Environment from a File containing
     * the figures' specs and a random builder.
     * @param areasFile File containing figures' specs.
     * @param builder A Random Robot Generator Builder, in order to write
     * a File with random parameters for items.
     */
    public void openEnvironment(File areasFile, RandomRobotGeneratorBuilder builder) throws IOException, FollowMeParserException {
        RandomRobotGenerator generator = new RandomRobotGenerator(builder);
        generator.generateRandomRobots();
        openEnvironment(areasFile, generator.getPath().toFile());
    }

    /**
     * This method is used to create the Commands iterators.
     * @param file A File containing the commands.
     * @see CommandsLoader
     */
    public void openCommands(File file) throws FollowMeParserException, IOException {
        iterators = commandsLoader.getIterators(file, this.environment.getItemsMap().keySet().stream().toList(), timeInterval);
    }

    /**
     * This method is used to tell if there's another command or not.
     */
    public boolean hasNext() {
        for(CommandsIterator<A, I> iterator : iterators) {
            if(iterator.hasNext()) return true;
        }
        return false;
    }

}