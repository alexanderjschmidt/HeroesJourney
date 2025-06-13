package heroes.journey.ui.windows;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import heroes.journey.GameState;
import heroes.journey.components.PositionComponent;
import heroes.journey.components.PossibleActionsComponent;
import heroes.journey.components.character.ActionComponent;
import heroes.journey.entities.actions.Action;
import heroes.journey.entities.actions.inputs.ActionInput;
import heroes.journey.initializers.utils.Utils;
import heroes.journey.systems.GameWorld;
import heroes.journey.tilemap.wavefunctiontiles.ActionTerrain;
import heroes.journey.ui.HUD;
import heroes.journey.ui.ScrollPane;
import heroes.journey.ui.ScrollPaneEntry;
import heroes.journey.ui.UI;

public class ActionMenu extends UI {

    private final ScrollPane<Action> actions;
    private final InfoUI infoUI;

    public ActionMenu(InfoUI infoUI) {
        super();
        this.setVisible(false);
        actions = new ActionScrollPane();
        mainTable.add(actions).row();
        mainTable.add().expandY().row();
        this.infoUI = infoUI;

        pack();
    }

    public static List<Action> getActionsFor(GameState gameState, UUID selectedEntity) {
        GameWorld world = gameState.getWorld();
        PossibleActionsComponent selectedActions = PossibleActionsComponent.get(world, selectedEntity);
        PositionComponent selectedPosition = PositionComponent.get(world, selectedEntity);
        // Get selected Entities Actions
        List<Action> requirementsMetOptions = selectedActions.getPossibleActions();
        System.out.println(requirementsMetOptions);
        if (selectedPosition != null) {
            // Get Tiles Locations Actions
            requirementsMetOptions = Stream.concat(requirementsMetOptions.stream(),
                getLocationActions(gameState, selectedEntity).stream()).collect(Collectors.toList());
            // Get Tiles Environment Actions
            requirementsMetOptions = Stream.concat(requirementsMetOptions.stream(),
                getTileActions(gameState, selectedPosition).stream()).collect(Collectors.toList());
        }
        return requirementsMetOptions.stream().distinct().toList();
    }

    private static List<Action> getLocationActions(GameState gameState, UUID selectedEntity) {
        UUID faction = Utils.getLocation(gameState, selectedEntity);
        if (faction != null) {
            PossibleActionsComponent factionActions = PossibleActionsComponent.get(gameState.getWorld(),
                faction);
            if (factionActions != null) {
                return factionActions.getPossibleActions();
            }
        }
        return new ArrayList<>();
    }

    private static List<Action> getTileActions(GameState gameState, PositionComponent selectedPosition) {
        ActionTerrain environment = gameState.getMap()
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
        actions.selectWrapper();
    }

    public void handleInputs() {
        actions.handleInput();
    }

    private class ActionScrollPane extends ScrollPane<Action> {

        @Override
        public void open(List<ScrollPaneEntry<Action>> options) {
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
            // TODO add back TargetAction logic
            Action action = getSelected().entry();
            UUID selectedEntity = HUD.get().getCursor().getSelected();
            System.out.println("Selected " + action + " " + selectedEntity);
            if (selectedEntity != null) {
                GameState.global()
                    .getWorld()
                    .edit(selectedEntity)
                    .create(ActionComponent.class)
                    .action(action);
            } else {
                action.onSelect(new ActionInput(GameState.global(), null));
            }
        }

        @Override
        public void onHover() {
            actions.getSelected()
                .entry()
                .onHover(new ActionInput(GameState.global(), HUD.get().getCursor().getSelected()));
            infoUI.showInfo(actions.getSelected().entry());
        }
    }

}
