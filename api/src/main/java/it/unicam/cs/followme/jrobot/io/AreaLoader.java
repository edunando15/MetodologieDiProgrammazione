package it.unicam.cs.followme.jrobot.io;

import it.unicam.cs.followme.jrobot.model.MotionlessArea;
import it.unicam.cs.followme.jrobot.model.Position;
import it.unicam.cs.followme.utilities.FollowMeParserException;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

/**
 * This functional interface is used to create the areas that
 * have been read in a File from a Parser.
 */
@FunctionalInterface
public interface AreaLoader<A extends MotionlessArea> {

    /**
     * This method is used to obtain a map that associates a Shape to a Position.
     * @param path The considered file's path.
     * @return A map that associates areas to positions.
     */
    Map<A, Position> createAreasMap(Path path) throws FollowMeParserException, IOException;

}
