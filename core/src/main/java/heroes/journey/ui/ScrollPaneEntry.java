package heroes.journey.ui;

import java.util.Objects;

public class ScrollPaneEntry<T> {
    private final T entry;
    private final boolean selectable;

    public ScrollPaneEntry(T entry, boolean show) {
        this.entry = entry;
        this.selectable = show;
    }

    public T entry() {
        return entry;
    }

    public boolean isSelectable() {
        return selectable;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ScrollPaneEntry<?> item) {
            return Objects.equals(this.entry, item.entry);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(entry);
    }
}
