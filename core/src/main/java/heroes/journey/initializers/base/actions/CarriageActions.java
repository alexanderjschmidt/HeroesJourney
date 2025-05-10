package heroes.journey.initializers.base.actions;

import heroes.journey.GameState;
import heroes.journey.components.NamedComponent;
import heroes.journey.components.PositionComponent;
import heroes.journey.components.utils.Utils;
import heroes.journey.entities.actions.Action;
import heroes.journey.entities.actions.results.ActionListResult;
import heroes.journey.entities.actions.results.StringResult;
import heroes.journey.initializers.InitializerInterface;
import heroes.journey.tilemap.features.Feature;
import heroes.journey.tilemap.features.FeatureManager;
import heroes.journey.tilemap.features.FeatureType;
import heroes.journey.utils.worldgen.MapGenerationEffect;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CarriageActions implements InitializerInterface {

    public static Action carriage;
    public static List<Action> carriageActions;

    static {
        carriage = Action.builder().name("Carriage").returnsActionList(true).onSelect((gs, e) -> {
            UUID town = Utils.getLocation(gs, e);
            List<Action> carriageActions = getCarriageActions(gs, town);
            return new ActionListResult(carriageActions);
        }).build().register();
        // Add Carriage Actions
        MapGenerationEffect carriage = MapGenerationEffect.builder()
            .name("carriage")
            .runOnLoad(true)
            .dependsOn(new String[]{heroes.journey.initializers.base.Map.trees.getName()})
            .applyEffect(gameState -> {
                for (Feature feature : FeatureManager.get().values()) {
                    if (feature.getType() != FeatureType.KINGDOM) {
                        continue;
                    }
                    UUID locationId = gameState.getEntities()
                        .getLocation(feature.location.getX(), feature.location.getY());
                    if (locationId == null) {
                        System.out.println(feature);
                        continue;
                    }
                    createCarriageAction(gameState, feature);
                }
            })
            .build()
            .register();
        carriageActions = new ArrayList<>();
    }

    private static List<Action> getCarriageActions(GameState gameState, UUID townId) {
        List<Action> questActions = new ArrayList<>();
        String townName = NamedComponent.get(gameState.getWorld(), townId, "---");
        for (int i = 0; i < carriageActions.size(); i++) {
            if (townName.equals("---") || carriageActions.get(i).toString().contains(townName)) {
                continue;
            }
            questActions.add(carriageActions.get(i));
        }
        return questActions;
    }

    public static void createCarriageAction(GameState gameState, Feature feature) {
        UUID town = gameState.getEntities().getLocation(feature.location.getX(), feature.location.getY());
        PositionComponent positionComponentLocation = PositionComponent.get(gameState.getWorld(), town);
        String locationName = NamedComponent.get(gameState.getWorld(), town, "Unknown Location");
        Action carriageAction = Action.builder().name("Carriage to " + locationName).onSelect((gs, e) -> {
            PositionComponent positionComponent = PositionComponent.get(gs.getWorld(), e);
            positionComponent.setPos(positionComponentLocation.getX(), positionComponentLocation.getY());
            return new StringResult("You have arrived at " + locationName + " on a carriage");
        }).build().register();
        carriageActions.add(carriageAction);
    }
}
