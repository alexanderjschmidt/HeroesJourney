package heroes.journey.ui.windows;

import static heroes.journey.registries.Registries.RegionManager;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import heroes.journey.GameState;
import heroes.journey.components.PositionComponent;
import heroes.journey.components.PossibleActionsComponent;
import heroes.journey.components.character.ActionComponent;
import heroes.journey.entities.actions.ActionEntry;
import heroes.journey.entities.actions.ActionInput;
import heroes.journey.initializers.utils.Utils;
import heroes.journey.systems.GameWorld;
import heroes.journey.tilemap.Feature;
import heroes.journey.tilemap.Region;
import heroes.journey.ui.HUD;
import heroes.journey.ui.ScrollPane;
import heroes.journey.ui.ScrollPaneEntry;
import heroes.journey.ui.UI;

public class ActionMenu extends UI {

    private final ScrollPane<ActionEntry> actions;
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

    public static List<ActionEntry> getActionsFor(GameState gameState, UUID selectedEntity) {
        GameWorld world = gameState.getWorld();
        PossibleActionsComponent selectedActions = PossibleActionsComponent.get(world, selectedEntity);
        PositionComponent selectedPosition = PositionComponent.get(world, selectedEntity);
        // Get selected Entities Actions
        List<ActionEntry> requirementsMetOptions = selectedActions.getPossibleActions(selectedEntity);
        if (selectedPosition != null) {
            // Get Region Locations Actions
            requirementsMetOptions = Stream.concat(requirementsMetOptions.stream(),
                getRegionFeatures(gameState, selectedEntity).stream()).collect(Collectors.toList());
        }
        return requirementsMetOptions.stream().distinct().toList();
    }

    // Make this return a list of locations to interact with. and they will have sub lists of what you can do there
    private static Set<ActionEntry> getRegionFeatures(GameState gameState, UUID selectedEntity) {
        String regionId = Utils.getRegion(gameState, selectedEntity);
        Region region = RegionManager.get(regionId);
        Set<ActionEntry> actions = new HashSet<>();
        for (Feature feature : region.getFeatures()) {
            PossibleActionsComponent factionActions = PossibleActionsComponent.get(gameState.getWorld(),
                feature.getEntityId());
            if (factionActions != null) {
                actions.addAll(factionActions.getPossibleActions(feature.getEntityId()));
            }
        }
        return actions;
    }

    public void open(List<ScrollPaneEntry<ActionEntry>> options) {
        actions.open(options);
    }

    public void select() {
        actions.selectWrapper();
    }

    public void handleInputs() {
        actions.handleInput();
    }

    private class ActionScrollPane extends ScrollPane<ActionEntry> {

        @Override
        public void open(List<ScrollPaneEntry<ActionEntry>> options) {
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
            ActionEntry action = getSelected().entry();
            UUID selectedEntity = HUD.get().getCursor().getSelected();
            System.out.println("Selected " + action + " " + selectedEntity);
            if (selectedEntity != null) {
                GameState.global()
                    .getWorld()
                    .edit(selectedEntity)
                    .create(ActionComponent.class)
                    .action(action);
            } else {
                action.getAction().onSelect(new ActionInput(GameState.global(), null), false);
            }
        }

        @Override
        public void onHover() {
            ActionEntry actionEntry = actions.getSelected().entry();
            ActionInput input = new ActionInput(GameState.global(), HUD.get().getCursor().getSelected(),
                actionEntry.getInput());
            actionEntry.getAction().onHover(input);
            infoUI.showInfo(actionEntry.getAction(), actionEntry.getInput());
        }
    }

}
