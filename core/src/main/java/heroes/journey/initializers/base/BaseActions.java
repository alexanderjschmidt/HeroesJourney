package heroes.journey.initializers.base;

import com.badlogic.ashley.core.Entity;
import heroes.journey.Application;
import heroes.journey.GameState;
import heroes.journey.components.ActionComponent;
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

import java.util.ArrayList;
import java.util.List;

public class BaseActions implements InitializerInterface {

    public static Action openActionMenu, wait, end_turn, exit_game, attack;
    public static CooldownAction workout, study;
    public static CooldownAction delve;
    public static Action chopTrees;
    public static Action inn;

    static {
        openActionMenu = new Action.Builder().name("THIS SHOULD NEVER BE DISPLAYED")
            .terminalAction(false)
            .requirementsMet()
            .add((gs, e) -> false)
            .owner()
            .onSelect()
            .add((gs, e) -> {
                HUD.get().getActionMenu().open();
                return null;
            })
            .owner()
            .build();
        exit_game = new Action.Builder().name("Exit Game").teamAction(true).onSelect().add((gs, e) -> {
            Application.get().setScreen(new MainMenuScreen(Application.get()));
            GameState.global().getEngine().removeAllEntities();
            return null;
        }).owner().build();
        end_turn = new Action.Builder().name("End Turn").teamAction(true)
            // This SEEMs contradictory, but its because this is a teamSkill and doesnt have a selected entity to apply to
            // that will get handled by the assigned wait action
            .terminalAction(false).onSelect().add((gs, e) -> {
                Entity entity = gs.getCurrentEntity();
                entity.add(new ActionComponent(wait));
                return null;
            }).owner().build();
        wait = new Action.Builder().name("Wait").build();
        workout = new CooldownAction.Builder().name("Work out")
            .turnCooldown(1)
            .onSelect()
            .add((gs, e) -> Utils.adjustBody(e, 1))
            .owner()
            .build();
        study = new CooldownAction.Builder().name("Study")
            .turnCooldown(2)
            .onSelect()
            .add((gs, e) -> Utils.adjustMind(e, 1))
            .owner()
            .build();
        delve = new CooldownAction.Builder().name("Delve")
            .turnCooldown(5)
            .factionCooldown(true)
            .onSelect()
            .add((gs, e) -> {
                Utils.addItem(e, Items.ironOre, 5);
                return "Successful Delve! You found 5 iron ore";
            })
            .owner()
            .build();
        chopTrees = new Action.Builder().name("Chop Trees").onSelect().add((gs, e) -> {
            Utils.addItem(e, Items.wood, 1);
            return "Gained 1 wood";
        }).owner().build();
        inn = new Action.Builder().name("Inn").terminalAction(false).onSelect().add((gs, e) -> {
            Entity town = Utils.getLocationsFaction(gs, e);
            QuestsComponent questsComponent = QuestsComponent.get(town);
            List<Action> questActions = new ArrayList<>();
            for (Quest quest : questsComponent) {
                questActions.add(new ClaimQuestAction.Builder().name(quest.toString()).quest(quest).build());
            }
            HUD.get().setState(new ActionSelectState(questActions));
            return null;
        }).owner().requirementsMet().add((gs, e) -> {
            Entity town = Utils.getLocationsFaction(gs, e);
            QuestsComponent questsComponent = QuestsComponent.get(town);
            return !questsComponent.isEmpty();
        }).owner().build();
    }

}
