package heroes.journey.initializers.base;

import java.util.List;

import com.badlogic.ashley.core.Entity;

import heroes.journey.Application;
import heroes.journey.GameState;
import heroes.journey.components.ActionComponent;
import heroes.journey.components.quests.QuestsComponent;
import heroes.journey.components.utils.Utils;
import heroes.journey.entities.actions.Action;
import heroes.journey.entities.actions.ActionManager;
import heroes.journey.entities.actions.CooldownAction;
import heroes.journey.entities.actions.ShowAction;
import heroes.journey.initializers.InitializerInterface;
import heroes.journey.screens.MainMenuScreen;
import heroes.journey.ui.HUD;
import heroes.journey.ui.ScrollPaneEntry;
import heroes.journey.ui.hudstates.ActionSelectState;
import heroes.journey.ui.hudstates.States;

public class BaseActions implements InitializerInterface {

    public static Action openActionMenu, wait, end_turn, exit_game, attack;
    public static CooldownAction workout, study;
    public static CooldownAction delve;
    public static Action chopTrees;
    public static Action questBoard;
    public static Action carriage;

    static {
        openActionMenu = Action.builder()
            .name("THIS SHOULD NEVER BE DISPLAYED")
            .terminal(false)
            .requirementsMet((gs, e) -> ShowAction.NO)
            .onSelect((gs, e) -> {
                HUD.get().getActionMenu().open();
                return null;
            })
            .build();
        exit_game = Action.builder().name("Exit Game").terminal(false).onSelect((gs, e) -> {
            Application.get().setScreen(new MainMenuScreen(Application.get()));
            GameState.global().getEngine().removeAllEntities();
            return null;
        }).build();
        ActionManager.addTeamAction(exit_game);
        end_turn = Action.builder().name("End Turn").terminal(false).onSelect((gs, e) -> {
            Entity entity = gs.getCurrentEntity();
            entity.add(new ActionComponent(wait));
            HUD.get().revertToInitialState();
            return null;
        }).build();
        ActionManager.addTeamAction(end_turn);
        wait = Action.builder().name("Wait").build();
        workout = CooldownAction.builder()
            .name("Work out")
            .turnCooldown(1)
            .onSelect((gs, e) -> Utils.adjustBody(e, 1))
            .build();
        study = CooldownAction.builder()
            .name("Study")
            .turnCooldown(2)
            .onSelect(((gs, e) -> Utils.adjustMind(e, 1)))
            .build();
        delve = CooldownAction.builder()
            .name("Delve")
            .turnCooldown(5)
            .factionCooldown(true)
            .onSelect((gs, e) -> {
                Utils.addItem(e, Items.ironOre, 5);
                HUD.get().setState(States.DELVE);
                return null;
            })
            .build();
        chopTrees = Action.builder()
            .name("Chop Trees")
            .onSelect((gs, e) -> Utils.addItem(e, Items.wood, 1))
            .build();
        questBoard = Action.builder().name("Quest Board").terminal(false).onSelect((gs, e) -> {
            Entity town = Utils.getLocationsFaction(gs, e);
            List<Action> questActions = Utils.getQuestClaimActions(town);
            List<ScrollPaneEntry<Action>> options = Utils.convertToScrollEntries(questActions);
            HUD.get().setState(new ActionSelectState(options));
            return null;
        }).requirementsMet((gs, e) -> {
            Entity town = Utils.getLocationsFaction(gs, e);
            QuestsComponent questsComponent = QuestsComponent.get(town);
            return questsComponent.isEmpty() ? ShowAction.GRAYED : ShowAction.YES;
        }).build();
        carriage = Action.builder().name("Carriage").terminal(false).onSelect((gs, e) -> {
            Entity town = Utils.getLocationsFaction(gs, e);
            List<Action> carriageActions = Utils.getCarriageActions(gs, town, e);
            List<ScrollPaneEntry<Action>> options = Utils.convertToScrollEntries(carriageActions);
            HUD.get().setState(new ActionSelectState(options));
            return null;
        }).build();
    }

}
