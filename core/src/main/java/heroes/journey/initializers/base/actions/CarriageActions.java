package heroes.journey.initializers.base.actions;

import heroes.journey.GameState;
import heroes.journey.components.character.NamedComponent;
import heroes.journey.components.character.PositionComponent;
import heroes.journey.components.utils.Utils;
import heroes.journey.entities.actions.Action;
import heroes.journey.ui.HUD;
import heroes.journey.ui.ScrollPaneEntry;
import heroes.journey.ui.hudstates.ActionSelectState;

import java.util.ArrayList;
import java.util.List;

public class CarriageActions {
    
    public static Action carriage;
    public static List<Action> carriageActions;

    static {
        carriage = Action.builder().name("Carriage").terminal(false).onSelect((gs, e) -> {
            Integer town = Utils.getLocation(gs, e);
            List<Action> carriageActions = getCarriageActions(gs, town);
            List<ScrollPaneEntry<Action>> options = Utils.convertToScrollEntries(carriageActions);
            HUD.get().setState(new ActionSelectState(options));
            return null;
        }).build().register();
        carriageActions = new ArrayList<>();
    }

    private static List<Action> getCarriageActions(GameState gameState, Integer townId) {
        List<Action> questActions = new ArrayList<>();
        String townName = NamedComponent.get(gameState.getWorld(), townId, "---");
        for (Action carriageAction : carriageActions) {
            if (townName.equals("---") || carriageAction.toString().contains(townName)) {
                continue;
            }
            questActions.add(carriageAction);
        }
        return questActions;
    }

    public static void createCarriageAction(GameState gameState, Integer town) {
        PositionComponent positionComponentLocation = PositionComponent.get(gameState.getWorld(), town);
        String locationName = NamedComponent.get(gameState.getWorld(), town, "Unknown Location");
        Action carriageAction = Action.builder()
            .name("Carriage to " + locationName.toString())
            .onSelect((gs, e) -> {
                PositionComponent positionComponent = PositionComponent.get(gs.getWorld(), e);
                positionComponent.setPos(positionComponentLocation.getX(), positionComponentLocation.getY());
                return "You have arrived at " + locationName + " on a carriage";
            })
            .build()
            .register();
        carriageActions.add(carriageAction);
    }
}
