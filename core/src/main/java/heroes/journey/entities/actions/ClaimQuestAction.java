package heroes.journey.entities.actions;

import com.badlogic.ashley.core.Entity;

import heroes.journey.GameState;
import heroes.journey.entities.quests.Quest;

public class ClaimQuestAction extends Action {

    private final Quest quest;

    public ClaimQuestAction(String name, int manaCost, boolean teamSkill, Quest quest) {
        super(name, manaCost, teamSkill);
        this.quest = quest;
    }

    public ClaimQuestAction(String name, int manaCost, Quest quest) {
        super(name, manaCost);
        this.quest = quest;
    }

    public ClaimQuestAction(String name, boolean teamSkill, Quest quest) {
        super(name, teamSkill);
        this.quest = quest;
    }

    public ClaimQuestAction(String name, Quest quest) {
        super(name);
        this.quest = quest;
    }

    @Override
    public boolean requirementsMet(GameState gameState, Entity entity) {
        return true;
    }

    @Override
    public void onSelect(GameState gameState, Entity entity) {
    }
}
