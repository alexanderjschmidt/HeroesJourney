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

    //TODO this is probably bad
    public static String popupMessage = "";
    public static Action openActionMenu, rest, end_turn, exit_game, popup, save;
    public static CooldownAction workout, study;
    public static Action questBoard;

    static {
        openActionMenu = Action.builder()
            .name("THIS SHOULD NEVER BE DISPLAYED")
            .returnsActionList(true)
            .requirementsMet((input) -> ShowAction.NO)
            .onSelect((input) -> new ActionListResult(ActionMenu.getActionsFor(input.getGameState(), input.getEntityId())))
            .build()
            .register();
        exit_game = Action.builder().name("Exit Game").onSelect((input) -> {
            Application.get().setScreen(new MainMenuScreen(Application.get()));
            return null;
        }).build().register();
        TeamActions.addTeamAction(exit_game);
        end_turn = Action.builder().name("End Turn").onSelect((input) -> {
            UUID entityId = input.getGameState().getCurrentEntity();
            input.getGameState().getWorld().edit(entityId).create(ActionComponent.class).action(BaseActions.rest);
            HUD.get().revertToInitialState();
            return null;
        }).build().register();
        TeamActions.addTeamAction(end_turn);
        save = Action.builder()
            .name("Save")
            .description("Save Game")
            .onSelect((input) -> {
                input.getGameState().save("save", true);
                return new StringResult("Your Game has been Saved!");
            }).build().register();
        TeamActions.addTeamAction(save);
        popup = Action.builder()
            .name("Popup")
            .onSelect((input) -> new StringResult(popupMessage))
            .build().register();
        rest = Action.builder()
            .name("Rest")
            .description("Do nothing, resting your body and mind.")
            .onSelect((input) -> {
                BuffsComponent buffsComponent = BuffsComponent.get(input.getGameState().getWorld(), input.getEntityId());
                buffsComponent.add(Buffs.rested);
                return new EndTurnResult();
            })
            .build()
            .register();

        workout = CooldownAction.builder()
            .name("Work out")
            .description("Lift weights, gain body")
            .turnCooldown(1)
            .onSelect((input) -> Utils.adjustBody(input.getGameState(), input.getEntityId(), 1))
            .build()
            .register();
        study = CooldownAction.builder()
            .name("Study")
            .description("Expand your mind, increasing your potential")
            .turnCooldown(2)
            .onSelect(((input) -> Utils.adjustMind(input.getGameState(), input.getEntityId(), 1)))
            .build()
            .register();
        questBoard = Action.builder()
            .name("Quest Board")
            .description("See what the people need help with")
            .returnsActionList(true)
            .onSelect((input) -> {
                UUID town = Utils.getLocation(input.getGameState(), input.getEntityId());
                List<Action> questActions = Utils.getQuestClaimActions(input.getGameState(), town);
                return new ActionListResult(questActions);
            })
            .requirementsMet((input) -> {
                UUID town = Utils.getLocation(input.getGameState(), input.getEntityId());
                QuestsComponent questsComponent = QuestsComponent.get(input.getGameState().getWorld(), town);
                return questsComponent.getQuests().isEmpty() ? ShowAction.GRAYED : ShowAction.YES;
            })
            .build()
            .register();
    }

}
