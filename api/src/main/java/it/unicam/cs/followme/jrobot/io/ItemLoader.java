package it.unicam.cs.followme.jrobot.io;

import it.unicam.cs.followme.jrobot.model.Direction;
import it.unicam.cs.followme.jrobot.model.MovableItem;
import it.unicam.cs.followme.jrobot.model.Position;
import it.unicam.cs.followme.jrobot.model.simulation.Robot;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Instances of this class are used to parse a text file
 * into parameters for robots.
 */
public class ItemLoader implements MovableItemLoader<MovableItem<Direction>> {

    @Override
    public Map<MovableItem<Direction>, Position> parse(String text) throws IOException {
        String[] lines = text.trim().split("\n");
        Set<Map.Entry<Robot<Direction>, Position>> entrySet = new HashSet<>();
        for (String line : lines) {
            if(!line.isBlank()) { entrySet.add(parseLine(line)); }
        }
        return entrySet.stream()
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    /**
     * This method is used to parse a String containing the parameters
     * and initial Position of a Movable Item.
     * @param text The given line.
     * @return An Entry with Movable Item as key and Position as value.
     * @throws IllegalArgumentException If any parameter is not correct.
     */
    private Map.Entry<Robot<Direction>, Position> parseLine(String text) throws IllegalArgumentException, IOException {
        String[] line = text.split(" ");
        if(line[0].equalsIgnoreCase("ROBOT")) {
            return new AbstractMap.SimpleEntry<>(
                    new Robot<>(new Direction(Double.parseDouble(line[1]), Double.parseDouble(line[2])),
                            Double.parseDouble(line[3]),
                            line[4]),
                    new Position(Double.parseDouble(line[5]), Double.parseDouble(line[6])));
        } else throw new IllegalArgumentException();
    }
}
