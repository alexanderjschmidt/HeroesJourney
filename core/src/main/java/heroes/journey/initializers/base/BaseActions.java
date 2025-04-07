package heroes.journey.initializers.base;

import java.util.List;

import com.badlogic.ashley.core.Entity;

import heroes.journey.Application;
import heroes.journey.GameState;
import heroes.journey.components.ActionComponent;
import heroes.journey.components.quests.QuestsComponent;
import heroes.journey.components.utils.Utils;
import heroes.journey.entities.actions.Action;
import heroes.journey.entities.actions.CooldownAction;
import heroes.journey.entities.actions.ShowAction;
import heroes.journey.initializers.InitializerInterface;
import heroes.journey.screens.MainMenuScreen;
import heroes.journey.ui.HUD;
import heroes.journey.ui.ScrollPaneEntry;
import heroes.journey.ui.hudstates.ActionSelectState;

public class BaseActions implements InitializerInterface {

    public static Action openActionMenu, wait, end_turn, exit_game, attack;
    public static CooldownAction workout, study;
    public static CooldownAction delve;
    public static Action chopTrees;
    public static Action questBoard;

    static {
        openActionMenu = new Action.Builder().name("THIS SHOULD NEVER BE DISPLAYED")
            .terminalAction(false)
            .requirementsMet((gs, e) -> ShowAction.NO)
            .onSelect((gs, e) -> {
                HUD.get().getActionMenu().open();
                return null;
            })
            .build();
        exit_game = new Action.Builder().name("Exit Game").teamAction(true).onSelect((gs, e) -> {
            Application.get().setScreen(new MainMenuScreen(Application.get()));
            GameState.global().getEngine().removeAllEntities();
            return null;
        }).build();
        end_turn = new Action.Builder().name("End Turn").teamAction(true).onSelect((gs, e) -> {
            Entity entity = gs.getCurrentEntity();
            entity.add(new ActionComponent(wait));
            HUD.get().revertToInitialState();
            return null;
        }).build();
        wait = new Action.Builder().name("Wait").build();
        workout = new CooldownAction.Builder().name("Work out")
            .turnCooldown(1)
            .onSelect((gs, e) -> Utils.adjustBody(e, 1))
            .build();
        study = new CooldownAction.Builder().name("Study")
            .turnCooldown(2)
            .onSelect(((gs, e) -> Utils.adjustMind(e, 1)))
            .build();
        delve = new CooldownAction.Builder().name("Delve")
            .turnCooldown(5)
            .factionCooldown(true)
            .onSelect((gs, e) -> {
                Utils.addItem(e, Items.ironOre, 5);
                return "Successful Delve! You found 5 iron ore";
            })
            .build();
        chopTrees = new Action.Builder().name("Chop Trees")
            .onSelect((gs, e) -> Utils.addItem(e, Items.wood, 1))
            .build();
        questBoard = new Action.Builder().name("Quest Board").terminalAction(false).onSelect((gs, e) -> {
            Entity town = Utils.getLocationsFaction(gs, e);
            List<Action> questActions = Utils.getQuestClaimActions(town);
            List<ScrollPaneEntry<Action>> options = questActions.stream()
                .map(key -> new ScrollPaneEntry<>(key, true))
                .toList();
            HUD.get().setState(new ActionSelectState(options));
            return null;
        }).requirementsMet((gs, e) -> {
            Entity town = Utils.getLocationsFaction(gs, e);
            QuestsComponent questsComponent = QuestsComponent.get(town);
            return questsComponent.isEmpty() ? ShowAction.GRAYED : ShowAction.YES;
        }).build();
    }

}
