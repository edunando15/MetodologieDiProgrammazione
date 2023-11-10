package it.unicam.cs.followme.jrobot.io;


import it.unicam.cs.followme.jrobot.model.MotionlessArea;
import it.unicam.cs.followme.jrobot.model.Position;
import it.unicam.cs.followme.jrobot.model.simulation.Circle;
import it.unicam.cs.followme.jrobot.model.simulation.Rectangle;
import it.unicam.cs.followme.utilities.FollowMeParser;
import it.unicam.cs.followme.utilities.FollowMeParserException;
import it.unicam.cs.followme.utilities.ShapeData;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Instances of this class are used to create a Map associating areas to their
 * central position, starting from a file containing the specs.
 */
public class MotionlessAreaLoader implements AreaLoader<MotionlessArea> {

    /**
     * The Parser for this loader.
     */
    private final FollowMeParser parser;

    public MotionlessAreaLoader(FollowMeParser parser) {
        this.parser = parser;
    }

    @Override
    public Map<MotionlessArea, Position> createAreasMap(Path path) throws FollowMeParserException, IOException {
        return parser.parseEnvironment(path).stream() // Stream of Shape Data.
                .map(this::createAreaFrom) // Stream containing Motionless areas.
                .collect(Collectors.toMap(area -> area, MotionlessArea::getCentralPosition));
    }

    /**
     * This method is used to create a Motion Less Area from a
     * ShapeData.
     * @param shape The shape parsed from a file.
     * @return The Motionless Area representing the strings.
     * @throws IllegalArgumentException If the figure is not supported.
     */
    private MotionlessArea createAreaFrom(ShapeData shape) {
        return switch (shape.shape()) {
            case "RECTANGLE" -> new Rectangle(shape.label(), // Label.
                    new Position(shape.args()[0], shape.args()[1]),
                    shape.args()[2], // Height.
                    shape.args()[3]); // Length.
            case "CIRCLE"    -> new Circle(shape.label(), // Label.
                    new Position(shape.args()[0], shape.args()[1]),
                    shape.args()[2]); // Radius.
            default -> throw new IllegalArgumentException("Unsupported area.");
        };
    }
}
