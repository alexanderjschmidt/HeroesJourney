package heroes.journey.ui;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.Batch;

import heroes.journey.GameState;
import heroes.journey.components.ActionComponent;
import heroes.journey.components.PositionComponent;
import heroes.journey.entities.actions.Action;
import heroes.journey.entities.actions.TargetAction;
import heroes.journey.tilemap.wavefunction.ActionTerrain;
import heroes.journey.ui.hudstates.ActionSelectState;
import heroes.journey.utils.art.ResourceManager;

public class ActionMenu extends UI {

    private List<Action> options;
    private int selected = 0;

    public ActionMenu() {
        super(0, 0, 8, 1, true, true);
        this.setVisible(false);
    }

    public void open() {
        Entity selectedEntity = GameState.global().getCurrentEntity();
        ActionComponent selectedActions = ActionComponent.get(selectedEntity);
        PositionComponent selectedPosition = PositionComponent.get(selectedEntity);
        // Get selected Entities Actions
        List<Action> requirementsMetOptions = selectedActions.stream()
            .filter(action -> action.requirementsMet(GameState.global(), selectedEntity))
            .toList();
        if (selectedPosition != null) {
            // Get Tiles Factions Actions
            Entity faction = GameState.global()
                .getEntities()
                .getFaction(selectedPosition.getX(), selectedPosition.getY());
            if (faction != null) {
                ActionComponent factionActions = ActionComponent.get(faction);
                if (factionActions != null) {
                    List<Action> requirementsMetFactionOptions = factionActions.stream()
                        .filter(action -> action.requirementsMet(GameState.global(), selectedEntity))
                        .toList();
                    requirementsMetOptions = Stream.concat(requirementsMetOptions.stream(),
                        requirementsMetFactionOptions.stream()).distinct().collect(Collectors.toList());
                }
            }
            // Get Tiles Environment Actions
            ActionTerrain environment = GameState.global()
                .getMap()
                .getEnvironment(selectedPosition.getX(), selectedPosition.getY());
            if (environment != null) {
                List<Action> requirementsMetEnvironmentOptions = environment.getActions()
                    .stream()
                    .filter(action -> action.requirementsMet(GameState.global(), selectedEntity))
                    .toList();
                requirementsMetOptions = Stream.concat(requirementsMetOptions.stream(),
                    requirementsMetEnvironmentOptions.stream()).distinct().collect(Collectors.toList());
            }
        }
        HUD.get().setState(new ActionSelectState(requirementsMetOptions));
    }

    // TODO Make this only accessed by entering the actionSelect State
    public void open(List<Action> options) {
        GameState.global().getRangeManager().clearRange();
        if (options.isEmpty()) {
            HUD.get().getCursor().clearSelected();
            HUD.get().revertToPreviousState();
            return;
        }
        this.options = options;
        selected = 0;
        options.get(selected).onHover(GameState.global(), HUD.get().getCursor().getSelected());
        this.setSize(8, options.size());
        this.setPosition(0, 0);
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
    }

    public void decrement() {
        if (selected > 0) {
            selected--;
            options.get(selected).onHover(GameState.global(), HUD.get().getCursor().getSelected());
        } else {
            selected = options.size() - 1;
        }
    }

    public void select() {
        options.get(selected).onSelect(GameState.global(), HUD.get().getCursor().getSelected());
        if (!(options.get(selected) instanceof TargetAction) && HUD.get().getCursor().getSelected() != null) {
            GameState.global().nextTurn();
        }
    }

    public Action getSelected() {
        return options.get(selected);
    }

}
