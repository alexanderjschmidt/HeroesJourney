package heroes.journey.initializers.base.actions;

import static heroes.journey.registries.Registries.BuffManager;

import java.util.List;
import java.util.UUID;

import heroes.journey.Application;
import heroes.journey.components.BuffsComponent;
import heroes.journey.components.QuestsComponent;
import heroes.journey.components.character.ActionComponent;
import heroes.journey.entities.Quest;
import heroes.journey.entities.actions.Action;
import heroes.journey.entities.actions.CooldownAction;
import heroes.journey.entities.actions.Cost;
import heroes.journey.entities.actions.ShowAction;
import heroes.journey.entities.actions.TargetAction;
import heroes.journey.entities.actions.TeamActions;
import heroes.journey.entities.actions.inputs.ActionInput;
import heroes.journey.entities.actions.inputs.TargetInput;
import heroes.journey.entities.actions.results.ActionListResult;
import heroes.journey.entities.actions.results.ActionResult;
import heroes.journey.entities.actions.results.EndTurnResult;
import heroes.journey.entities.actions.results.StringResult;
import heroes.journey.initializers.InitializerInterface;
import heroes.journey.initializers.utils.StatsUtils;
import heroes.journey.initializers.utils.Utils;
import heroes.journey.ui.HUD;
import heroes.journey.ui.screens.MainMenuScreen;
import heroes.journey.ui.windows.ActionMenu;

public class BaseActions implements InitializerInterface {

    //TODO this is probably bad
    public static String popupMessage = "";
    public static Action openActionMenu, end_turn, exit_game, popup, save;
    public static CooldownAction workout, study;
    public static Action rest, questBoard;

    @Override
    public void init() {
        openActionMenu = new Action("open_action_menu", "THIS SHOULD NEVER BE DISPLAYED", "", true, null) {
            @Override
            public ShowAction internalRequirementsMet(ActionInput input) {
                return ShowAction.NO;
            }

            @Override
            public ActionResult internalOnSelect(ActionInput input) {
                return new ActionListResult(
                    ActionMenu.getActionsFor(input.getGameState(), input.getEntityId()));
            }
        }.register();

        exit_game = new Action("exit_game", "Exit Game", "Return to main menu", false, null) {
            @Override
            public ActionResult internalOnSelect(ActionInput input) {
                Application.get().setScreen(new MainMenuScreen(Application.get()));
                return null;
            }
        }.register();
        TeamActions.addTeamAction(exit_game);

        end_turn = new Action("end_turn", "End Turn", "End your turn", false, null) {
            @Override
            public ActionResult internalOnSelect(ActionInput input) {
                UUID entityId = input.getGameState().getCurrentEntity();
                input.getGameState()
                    .getWorld()
                    .edit(entityId)
                    .create(ActionComponent.class)
                    .action(BaseActions.rest);
                HUD.get().revertToInitialState();
                return null;
            }
        }.register();
        TeamActions.addTeamAction(end_turn);

        save = new Action("save", "Save", "Save Game", false, null) {
            @Override
            public ActionResult internalOnSelect(ActionInput input) {
                input.getGameState().save("save", true);
                return new StringResult("Your Game has been Saved!");
            }
        }.register();
        TeamActions.addTeamAction(save);

        popup = new Action("popup", "Popup", "", false, null) {
            @Override
            public ActionResult internalOnSelect(ActionInput input) {
                return new StringResult(popupMessage);
            }
        }.register();

        // Used in End Turn
        rest = new Action("rest", "Rest", "Do nothing, resting your body and mind.", false, null) {
            @Override
            public ActionResult internalOnSelect(ActionInput input) {
                BuffsComponent buffsComponent = BuffsComponent.get(input.getGameState().getWorld(),
                    input.getEntityId());
                buffsComponent.add(BuffManager.get("rested"));
                return new EndTurnResult();
            }
        }.register();

        workout = new CooldownAction("workout", "Work out", "Lift weights, gain body", false,
            new Cost(0, 0, 0, 0), 1, false) {
            @Override
            public ActionResult internalOnSelect(ActionInput input) {
                return StatsUtils.adjustBody(input.getGameState(), input.getEntityId(), 1);
            }
        }.register();

        study = new CooldownAction("study", "Study", "Expand your mind, increasing your potential", false,
            new Cost(0, 0, 0, 0), 2, false) {
            @Override
            public ActionResult internalOnSelect(ActionInput input) {
                return StatsUtils.adjustMind(input.getGameState(), input.getEntityId(), 1);
            }
        }.register();

        questBoard = new TargetAction<Quest>("quest_board", "Quest Board",
            "See what the people need help with", new Cost(0, 0, 0, 0)) {
            @Override
            public List<Quest> getTargets(ActionInput input) {
                UUID town = Utils.getLocation(input);
                return QuestsComponent.get(input.getGameState().getWorld(), town).getQuests();
            }

            @Override
            public String getTargetDisplayName(TargetInput<Quest> input) {
                return input.getInput().getName();
            }

            @Override
            protected ActionResult onSelectTarget(TargetInput<Quest> input) {
                UUID town = Utils.getLocation(input);
                QuestsComponent factionsQuestsComponent = QuestsComponent.get(input.getGameState().getWorld(),
                    town);

                if (factionsQuestsComponent != null) {
                    factionsQuestsComponent.remove(input.getInput());
                    QuestsComponent.get(input.getGameState().getWorld(), input.getEntityId())
                        .addQuest(input.getInput());
                }
                return new EndTurnResult();
            }
        }.register();
    }

}
