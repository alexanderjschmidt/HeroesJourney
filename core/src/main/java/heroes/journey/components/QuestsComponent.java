package heroes.journey.components;

import java.util.ArrayList;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;

import heroes.journey.components.interfaces.ClonableComponent;
import heroes.journey.entities.quests.Quest;

public class QuestsComponent extends ArrayList<Quest> implements ClonableComponent<QuestsComponent> {

    public QuestsComponent() {
        super();
    }

    @Override
    public QuestsComponent clone() {
        QuestsComponent clone = (QuestsComponent)super.clone();
        return clone;
    }

    public QuestsComponent addQuest(Quest quest) {
        this.add(quest);
        return this;
    }

    private static final ComponentMapper<QuestsComponent> mapper = ComponentMapper.getFor(
        QuestsComponent.class);

    public static QuestsComponent get(Entity entity) {
        return mapper.get(entity);
    }
}
