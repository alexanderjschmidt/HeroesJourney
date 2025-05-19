package heroes.journey.utils.worldgen.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import lombok.Getter;

public class WeightedRandomPicker<T> extends ArrayList<T> {
    public final List<Long> cumulativeWeights; // Stores cumulative weights
    private final Map<T,Integer> indexMap;       // Maps items to their index in `items`
    @Getter private long totalWeight;                   // Sum of all weights

    public WeightedRandomPicker() {
        this.cumulativeWeights = new ArrayList<>();
        this.indexMap = new HashMap<>();
        this.totalWeight = 0;
    }

    public WeightedRandomPicker(WeightedRandomPicker<T> possibleTiles, int weight) {
        this();
        if (possibleTiles.isEmpty()) {
            return;
        }
        addItem(possibleTiles.getFirst(), possibleTiles.cumulativeWeights.getFirst());
        for (int i = 1; i < possibleTiles.size(); i++) {
            addItem(possibleTiles.get(i), weight);
        }
    }

    public WeightedRandomPicker(WeightedRandomPicker<T> possibleTiles) {
        this();
        if (possibleTiles.isEmpty()) {
            return;
        }
        addItem(possibleTiles.getFirst(), possibleTiles.cumulativeWeights.getFirst());
        for (int i = 1; i < possibleTiles.size(); i++) {
            addItem(possibleTiles.get(i),
                possibleTiles.cumulativeWeights.get(i) - possibleTiles.cumulativeWeights.get(i - 1));
        }
    }

    public WeightedRandomPicker(WeightedRandomPicker<T> possibleTiles, boolean equalWeights) {
        this();
        if (possibleTiles.isEmpty()) {
            return;
        }
        addItem(possibleTiles.getFirst(), equalWeights ? 10 : possibleTiles.cumulativeWeights.getFirst());
        for (int i = 1; i < possibleTiles.size(); i++) {
            addItem(possibleTiles.get(i), equalWeights ?
                10 :
                (possibleTiles.cumulativeWeights.get(i) - possibleTiles.cumulativeWeights.get(i - 1)));
        }
    }

    /**
     * Adds an item with a specific weight
     */
    public void addItem(T item, long weight) {
        if (item == null) {
            throw new RuntimeException("Item cannot be null");
        }
        if (indexMap.containsKey(item)) {
            remove(item);
        }
        if (weight <= 0)
            throw new IllegalArgumentException("Weight must be positive " + weight);

        add(item);
        totalWeight += weight;
        cumulativeWeights.add(totalWeight);
        indexMap.put(item, size() - 1);
        //System.out.println(size() + " " + cumulativeWeights.size());
    }

    @Override
    public boolean removeIf(Predicate<? super T> filter) {
        boolean removed = false;
        for (int i = size() - 1; i >= 0; i--) { // Iterate in reverse to avoid index shifting
            T item = get(i);
            if (filter.test(item)) {
                remove(item);  // Call custom remove logic
                removed = true;
            }
        }
        return removed;
    }

    /**
     * Removes an item and adjusts cumulative weights
     *
     * @return
     */
    public boolean remove(Object item) {
        Integer index = indexMap.remove(item);
        if (index == null)
            return false;

        long weightToRemove =
            cumulativeWeights.get(index) - (index > 0 ? cumulativeWeights.get(index - 1) : 0);

        // Adjust all following cumulative weights
        for (int i = index + 1; i < cumulativeWeights.size(); i++) {
            cumulativeWeights.set(i, cumulativeWeights.get(i) - weightToRemove);
        }

        // Remove item and its cumulative weight
        //System.out.println(size() + " " + cumulativeWeights.size());
        super.remove((int)index);
        long removedWeight = cumulativeWeights.remove((int)index);

        // Update indexMap for shifted elements
        for (int i = index; i < size(); i++) {
            indexMap.put(get(i), i);
        }

        // Update totalWeight
        totalWeight = cumulativeWeights.isEmpty() ? 0 : cumulativeWeights.getLast();
        return true;
    }

    /**
     * Halves the weight of the given item.
     *
     * @param item the item whose weight should be halved
     * @return true if the item's weight was halved, false if the item was not found
     */
    public boolean halveWeight(T item) {
        Integer index = indexMap.get(item);
        if (index == null) {
            return false; // Item not found
        }

        long originalWeight =
            cumulativeWeights.get(index) - (index > 0 ? cumulativeWeights.get(index - 1) : 0);
        long newWeight = Math.max(1, originalWeight / 2); // Ensure weight stays positive

        remove(item);       // Remove the current item and adjust state
        addItem(item, newWeight); // Re-add it with the new halved weight
        return true;
    }

    /**
     * Selects a random item based on weight
     */
    public T getRandomItem() {
        if (isEmpty())
            throw new IllegalStateException("No items available");

        long r = (long)(Math.random() * totalWeight);
        int index = Collections.binarySearch(cumulativeWeights, r);
        if (index < 0)
            index = -index - 1; // Convert negative insertion point
        //System.out.println(size() + " " + cumulativeWeights.size());
        return get(index);
    }

    public void testDistribution() {
        HashMap<String,Integer> distribution = new HashMap<String,Integer>();
        for (T t : this) {
            distribution.put(t.toString(), 0);
        }
        for (int i = 0; i < 1000; i++) {
            T t = this.getRandomItem();
            distribution.put(t.toString(), distribution.get(t.toString()) + 1);
        }
        System.out.println("Total: " + this.totalWeight);
        long previousWeight = 0;
        for (int i = 0; i < this.size(); i++) {
            System.out.println(
                get(i).toString().charAt(0) + ": " + (cumulativeWeights.get(i) - previousWeight) + ": " +
                    distribution.get(get(i).toString()));
            previousWeight = cumulativeWeights.get(i);
        }
    }

    @Override
    public void clear() {
        super.clear();
        cumulativeWeights.clear();
        indexMap.clear();
        totalWeight = 0;
    }
}
