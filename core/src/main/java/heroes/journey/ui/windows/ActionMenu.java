package heroes.journey.ui.windows;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import heroes.journey.GameState;
import heroes.journey.components.PositionComponent;
import heroes.journey.components.PossibleActionsComponent;
import heroes.journey.components.utils.Utils;
import heroes.journey.entities.actions.Action;
import heroes.journey.entities.actions.ShowAction;
import heroes.journey.tilemap.wavefunction.ActionTerrain;
import heroes.journey.ui.*;
import heroes.journey.ui.hudstates.ActionSelectState;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        Entity selectedEntity = GameState.global().getCurrentEntity();
        PossibleActionsComponent selectedActions = PossibleActionsComponent.get(selectedEntity);
        PositionComponent selectedPosition = PositionComponent.get(selectedEntity);
        // Get selected Entities Actions
        List<ScrollPaneEntry<Action>> requirementsMetOptions = filter(selectedActions, selectedEntity);
        if (selectedPosition != null) {
            // Get Tiles Factions Actions
            requirementsMetOptions = Stream.concat(requirementsMetOptions.stream(),
                getFactionActions(selectedEntity).stream()).collect(Collectors.toList());
            // Get Tiles Environment Actions
            requirementsMetOptions = Stream.concat(requirementsMetOptions.stream(),
                    getTileActions(selectedEntity, selectedPosition).stream())
                .collect(Collectors.toList());
        }
        System.out.println("OPEN");
        HUD.get().setState(new ActionSelectState(requirementsMetOptions.stream().distinct().toList()));
    }

    private List<ScrollPaneEntry<Action>> getFactionActions(Entity selectedEntity) {
        Entity faction = Utils.getLocationsFaction(GameState.global(), selectedEntity);
        if (faction != null) {
            PossibleActionsComponent factionActions = PossibleActionsComponent.get(faction);
            if (factionActions != null) {
                return filter(factionActions, selectedEntity);
            }
        }
        return new ArrayList<>();
    }

    private List<ScrollPaneEntry<Action>> getTileActions(Entity selectedEntity, PositionComponent selectedPosition) {
        ActionTerrain environment = GameState.global()
            .getMap()
            .getEnvironment(selectedPosition.getX(), selectedPosition.getY());
        if (environment != null) {
            return filter(environment.getActions(), selectedEntity);
        }
        return new ArrayList<>();
    }

    private List<ScrollPaneEntry<Action>> filter(List<Action> input, Entity entity) {
        return input.stream()
            .map(action -> {
                ShowAction result = action.requirementsMet(GameState.global(), entity);
                return new AbstractMap.SimpleEntry<>(action, result);
            })
            .filter(entry -> entry.getValue() != ShowAction.NO)
            .map(entry -> new ScrollPaneEntry<>(
                entry.getKey(),
                entry.getValue() != ShowAction.GRAYED
            ))
            .toList();
    }

    public void open(List<ScrollPaneEntry<Action>> options) {
        actions.open(options);
    }

    public Action getSelected() {
        return actions.getSelected();
    }

    public void select() {
        actions.selectWrapper();
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
            Action selectedAction = getSelected();
            System.out.println("Selected " + selectedAction + " " + selectedAction.isTerminal());
            String result = selectedAction.onSelect(GameState.global(), HUD.get().getCursor().getSelected());
            if (result != null) {

            }
        }

        @Override
        public void onHover() {
            getSelected().onHover(GameState.global(), HUD.get().getCursor().getSelected());
            actionDetailUI.setAction(getSelected());
        }
    }

}
