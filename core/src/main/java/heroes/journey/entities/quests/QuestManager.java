package heroes.journey.entities.quests;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class QuestManager extends HashMap<String,Quest> {

    private static QuestManager questManager;

    private QuestManager() {
    }

    public static QuestManager get() {
        if (questManager == null)
            questManager = new QuestManager();
        return questManager;
    }

    public static List<Quest> get(List<String> questStrings) {
        return questStrings.stream().map(questManager::get) // get the Action for each string
            .filter(Objects::nonNull) // optionally skip nulls
            .collect(Collectors.toList());
    }

    public static Quest register(Quest quest) {
        get().put(quest.toString(), quest);
        return quest;
    }

}
