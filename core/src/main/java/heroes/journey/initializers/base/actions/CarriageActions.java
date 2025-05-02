package heroes.journey.initializers.base.actions;

import java.util.ArrayList;
import java.util.List;

import heroes.journey.GameState;
import heroes.journey.components.NamedComponent;
import heroes.journey.components.PositionComponent;
import heroes.journey.components.utils.Utils;
import heroes.journey.entities.actions.Action;
import heroes.journey.entities.actions.results.ActionListResult;
import heroes.journey.entities.actions.results.StringResult;

public class CarriageActions {

    public static Action carriage;
    public static List<Action> carriageActions;

    static {
        carriage = Action.builder().name("Carriage").returnsActionList(true).onSelect((gs, e) -> {
            Integer town = Utils.getLocation(gs, e);
            List<Action> carriageActions = getCarriageActions(gs, town);
            return new ActionListResult(carriageActions);
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
                return new StringResult("You have arrived at " + locationName + " on a carriage");
            })
            .build()
            .register();
        carriageActions.add(carriageAction);
    }
}
