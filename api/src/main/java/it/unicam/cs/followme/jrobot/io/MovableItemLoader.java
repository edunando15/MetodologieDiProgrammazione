package it.unicam.cs.followme.jrobot.io;

import it.unicam.cs.followme.jrobot.model.Direction;
import it.unicam.cs.followme.jrobot.model.MovableItem;
import it.unicam.cs.followme.jrobot.model.Position;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

/**
 * This Functional Interface is used to read a File containing a description of
 * Movable items, in order to create a Map that associates the created Movable items
 * with their initial positions.
 */
@FunctionalInterface
public interface MovableItemLoader<I extends MovableItem<Direction>> {

    /**
     * This method is used to parse a File.
     * @param file The considered File.
     * @return A Map that associates Movable items
     * to their positions.
     * @throws IOException If any I/O error occur during the reading.
     */
    default Map<I, Position> parse(File file) throws IOException {
        return parse(file.toPath());
    }

    /**
     * This method is used to parse a Path.
     * @param path The considered Path.
     * @return A Map that associates Movable items to their
     * positions.
     * @throws IOException If any I/O error occur during the reading.
     */
    default Map<I, Position> parse(Path path) throws IOException {
        return parse(Files.readString(path));
    }

    /**
     * This method is used to parse a List of strings.
     * @param text A String containing the description of items.
     * @return A Map that associates items to their positions.
     * @throws IOException If any I/O error occur during the reading.
     */
    Map<I, Position> parse(String text) throws IOException;

}
