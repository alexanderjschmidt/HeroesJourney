package heroes.journey.components;

import java.util.ArrayList;
import java.util.List;

import com.artemis.World;

import heroes.journey.components.utils.PooledClonableComponent;
import heroes.journey.entities.quests.Quest;
import heroes.journey.entities.quests.QuestManager;

public class QuestsComponent extends PooledClonableComponent<QuestsComponent> {

    private final List<String> quests;

    public QuestsComponent() {
        this.quests = new ArrayList<>();
    }

    public List<Quest> getQuests() {
        return QuestManager.get(quests);
    }

    public QuestsComponent addQuest(Quest quest) {
        quests.add(quest.toString());
        return this;
    }

    public static QuestsComponent get(World world, int entityId) {
        return world.getMapper(QuestsComponent.class).get(entityId);
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
