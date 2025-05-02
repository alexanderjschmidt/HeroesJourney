package heroes.journey.ui.windows;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.artemis.World;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;

import heroes.journey.GameState;
import heroes.journey.components.PositionComponent;
import heroes.journey.components.PossibleActionsComponent;
import heroes.journey.components.character.ActionComponent;
import heroes.journey.components.utils.Utils;
import heroes.journey.entities.actions.Action;
import heroes.journey.tilemap.wavefunctiontiles.ActionTerrain;
import heroes.journey.ui.BasicBackground;
import heroes.journey.ui.HUD;
import heroes.journey.ui.ScrollPane;
import heroes.journey.ui.ScrollPaneEntry;
import heroes.journey.ui.UI;

public class ActionMenu extends Stack {

    private final ScrollPane<Action> actions;
    private final InfoUI infoUI;

    public ActionMenu(InfoUI infoUI) {
        super();
        this.setVisible(false);
        actions = new ActionScrollPane();
        UI background = new BasicBackground();
        this.add(background);
        this.add(actions);
        this.infoUI = infoUI;
    }

    public List<Action> getActionsFor(Integer selectedEntity) {
        World world = GameState.global().getWorld();
        PossibleActionsComponent selectedActions = PossibleActionsComponent.get(world, selectedEntity);
        PositionComponent selectedPosition = PositionComponent.get(world, selectedEntity);
        // Get selected Entities Actions
        List<Action> requirementsMetOptions = selectedActions.getPossibleActions();
        if (selectedPosition != null) {
            // Get Tiles Locations Actions
            requirementsMetOptions = Stream.concat(requirementsMetOptions.stream(),
                getLocationActions(selectedEntity).stream()).collect(Collectors.toList());
            // Get Tiles Environment Actions
            requirementsMetOptions = Stream.concat(requirementsMetOptions.stream(),
                getTileActions(selectedPosition).stream()).collect(Collectors.toList());
        }
        return requirementsMetOptions.stream().distinct().toList();
    }

    private List<Action> getLocationActions(Integer selectedEntity) {
        Integer faction = Utils.getLocation(GameState.global(), selectedEntity);
        if (faction != null) {
            PossibleActionsComponent factionActions = PossibleActionsComponent.get(
                GameState.global().getWorld(), faction);
            if (factionActions != null) {
                return factionActions.getPossibleActions();
            }
        }
        return new ArrayList<>();
    }

    private List<Action> getTileActions(
        PositionComponent selectedPosition) {
        ActionTerrain environment = GameState.global()
            .getMap()
            .getEnvironment(selectedPosition.getX(), selectedPosition.getY());
        if (environment != null) {
            return environment.getActions();
        }
        return new ArrayList<>();
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
                Integer selectedEntity = HUD.get().getCursor().getSelected();
                System.out.println("Selected " + action + " " + selectedEntity);
                if (selectedEntity != null) {
                    GameState.global()
                        .getWorld()
                        .edit(selectedEntity)
                        .create(ActionComponent.class)
                        .action(action);
                } else {
                    action.onSelect(GameState.global(), null);
                }
            }
        }

        @Override
        public void onHover() {
            actions.getSelected().entry().onHover(GameState.global(), HUD.get().getCursor().getSelected());
            infoUI.showInfo(actions.getSelected().entry());
        }
    }

}
