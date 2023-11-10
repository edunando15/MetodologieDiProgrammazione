package it.unicam.cs.followme.jrobot.io;

import it.unicam.cs.followme.jrobot.model.Direction;
import it.unicam.cs.followme.jrobot.model.Environment;
import it.unicam.cs.followme.jrobot.model.MotionlessArea;
import it.unicam.cs.followme.jrobot.model.MovableItem;
import it.unicam.cs.followme.utilities.FollowMeParserException;

import java.io.File;
import java.io.IOException;

/**
 * Classes implementing this interface will provide a way to create an Environment,
 * starting from files containing the specs of items and areas.
 */
public interface EnvironmentLoader<A extends MotionlessArea, I extends MovableItem<Direction>> {

    /**
     * This method is used to obtain an Environment starting from specs files.
     * @param areasFile File containing areas' specs.
     * @param itemsFile File containing items' specs.
     * @return An Environment with the given arguments.
     */
    Environment<A, I> getEnvironment(File areasFile, File itemsFile) throws FollowMeParserException, IOException;

}
