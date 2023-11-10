package it.unicam.cs.followme.jrobot.parsing;

import it.unicam.cs.followme.jrobot.io.RandomRobotGenerator;
import it.unicam.cs.followme.jrobot.model.*;
import it.unicam.cs.followme.jrobot.model.simulation.SimulationSpace;
import it.unicam.cs.followme.jrobot.util.Pair;
import it.unicam.cs.followme.utilities.FollowMeParserException;
import it.unicam.cs.followme.utilities.RobotCommand;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Stack;

/**
 * Instances of this class represent an Iterator for commands.
 * Converts the commands into applicable movements.
 * Before invoking {@link #next(Environment)} method, {@link #hasNext()} method should be invoked,
 * because if {@link #hasNext()} returns false, an {@link IllegalStateException} will be thrown.
 */
public class CommandsIterator<A extends MotionlessArea, I extends MovableItem<Direction>> {

    /**
     * The commands for this Movable Item.
     */
    private final List<Pair<RobotCommand, String[]>> commands;

    /**
     * The Stack containing the loops indexes.
     */
    private final Stack<Integer> loopsIndexes;

    /**
     * The original Movable Item for this Commands Iterator.
     */
    private final I item;

    /**
     * This variable is used to memorize what instruction
     * has been executed.
     */
    private int counter;

    /**
     * This variable represents the considered time interval.
     */
    private final double timeInterval;

    public CommandsIterator(List<Pair<RobotCommand, String[]>> commands, I item, double timeInterval) {
        this.commands = initialiseCommands(commands);
        this.item = item;
        this.timeInterval = timeInterval;
        loopsIndexes = new Stack<>();
        counter = 0;
    }

    /**
     * This method is used to tell if there are other
     * commands to be executed.
     * @return True if there are other commands that
     * haven't been executed yet, false otherwise.
     */
    public boolean hasNext() {
        return commands.get(counter) != null;
    }

    /**
     * This method is used to compute the next command of an Item.
     * @param environment The considered Environment in which the
     * Item is.
     * @throws IllegalStateException If {@link #hasNext()} method
     * returns false.
     */
    public void next(Environment<A, I> environment) throws IllegalStateException, FollowMeParserException {
        if(!hasNext()) throw new IllegalStateException("hasNext() returned false.");
        convertCommandIntoItemParameters(commands.get(counter), environment);
    }

    /**
     * This method is used to convert commands into parameters for
     * a Movable Item.
     */
    private void convertCommandIntoItemParameters(Pair<RobotCommand, String[]> commands, Environment<A, I> e) throws FollowMeParserException {
        switch(commands.first()) {
            case MOVE -> genericMoveRead(e, commands);
            case STOP -> stopRead(commands.first());
            case SIGNAL -> signalParsed(commands.second());
            case UNSIGNAL -> unsignalRead(commands.second());
            case FOLLOW -> followRead(e, commands.second());
            case UNTIL -> untilRead(e, commands);
            case REPEAT -> repeatRead(e, commands.second());
            case FOREVER -> foreverRead(e);
            case CONTINUE -> continueRead(commands.second(), e);
            case DONE -> doneRead(e);
        }
    }

    /**
     * This method is invoked when a generic Move command is read.
     * It decides what method must be invoked.
     */
    private void genericMoveRead(Environment<A, I> e, Pair<RobotCommand, String[]> pair) {
        if(pair.second().length == 3) moveRead(pair.second()); // It's MOVE x y s.
        else moveRandomRead(pair.second(), e); // It's a MOVE RANDOM x1 x2 y1 y2 s.
        counter++;

    }

    /**
     * This method is invoked when a MOVE command is parsed.
     * It is assumed that the given Direction is correct.
     * @param elements The MOVE parameters: x y s,
     * where (x, y) is a Direction and s is a speed.
     */
    private void moveRead(String[] elements) {
        Direction d = new Direction(Double.parseDouble(elements[0]), Double.parseDouble(elements[1]));
        item.setDirection(d);
        item.setSpeed(Double.parseDouble(elements[2]));
    }

    /**
     * This method is invoked when a MOVE RANDOM command
     * is parsed.
     * @param elements The MOVE RANDOM parameters:
     * x1 x2 y1 y2 s.
     */
    private void moveRandomRead(String[] elements, Environment<A, I> e) {
        Double v1 = RandomRobotGenerator.generateRandomBetween(Double.parseDouble(elements[0]), Double.parseDouble(elements[1]));
        Double v2 = RandomRobotGenerator.generateRandomBetween(Double.parseDouble(elements[2]), Double.parseDouble(elements[3]));
        DirectionComputer computer = DirectionComputer.DEFAULT_DIRECTION_COMPUTER;
        Optional<Direction> d = computer.computeDirectionOf(e.getItemPositionOf(item), new Position(v1, v2));
        if(d.isPresent()) {
            item.setDirection(d.get());
            item.setSpeed(Double.parseDouble(elements[4]));
        }
        else item.setSpeed(0.0); // Must remain where it is.
    }

    /**
     * This method is invoked when a STOP command is parsed.
     * It sets the speed of the Item at 0.0 m/s.
     */
    private void stopRead(RobotCommand command) {
        item.setSpeed(0.0);
        if(!isLoopCommand(command)) counter++;
    }

    /**
     * This method is invoked when a SIGNAL command is read.
     * @param elements The label to signal.
     */
    private void signalParsed(String[] elements) {
        item.setSpeed(0.0); // Stops the Item.
        item.setCondition(elements[0]);
        item.signal();
        counter++;
    }

