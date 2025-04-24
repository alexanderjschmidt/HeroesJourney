package heroes.journey.components.utils;

import com.artemis.PooledComponent;

public abstract class PooledClonableComponent<T extends PooledClonableComponent<T>> extends PooledComponent {
    @Override
    protected void reset() {

    }

    public abstract void copy(T from);

}
