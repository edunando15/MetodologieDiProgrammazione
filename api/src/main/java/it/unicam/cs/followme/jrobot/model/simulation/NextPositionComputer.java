package it.unicam.cs.followme.jrobot.model.simulation;

import it.unicam.cs.followme.jrobot.model.*;

public class NextPositionComputer <A extends MotionlessArea,
        I extends MovableItem<Direction>> implements PositionComputer<A, I> {

    @Override
    public Position computeNextPosition(I item, Double timeInterval, Environment<A, I> e) {
        Double module = moduleCalculator(item);
        return computePosition(e.getItemPositionOf(item), item, timeInterval);
    }
}
