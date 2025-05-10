package heroes.journey.components.character;

import heroes.journey.components.utils.KnownEntity;
import heroes.journey.components.utils.PooledClonableComponent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class KnowledgeComponent extends PooledClonableComponent<KnowledgeComponent> {

    public Map<UUID, KnownEntity> knownEntities = new HashMap<>();

    @Override
    public void copy(KnowledgeComponent from) {

    }

    @Override
    protected void reset() {
        knownEntities.clear();
    }
}
