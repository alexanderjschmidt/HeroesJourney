package heroes.journey.ui.windows;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.artemis.World;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;

import heroes.journey.GameState;
import heroes.journey.components.overworld.character.ActionComponent;
import heroes.journey.components.overworld.character.PositionComponent;
import heroes.journey.components.overworld.character.PossibleActionsComponent;
import heroes.journey.components.utils.Utils;
import heroes.journey.entities.actions.Action;
import heroes.journey.entities.actions.ShowAction;
import heroes.journey.tilemap.wavefunction.ActionTerrain;
import heroes.journey.ui.BasicBackground;
import heroes.journey.ui.HUD;
import heroes.journey.ui.ScrollPane;
import heroes.journey.ui.ScrollPaneEntry;
import heroes.journey.ui.UI;
import heroes.journey.ui.hudstates.ActionSelectState;

public class ActionMenu extends Stack {

    private final ScrollPane<Action> actions;
    private final ActionDetailUI actionDetailUI;

    public ActionMenu(ActionDetailUI actionDetailUI) {
        super();
        this.setVisible(false);
        actions = new ActionScrollPane();
        UI background = new BasicBackground();
        this.add(background);
        this.add(actions);
        this.actionDetailUI = actionDetailUI;
    }

    public void open() {
        Integer selectedEntity = GameState.global().getCurrentEntity();
        World world = GameState.global().getWorld();
        PossibleActionsComponent selectedActions = PossibleActionsComponent.get(world, selectedEntity);
        PositionComponent selectedPosition = PositionComponent.get(world, selectedEntity);
        // Get selected Entities Actions
        List<ScrollPaneEntry<Action>> requirementsMetOptions = filter(selectedActions.getPossibleActions(),
            selectedEntity);
        if (selectedPosition != null) {
            // Get Tiles Factions Actions
            requirementsMetOptions = Stream.concat(requirementsMetOptions.stream(),
                getFactionActions(selectedEntity).stream()).collect(Collectors.toList());
            // Get Tiles Environment Actions
            requirementsMetOptions = Stream.concat(requirementsMetOptions.stream(),
                getTileActions(selectedEntity, selectedPosition).stream()).collect(Collectors.toList());
        }
        System.out.println("OPEN");
        HUD.get().setState(new ActionSelectState(requirementsMetOptions.stream().distinct().toList()));
    }

    private List<ScrollPaneEntry<Action>> getFactionActions(Integer selectedEntity) {
        Integer faction = Utils.getLocationsFaction(GameState.global(), selectedEntity);
        if (faction != null) {
            PossibleActionsComponent factionActions = PossibleActionsComponent.get(
                GameState.global().getWorld(), faction);
            if (factionActions != null) {
                return filter(factionActions.getPossibleActions(), selectedEntity);
            }
        }
        return new ArrayList<>();
    }

    private List<ScrollPaneEntry<Action>> getTileActions(
        Integer selectedEntity,
        PositionComponent selectedPosition) {
        ActionTerrain environment = GameState.global()
            .getMap()
            .getEnvironment(selectedPosition.getX(), selectedPosition.getY());
        if (environment != null) {
            return filter(environment.getActions(), selectedEntity);
        }
        return new ArrayList<>();
    }

    private List<ScrollPaneEntry<Action>> filter(List<Action> input, Integer entityId) {
        return input.stream()
            .map(action -> {
                ShowAction result = action.requirementsMet(GameState.global(), entityId);
                return new AbstractMap.SimpleEntry<>(action, result);
            })
            .filter(entry -> entry.getValue() != ShowAction.NO)
            .map(entry -> new ScrollPaneEntry<>(entry.getKey(), entry.getValue() != ShowAction.GRAYED))
            .toList();
    }

    public void open(List<ScrollPaneEntry<Action>> options) {
        actions.open(options);
    }

    public void select() {
        actions.select();
    }

    public void handleInputs() {
        actions.handleInput();
    }

    private class ActionScrollPane extends ScrollPane<Action> {

        @Override
        public void open(List<ScrollPaneEntry<Action>> options) {
            GameState.global().getRangeManager().clearRange();
            if (options.isEmpty()) {
                System.out.println("Options Empty");
                HUD.get().getCursor().clearSelected();
                HUD.get().revertToPreviousState();
                return;
            }
            super.open(options);
        }

        @Override
        public void select() {
            ScrollPaneEntry<Action> selectedAction = actions.getSelected();
            if (selectedAction.isSelectable()) {
                // TODO add back TargetAction logic
                Action action = selectedAction.entry();
                System.out.println("Selected " + action + " " + action.isTerminal());
                if (action.isTerminal()) {
                    Integer selectedEntity = HUD.get().getCursor().getSelected();
                    GameState.global()
                        .getWorld()
                        .edit(selectedEntity)
                        .create(ActionComponent.class)
                        .action(action);
                    HUD.get().revertToInitialState();
                } else {
                    action.onSelect(GameState.global(), HUD.get().getCursor().getSelected());
                }
            }
        }

        @Override
        public void onHover() {
            actions.getSelected().entry().onHover(GameState.global(), HUD.get().getCursor().getSelected());
            actionDetailUI.setAction(actions.getSelected().entry());
        }
    }

}
