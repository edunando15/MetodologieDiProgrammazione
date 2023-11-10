package it.unicam.cs.followme.jrobot.io;

import it.unicam.cs.followme.jrobot.model.Direction;
import it.unicam.cs.followme.jrobot.model.Environment;
import it.unicam.cs.followme.jrobot.model.MotionlessArea;
import it.unicam.cs.followme.jrobot.model.MovableItem;
import it.unicam.cs.followme.jrobot.model.simulation.SimulationEnvironment;
import it.unicam.cs.followme.utilities.FollowMeParserException;

import java.io.File;
import java.io.IOException;

/**
 * Instances of this class are used to load an Environment, starting
 * from two files containing the items' specs and areas' specs.
 */
public class SimulationEnvironmentLoader implements EnvironmentLoader<MotionlessArea, MovableItem<Direction>> {

    private final AreaLoader<MotionlessArea> areaLoader;

    private final MovableItemLoader<MovableItem<Direction>> movableItemLoader;

    /**
     * Constructs an Environment Loader.
     * @param areaLoader The Area Loader.
     * @param movableItemLoader The Movable Item Loader.
     */
    public SimulationEnvironmentLoader(AreaLoader<MotionlessArea> areaLoader, MovableItemLoader<MovableItem<Direction>> movableItemLoader) {
        this.areaLoader = areaLoader;
        this.movableItemLoader = movableItemLoader;
    }

    @Override
    public Environment<MotionlessArea, MovableItem<Direction>> getEnvironment(File areasFile, File itemsFile) throws FollowMeParserException, IOException {
        return new SimulationEnvironment<>(areaLoader.createAreasMap(areasFile.toPath()),
                movableItemLoader.parse(itemsFile));
    }
}
