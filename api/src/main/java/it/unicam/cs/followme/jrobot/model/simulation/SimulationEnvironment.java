package it.unicam.cs.followme.jrobot.model.simulation;

import it.unicam.cs.followme.jrobot.model.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This class is used to represent a Simulation Environment.
 * @param <A> The type representing a MotionLessArea.
 * @param <I> The type representing a MovableItem.
 */
public class SimulationEnvironment<A extends MotionlessArea,
        I extends MovableItem<Direction>> implements Environment<A, I> {

    /**
     * This variable is used to store the areas
     * (associates a MotionLessArea to a Position).
     */
    private final Map<A, Position> areasMap;

    /**
     * This variable is used to store the items
     * (associates a MovableItem to a Position).
     */
    private final Map<I, Position> itemsMap;

    /**
     * This is the constructor for a Simulation Environment.
     * @param areasMap The Map associating positions to areas.
     * @param itemsMap The Map associating positions to items.
     */
    public SimulationEnvironment(Map<A, Position> areasMap, Map<I, Position> itemsMap) {
        this.areasMap = new HashMap<>(areasMap);
        this.itemsMap = new HashMap<>(itemsMap);
    }

    public SimulationEnvironment() {
        this(new HashMap<>(), new HashMap<>());
    }

    /**
     * Construct a Simulation Environment from a
     * starting one.
     * @param e The Simulation Environment whose
     * items will be copied in the new.
     */
    public SimulationEnvironment(SimulationEnvironment<A, I> e) {
        this(e.getAreasMap(), e.getItemsMap());
    }

    @Override
    public Map<A, Position> getAreasMap() {
        return new HashMap<>(areasMap);
    }

    @Override
    public Map<I, Position> getItemsMap() {
        return new HashMap<>(itemsMap);
    }

    @Override
    public void addArea(A area) throws NullPointerException, IllegalArgumentException {
        if(area == null) throw new NullPointerException("Null argument.");
        areasMap.put(area, area.getCentralPosition());
    }

    @Override
    public void addItem(I item, Position itemPosition) throws NullPointerException, IllegalArgumentException {
        if(item == null || itemPosition == null) throw new NullPointerException("Null arguments.");
        itemsMap.put(item, itemPosition);
    }

    @Override
    public void updateMovableItemPosition(I item, Position newItemPosition) throws NullPointerException {
        if(item == null || newItemPosition == null) throw new NullPointerException("Null arguments.");
        itemsMap.replace(item, newItemPosition);
    }

    @Override
    public List<A> getAreasAt(Position position) {
        return areasMap.keySet().stream()
                .parallel()
                .filter(area -> area.coversPosition(position))
                .toList();
    }

    @Override
    public List<I> getMovableItemsAt(Position position) {
        return itemsMap.entrySet().stream()
                .parallel()
                .filter(entry -> entry.getValue().equals(position))
                .map(Map.Entry::getKey)
                .toList();
    }

    @Override
    public Position getItemPositionOf(I item) {
        return itemsMap.get(item);
    }

    @Override
    public void makeItemsSignal(String label) throws NullPointerException {
        if(label == null) throw new NullPointerException("Null argument.");
        Set<I> items = itemsMap.keySet();
        for(I item : items) {
            if(item.getCondition().equals(label)) item.signal();
        }
    }

    @Override
    public void makeItemsUnSignal(String label) {
        if(label == null) throw new NullPointerException("Null argument.");
        Set<I> items = itemsMap.keySet();
        for(I item : items) {
            if(item.getCondition().equals(label)) item.unSignal();
        }
    }

    @Override
    public SimulationEnvironment<A, I> getInstance(Map<I, Position> items) {
        return new SimulationEnvironment<>(getAreasMap(), items);
    }

}
