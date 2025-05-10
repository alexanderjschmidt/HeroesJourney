package heroes.journey.initializers.base.actions;

import heroes.journey.Application;
import heroes.journey.components.BuffsComponent;
import heroes.journey.components.QuestsComponent;
import heroes.journey.components.character.ActionComponent;
import heroes.journey.components.utils.Utils;
import heroes.journey.entities.actions.Action;
import heroes.journey.entities.actions.CooldownAction;
import heroes.journey.entities.actions.ShowAction;
import heroes.journey.entities.actions.TeamActions;
import heroes.journey.entities.actions.results.ActionListResult;
import heroes.journey.entities.actions.results.EndTurnResult;
import heroes.journey.entities.actions.results.StringResult;
import heroes.journey.initializers.InitializerInterface;
import heroes.journey.initializers.base.Buffs;
import heroes.journey.ui.HUD;
import heroes.journey.ui.screens.MainMenuScreen;
import heroes.journey.ui.windows.ActionMenu;

import java.util.List;
import java.util.UUID;

public class BaseActions implements InitializerInterface {

    public static String popupMessage = "";
    public static Action openActionMenu, rest, end_turn, exit_game, popup, save;
    public static CooldownAction workout, study;
    public static Action questBoard;

    static {
        openActionMenu = Action.builder()
            .name("THIS SHOULD NEVER BE DISPLAYED")
            .returnsActionList(true)
            .requirementsMet((gs, e) -> ShowAction.NO)
            .onSelect((gs, e) -> new ActionListResult(ActionMenu.getActionsFor(gs, e)))
            .build()
            .register();
        exit_game = Action.builder().name("Exit Game").onSelect((gs, e) -> {
            Application.get().setScreen(new MainMenuScreen(Application.get()));
            return null;
        }).build().register();
        TeamActions.addTeamAction(exit_game);
        end_turn = Action.builder().name("End Turn").onSelect((gs, e) -> {
            UUID entityId = gs.getCurrentEntity();
            gs.getWorld().edit(entityId).create(ActionComponent.class).action(BaseActions.rest);
            HUD.get().revertToInitialState();
            return null;
        }).build().register();
        TeamActions.addTeamAction(end_turn);
        save = Action.builder()
            .name("Save")
            .description("Save Game")
            .onSelect((gs, e) -> {
                gs.save("save", true);
                return new StringResult("Your Game has been Saved!");
            }).build().register();
        TeamActions.addTeamAction(save);
        popup = Action.builder()
            .name("Popup")
            .onSelect((gs, e) -> new StringResult(popupMessage))
            .build().register();
        rest = Action.builder()
            .name("Rest")
            .description("Do nothing, resting your body and mind.")
            .onSelect((gs, e) -> {
                BuffsComponent buffsComponent = BuffsComponent.get(gs.getWorld(), e);
                buffsComponent.add(Buffs.rested);
                return new EndTurnResult();
            })
            .build()
            .register();

        workout = CooldownAction.builder()
            .name("Work out")
            .description("Lift weights, gain body")
            .turnCooldown(1)
            .onSelect((gs, e) -> Utils.adjustBody(gs, e, 1))
            .build()
            .register();
        study = CooldownAction.builder()
            .name("Study")
            .description("Expand your mind, increasing your potential")
            .turnCooldown(2)
            .onSelect(((gs, e) -> Utils.adjustMind(gs, e, 1)))
            .build()
            .register();
        questBoard = Action.builder()
            .name("Quest Board")
            .description("See what the people need help with")
            .returnsActionList(true)
            .onSelect((gs, e) -> {
                UUID town = Utils.getLocation(gs, e);
                List<Action> questActions = Utils.getQuestClaimActions(gs, town);
                return new ActionListResult(questActions);
            })
            .requirementsMet((gs, e) -> {
                UUID town = Utils.getLocation(gs, e);
                QuestsComponent questsComponent = QuestsComponent.get(gs.getWorld(), town);
                return questsComponent.getQuests().isEmpty() ? ShowAction.GRAYED : ShowAction.YES;
            })
            .build()
            .register();
    }

}
