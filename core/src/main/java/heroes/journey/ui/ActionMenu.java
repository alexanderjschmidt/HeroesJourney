package heroes.journey.ui;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.Batch;
import heroes.journey.GameState;
import heroes.journey.components.PositionComponent;
import heroes.journey.components.PossibleActionsComponent;
import heroes.journey.components.utils.Utils;
import heroes.journey.entities.actions.Action;
import heroes.journey.tilemap.wavefunction.ActionTerrain;
import heroes.journey.ui.hudstates.ActionSelectState;
import heroes.journey.utils.art.ResourceManager;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ActionMenu extends UI {

    private List<Action> options;
    private int selected = 0;
    private int MAX_HEIGHT = 14;
    private ActionDetailUI actionDetailUI;

    public ActionMenu(ActionDetailUI actionDetailUI) {
        super(0, 0, 8, 1, true, true);
        this.setVisible(false);
        this.actionDetailUI = actionDetailUI;
    }

    public void open() {
        Entity selectedEntity = GameState.global().getCurrentEntity();
        PossibleActionsComponent selectedActions = PossibleActionsComponent.get(selectedEntity);
        PositionComponent selectedPosition = PositionComponent.get(selectedEntity);
        // Get selected Entities Actions
        List<Action> requirementsMetOptions = selectedActions.stream()
            .filter(action -> action.requirementsMet(GameState.global(), selectedEntity))
            .toList();
        if (selectedPosition != null) {
            // Get Tiles Factions Actions
            requirementsMetOptions = Stream.concat(requirementsMetOptions.stream(),
                getFactionActions(selectedEntity).stream()).distinct().collect(Collectors.toList());
            // Get Tiles Environment Actions
            requirementsMetOptions = Stream.concat(requirementsMetOptions.stream(),
                    getTileActions(selectedEntity, selectedPosition).stream())
                .distinct()
                .collect(Collectors.toList());
        }
        HUD.get().setState(new ActionSelectState(requirementsMetOptions));
    }

    private List<Action> getFactionActions(Entity selectedEntity) {
        Entity faction = Utils.getLocationsFaction(GameState.global(), selectedEntity);
        if (faction != null) {
            PossibleActionsComponent factionActions = PossibleActionsComponent.get(faction);
            if (factionActions != null) {
                return factionActions.stream()
                    .filter(action -> action.requirementsMet(GameState.global(), selectedEntity))
                    .toList();
            }
        }
        return new ArrayList<>();
    }

    private List<Action> getTileActions(Entity selectedEntity, PositionComponent selectedPosition) {
        ActionTerrain environment = GameState.global()
            .getMap()
            .getEnvironment(selectedPosition.getX(), selectedPosition.getY());
        if (environment != null) {
            return environment.getActions()
                .stream()
                .filter(action -> action.requirementsMet(GameState.global(), selectedEntity))
                .toList();
        }
        return new ArrayList<>();
    }

    public void open(List<Action> options) {
        GameState.global().getRangeManager().clearRange();
        if (options.isEmpty()) {
            System.out.println("Options Empty");
            HUD.get().getCursor().clearSelected();
            HUD.get().revertToPreviousState();
            return;
        }
        this.options = options;
        selected = 0;
        options.get(selected).onHover(GameState.global(), HUD.get().getCursor().getSelected());
        this.setSize(8, Math.min(MAX_HEIGHT, options.size()));
        this.setPosition(0, 0);
        actionDetailUI.setAction(options.get(selected));
    }

    @Override
    public void update() {
    }

    @Override
    public void drawUI(Batch batch, float parentAlpha) {
        batch.draw(ResourceManager.get().select, getX() + (0.5f * HUD.FONT_SIZE),
            getY() + ((height - (selected + 0.5f)) * HUD.FONT_SIZE));
        for (int i = 0; i < options.size(); i++) {
            drawText(batch, options.get(i).toString(), 1, i);
        }
    }

    public void increment() {
        if (selected < options.size() - 1) {
            selected++;
            options.get(selected).onHover(GameState.global(), HUD.get().getCursor().getSelected());
        } else {
            selected = 0;
        }
        actionDetailUI.setAction(options.get(selected));
    }

    public void decrement() {
        if (selected > 0) {
            selected--;
            options.get(selected).onHover(GameState.global(), HUD.get().getCursor().getSelected());
        } else {
            selected = options.size() - 1;
        }
        actionDetailUI.setAction(options.get(selected));
    }

    public void select() {
        Action selectedAction = options.get(selected);
        System.out.println("Selected " + selectedAction + " " + selectedAction.isTerminal());
        selectedAction.onSelect(GameState.global(), HUD.get().getCursor().getSelected());
    }

    public Action getSelected() {
        return options.get(selected);
    }

}
