package heroes.journey.initializers.base;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.ashley.core.Entity;

import heroes.journey.Application;
import heroes.journey.GameState;
import heroes.journey.components.ActionComponent;
import heroes.journey.components.CooldownComponent;
import heroes.journey.components.StatsComponent;
import heroes.journey.components.quests.QuestsComponent;
import heroes.journey.components.utils.Utils;
import heroes.journey.entities.actions.Action;
import heroes.journey.entities.actions.ClaimQuestAction;
import heroes.journey.entities.actions.CooldownAction;
import heroes.journey.entities.quests.Quest;
import heroes.journey.initializers.InitializerInterface;
import heroes.journey.screens.MainMenuScreen;
import heroes.journey.ui.HUD;
import heroes.journey.ui.hudstates.ActionSelectState;

public class BaseActions implements InitializerInterface {

    public static Action openActionMenu, wait, end_turn, exit_game, attack;
    public static CooldownAction workout, study;
    public static CooldownAction delve;
    public static Action chopTrees;
    public static Action inn;

    static {
        openActionMenu = new Action("THIS SHOULD NEVER BE DISPLAYED") {

            @Override
            public boolean isTerminal() {
                return false;
            }

            @Override
            public String onSelect(GameState gameState, Entity selected) {
                HUD.get().getActionMenu().open();
                return null;
            }

            @Override
            public boolean requirementsMet(GameState gameState, Entity selected) {
                return false;
            }
        };
        exit_game = new Action("Exit Game", true) {

            @Override
            public String onSelect(GameState gameState, Entity selected) {
                GameState.global().getEngine().removeAllEntities();
                Application.get().setScreen(new MainMenuScreen(Application.get()));
                return null;
            }

            @Override
            public boolean requirementsMet(GameState gameState, Entity selected) {
                return true;
            }
        };
        end_turn = new Action("End Turn", true) {

            @Override
            public boolean isTerminal() {
                // This SEEMs contradictory, but its because this is a teamSkill and doesnt have a selected entity to apply to
                // that will get handled by the assigned wait action
                return false;
            }

            @Override
            public String onSelect(GameState gameState, Entity selected) {
                Entity entity = gameState.getCurrentEntity();
                entity.add(new ActionComponent(wait));
                return null;
            }

            @Override
            public boolean requirementsMet(GameState gameState, Entity selected) {
                return true;
            }
        };
        wait = new Action("Wait") {
            @Override
            public String onSelect(GameState gameState, Entity selected) {
                return null;
            }

            @Override
            public boolean requirementsMet(GameState gameState, Entity selected) {
                return true;
            }
        };
        workout = new CooldownAction("Work out", 2) {
            @Override
            public String onSelectHelper(GameState gameState, Entity selected) {
                StatsComponent statsComponent = StatsComponent.get(selected);
                if (statsComponent == null)
                    return null;
                statsComponent.setBody(statsComponent.getBody() + 1);
                return "Successful Workout! Gain 1 Body";
            }

            @Override
            public boolean requirementsMetHelper(GameState gameState, Entity selected) {
                return true;
            }
        };
        study = new CooldownAction("Study", 2) {
            @Override
            public String onSelectHelper(GameState gameState, Entity selected) {
                StatsComponent statsComponent = StatsComponent.get(selected);
                if (statsComponent == null)
                    return null;
                statsComponent.setMind(statsComponent.getMind() + 1);
                return "Successful Study Session! Gain 1 Mind";
            }

            @Override
            public boolean requirementsMetHelper(GameState gameState, Entity selected) {
                return true;
            }
        };
        delve = new CooldownAction("Delve", 5) {

            @Override
            public String onSelect(GameState gameState, Entity entity) {
                Entity dungeon = Utils.getLocationsFaction(gameState, entity);
                CooldownComponent.get(dungeon).put(this, getTurnCooldown());
                return onSelectHelper(gameState, entity);
            }

            @Override
            public String onSelectHelper(GameState gameState, Entity selected) {
                Utils.addItem(selected, Items.ironOre, 5);
                return "Successful Delve! You found 5 iron ore";
            }

            @Override
            public boolean requirementsMet(GameState gameState, Entity entity) {
                Entity dungeon = Utils.getLocationsFaction(gameState, entity);
                return requirementsMetHelper(gameState, dungeon);
            }

            @Override
            public boolean requirementsMetHelper(GameState gameState, Entity dungeon) {
                return !CooldownComponent.get(dungeon).containsKey(this);
            }
        };
        chopTrees = new Action("Chop Trees") {
            @Override
            public boolean requirementsMet(GameState gameState, Entity selected) {
                return true;
            }

            @Override
            public String onSelect(GameState gameState, Entity selected) {
                Utils.addItem(selected, Items.wood, 1);
                return "Gained 1 wood";
            }
        };
        inn = new Action("Inn") {

            @Override
            public boolean isTerminal() {
                return false;
            }

            @Override
            public String onSelect(GameState gameState, Entity selected) {
                Entity town = Utils.getLocationsFaction(gameState, selected);
                QuestsComponent questsComponent = QuestsComponent.get(town);
                List<Action> questActions = new ArrayList<>();
                for (Quest quest : questsComponent) {
                    questActions.add(new ClaimQuestAction(quest.toString(), quest));
                }
                HUD.get().setState(new ActionSelectState(questActions));
                return null;
            }

            @Override
            public boolean requirementsMet(GameState gameState, Entity selected) {
                Entity town = Utils.getLocationsFaction(gameState, selected);
                QuestsComponent questsComponent = QuestsComponent.get(town);
                return !questsComponent.isEmpty();
            }
        };
    }

}