    /**
     * This method is invoked when a UNSIGNAL command is read.
     * @param elements The label to unsignal.
     */
    private void unsignalRead(String[] elements) {
        if(item.getCondition().equals(elements[0])) item.unSignal();
        counter++;
    }

    /**
     * This method is invoked when a FOLLOW command is read.
     * @param elements In order, the parameters are label,
     * distance, speed.
     */
    private void followRead(Environment<A, I> e, String[] elements) {
        Space<I> space = new SimulationSpace<>();
        DirectionComputer computer = DirectionComputer.DEFAULT_DIRECTION_COMPUTER;
        Position computedPosition = space.getAveragePositionOfItems(item, Double.parseDouble(elements[1]), e, elements[0]);
        Optional<Direction> computedDirection = computer.computeDirectionOf(e.getItemPositionOf(item), computedPosition);
        if(computedDirection.isPresent()) {
            item.setDirection(computedDirection.get());
            item.setSpeed(Double.parseDouble(elements[2]));
        }
        else item.setSpeed(0.0); // The Item must remain where it is.
        counter++;
    }

    /**
     * This method is invoked when a UNTIL command is read.
     * Verifies the label in which the Item is in the Environment.
     * If it's
     * @param e The considered Environment.
     */
    private void untilRead(Environment<A, I> e, Pair<RobotCommand, String[]> command) throws FollowMeParserException {
        if(!untilVerified(e, command.second())) {
            loopsIndexes.push(counter++);
            next(e);
        }
        else {
            counter = jumpToNextDone() + 1; // The UNTIL body must be skipped.
            stopRead(command.first()); // The item must stop.
        }
    }

    /**
     * This method is invoked when a REPEAT command is read.
     * @param e The considered Environment.
     * @param elements The number of repetitions.
     */
    private void repeatRead(Environment<A, I> e, String[] elements) throws FollowMeParserException {
        int n = Integer.parseInt(elements[0]);
        if(n > 0) { // The body must be executed.
            loopsIndexes.push(counter);
            elements[0] = String.valueOf(--n); // Decrements the number.
            counter++;
        } else {
            elements[0] = elements[1]; // Reassignment of number.
            counter = jumpToNextDone() + 1; // The body must be skipped.
        }
        next(e); // Invokes the next command.
    }

    /**
     * This method is invoked when a DO FOREVER command is read.
     * @param e The considered Environment.
     */
    private void foreverRead(Environment<A, I> e) throws FollowMeParserException {
        loopsIndexes.push(counter);
        if(commands.get(counter + 1).first().equals(RobotCommand.DONE)) throw new FollowMeParserException("DONE command after FOREVER at command " + counter);
        counter++;
        next(e);
    }

    /**
     * This method is invoked when a SKIP command is read.
     * @param elements The time of execution.
     */
    private void continueRead(String[] elements, Environment<A, I> e) throws FollowMeParserException {
        double n = Double.parseDouble(elements[0]);
        if(n > 0.0) {
            n -= timeInterval;
            elements[0] = String.valueOf(n);
        } else {
            elements[0] = elements[1]; // Reassignment of number.
            counter++; // The program must go on because the skip ended.
            next(e);
        }
    }

    private void doneRead(Environment<A, I> e) throws FollowMeParserException {
        if(!loopsIndexes.isEmpty()) counter = loopsIndexes.pop(); // Removes the first index in the stack.
        else counter++; // It's over.
        next(e); // Goes to the next command.
    }

    /**
     * This method is used to tell if an Item is in an Area in which
     * the condition is satisfied.
     * @param e The considered Environment.
     * @param elements The considered label.
     * @return True if the Item is in the correct Area in the
     * considered Environment, false otherwise.
     */
    private boolean untilVerified(Environment<A, I> e, String[] elements) {
        List<A> areas = e.getAreasAt(e.getItemPositionOf(item));
        for(A area : areas) {
            if(area.getLabel().equals(elements[0])) return true;
        }
        return false; // Item is not in the correct area.
    }

    /**
     * This method is used to "jump" to the next instruction
     */
    private int jumpToNextDone() {
        int v = 1;
        for(int i = counter + 1; i < commands.size(); i++) { // Starting from the next command...
            if(isLoopCommand(commands.get(i).first())) v++;
            if(commands.get(i).first().equals(RobotCommand.DONE)) {
                if(--v == 0) return i; // Current position.
            }
        }
        return -1; // Impossible as the program was already parsed.
    }

    /**
     * This method is used to tell if a command is a loop command
     * or not.
     * @param command A command.
     * @return True if it's a loop command (REPEAT, FOREVER,
     * UNTIL), false otherwise.
     */
    private boolean isLoopCommand(RobotCommand command) {
        return switch (command) {
            case UNTIL, FOREVER, REPEAT -> true;
            default -> false;
        };
    }

    public I getItem() {
        return item;
    }

    /**
     * This method is used to construct the commands for this iterator.
     */
    private List<Pair<RobotCommand, String[]>> initialiseCommands(List<Pair<RobotCommand, String[]>> commands) {
        ArrayList<Pair<RobotCommand, String[]>> result = new ArrayList<>();
        for(Pair<RobotCommand, String[]> command : commands) {
            if(command != null) result.add(new Pair<>(command.first(), getCopyOf(command.second())));
            else result.add(null); // End of commands.
        }
        return result;
    }

    private String[] getCopyOf(String[] parameters) {
        String[] result = new String[parameters.length];
        for(int i = 0; i < parameters.length; i++) {
            result[i] = String.copyValueOf(parameters[i].toCharArray(), 0, parameters[i].length());
        }
        return result;
    }
}
