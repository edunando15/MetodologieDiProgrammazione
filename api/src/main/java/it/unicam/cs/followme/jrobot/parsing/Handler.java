package it.unicam.cs.followme.jrobot.parsing;

import it.unicam.cs.followme.jrobot.model.Direction;
import it.unicam.cs.followme.jrobot.model.MotionlessArea;
import it.unicam.cs.followme.jrobot.model.MovableItem;
import it.unicam.cs.followme.jrobot.util.Pair;
import it.unicam.cs.followme.utilities.RobotCommand;

import java.util.ArrayList;
import java.util.List;

/**
 * Instances of this class are used to collect the robots' instructions in a simulation.
 * The instructions are collected in FIFO order, so the first instruction read from the Parser
 * will be the first instruction executed.
 */
public class Handler<A extends MotionlessArea, I extends MovableItem<Direction>>
        implements CommandsHandler {

    /**
     * Ordered list that contains the commands parsed from the Parser
     * associated to their arguments.
     */
    private ArrayList<Pair<RobotCommand, String[]>> commands;

    @Override
    public void parsingStarted() { commands = new ArrayList<>(); }

    @Override
    public void parsingDone() {
        commands.add(null); // Used to signal the end of commands.
    }

    @Override
    public void moveCommand(double[] args) { commands.add(new Pair<>(RobotCommand.MOVE, obtainStringArrayFrom(args))); }

    @Override
    public void moveRandomCommand(double[] args) { commands.add(new Pair<>(RobotCommand.MOVE, obtainStringArrayFrom(args))); }

    @Override
    public void signalCommand(String label) { commands.add(new Pair<>(RobotCommand.SIGNAL, new String[]{label})); }

    @Override
    public void unsignalCommand(String label) { commands.add(new Pair<>(RobotCommand.UNSIGNAL, new String[]{label})); }

    @Override
    public void followCommand(String label, double[] args) {
        String[] parameters = obtainStringArrayFrom(args);
        commands.add(new Pair<>(RobotCommand.FOLLOW, new String[]{label, parameters[0], parameters[1]}));
    }

    @Override
    public void stopCommand() { commands.add(new Pair<>(RobotCommand.STOP, new String[]{})); }

    @Override
    public void continueCommand(int s) { commands.add(new Pair<>(RobotCommand.CONTINUE, new String[]{String.valueOf(s), String.valueOf(s)})); }

    @Override
    public void repeatCommandStart(int n) { commands.add(new Pair<>(RobotCommand.REPEAT, new String[]{String.valueOf(n), String.valueOf(n)})); }

    @Override
    public void untilCommandStart(String label) { commands.add(new Pair<>(RobotCommand.UNTIL, new String[]{label})); }

    @Override
    public void doForeverStart() { commands.add(new Pair<>(RobotCommand.FOREVER, new String[]{})); }

    @Override
    public void doneCommand() { commands.add(new Pair<>(RobotCommand.DONE, new String[]{})); }

    /**
     * Creates a String array starting from a double array.
     */
    private String[] obtainStringArrayFrom(double[] args) {
        String[] result = new String[args.length];
        for(int i = 0; i < result.length; i++) {
            result[i] = String.valueOf(args[i]);
        }
        return result;
    }

    @Override
    public List<Pair<RobotCommand, String[]>> getReadCommands() {
        return commands;
    }
}
