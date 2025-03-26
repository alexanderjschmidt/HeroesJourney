package heroes.journey.components;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import heroes.journey.components.interfaces.ClonableComponent;
import heroes.journey.entities.quests.Quest;

import java.util.ArrayList;
import java.util.List;

public class QuestsComponent implements ClonableComponent<QuestsComponent> {

    private List<Quest> quests;

    public QuestsComponent() {
        this.quests = new ArrayList<>();
    }

    public List<Quest> getQuests() {
        return quests;
    }

    @Override
    public QuestsComponent clone() {
        QuestsComponent questsComponent = new QuestsComponent();
        questsComponent.quests = new ArrayList<>(quests);
        return questsComponent;
    }

    private static final ComponentMapper<QuestsComponent> mapper = ComponentMapper.getFor(
        QuestsComponent.class);

    public static QuestsComponent get(Entity entity) {
        return mapper.get(entity);
    }
}
