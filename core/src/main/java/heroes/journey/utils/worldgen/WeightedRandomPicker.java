package heroes.journey.utils.worldgen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class WeightedRandomPicker<T> extends ArrayList<T> {
    private final List<Integer> cumulativeWeights; // Stores cumulative weights
    private final Map<T,Integer> indexMap;       // Maps items to their index in `items`
    private int totalWeight;                   // Sum of all weights

    public WeightedRandomPicker() {
        this.cumulativeWeights = new ArrayList<>();
        this.indexMap = new HashMap<>();
        this.totalWeight = 0;
    }

    public WeightedRandomPicker(WeightedRandomPicker<T> possibleTiles) {
        this();
        for (int i = 0; i < possibleTiles.size(); i++) {
            addItem(possibleTiles.get(i), possibleTiles.cumulativeWeights.get(i));
        }
    }

    /**
     * Adds an item with a specific weight
     */
    public void addItem(T item, int weight) {
        if (weight <= 0)
            throw new IllegalArgumentException("Weight must be positive");

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

        int weightToRemove =
            cumulativeWeights.get(index) - (index > 0 ? cumulativeWeights.get(index - 1) : 0);

        // Adjust all following cumulative weights
        for (int i = index + 1; i < cumulativeWeights.size(); i++) {
            cumulativeWeights.set(i, cumulativeWeights.get(i) - weightToRemove);
        }

        // Remove item and its cumulative weight
        //System.out.println(size() + " " + cumulativeWeights.size());
        super.remove((int)index);
        cumulativeWeights.remove((int)index);

        // Update indexMap for shifted elements
        for (int i = index; i < size(); i++) {
            indexMap.put(get(i), i);
        }

        // Update totalWeight
        totalWeight = cumulativeWeights.isEmpty() ? 0 : cumulativeWeights.getLast();
        return true;
    }

    /**
     * Selects a random item based on weight
     */
    public T getRandomItem() {
        if (isEmpty())
            throw new IllegalStateException("No items available");

        int r = (int)(Math.random() * totalWeight);
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
        int previousWeight = 0;
        for (int i = 0; i < this.size(); i++) {
            System.out.println(get(i).toString() + ": " + (cumulativeWeights.get(i) - previousWeight) + ": " +
                distribution.get(get(i).toString()));
            previousWeight = cumulativeWeights.get(i);
        }
    }
}
