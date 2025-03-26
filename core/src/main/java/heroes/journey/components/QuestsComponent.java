package heroes.journey.components;

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
}
