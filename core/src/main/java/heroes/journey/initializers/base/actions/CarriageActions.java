package heroes.journey.initializers.base.actions;

import heroes.journey.components.NamedComponent;
import heroes.journey.components.PositionComponent;
import heroes.journey.components.utils.Utils;
import heroes.journey.entities.actions.TargetAction;
import heroes.journey.entities.actions.results.StringResult;
import heroes.journey.initializers.InitializerInterface;
import heroes.journey.tilemap.features.Feature;
import heroes.journey.tilemap.features.FeatureManager;
import heroes.journey.tilemap.features.FeatureType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CarriageActions implements InitializerInterface {

    public static TargetAction<Feature> carriage;

    static {
        carriage = TargetAction.<Feature>targetBuilder().name("Carriage").getTargets((input) -> {
            UUID currentLocation = Utils.getLocation(input);
            List<Feature> featuresWithCarriage = new ArrayList<>();
            for (Feature feature : FeatureManager.get().values()) {
                if (feature.getType() != FeatureType.KINGDOM || currentLocation == feature.entityId) {
                    continue;
                }
                featuresWithCarriage.add(feature);
            }
            return featuresWithCarriage;
        }).onSelectTarget((targetInput) -> {
            UUID town = targetInput.getGameState().getEntities().getLocation(targetInput.getInput().location.getX(), targetInput.getInput().location.getY());
            PositionComponent positionComponentLocation = PositionComponent.get(targetInput.getGameState().getWorld(), town);
            String locationName = NamedComponent.get(targetInput.getGameState().getWorld(), town, "Unknown Location");
            PositionComponent positionComponent = PositionComponent.get(targetInput.getGameState().getWorld(), targetInput.getEntityId());
            positionComponent.setPos(positionComponentLocation.getX(), positionComponentLocation.getY());
            return new StringResult("You have arrived at " + locationName + " on a carriage");
        }).build().register();
    }
}
