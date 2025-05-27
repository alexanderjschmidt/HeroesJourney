package heroes.journey.components;

import static heroes.journey.registries.Registries.QuestManager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import heroes.journey.components.utils.PooledClonableComponent;
import heroes.journey.entities.quests.Quest;
import heroes.journey.systems.GameWorld;

public class QuestsComponent extends PooledClonableComponent<QuestsComponent> {

    private final List<String> quests;

    public QuestsComponent() {
        this.quests = new ArrayList<>();
    }

    public List<Quest> getQuests() {
        return QuestManager.get(quests);
    }

    public boolean remove(Quest quest) {
        return quests.remove(quest.toString());
    }

    public QuestsComponent addQuest(Quest quest) {
        quests.add(quest.toString());
        return this;
    }

    public static QuestsComponent get(GameWorld world, UUID entityId) {
        return world.getEntity(QuestsComponent.class, entityId);
    }

    @Override
    protected void reset() {
        quests.clear();
    }

    @Override
    public void copy(QuestsComponent from) {
        quests.addAll(from.quests);
    }
}
