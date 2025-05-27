package heroes.journey.initializers.base.actions;

import static heroes.journey.initializers.base.Map.KINGDOM;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import heroes.journey.components.NamedComponent;
import heroes.journey.components.PositionComponent;
import heroes.journey.entities.actions.TargetAction;
import heroes.journey.entities.actions.results.StringResult;
import heroes.journey.initializers.InitializerInterface;
import heroes.journey.initializers.utils.Utils;
import heroes.journey.registries.FeatureManager;
import heroes.journey.tilemap.Feature;

public class CarriageActions implements InitializerInterface {

    public static TargetAction<Feature> carriage;

    @Override
    public void init() {
        carriage = TargetAction.<Feature>targetBuilder().name("Carriage").getTargets((input) -> {
            UUID currentLocation = Utils.getLocation(input);
            List<Feature> featuresWithCarriage = new ArrayList<>();
            for (Feature feature : FeatureManager.get().values()) {
                if (feature.getType() != KINGDOM || currentLocation == feature.entityId) {
                    continue;
                }
                featuresWithCarriage.add(feature);
            }
            return featuresWithCarriage;
        }).onSelectTarget((targetInput) -> {
            UUID town = targetInput.getGameState()
                .getEntities()
                .getLocation(targetInput.getInput().location.getX(), targetInput.getInput().location.getY());
            PositionComponent positionComponentLocation = PositionComponent.get(
                targetInput.getGameState().getWorld(), town);
            String locationName = NamedComponent.get(targetInput.getGameState().getWorld(), town,
                "Unknown Location");
            PositionComponent positionComponent = PositionComponent.get(targetInput.getGameState().getWorld(),
                targetInput.getEntityId());
            positionComponent.setPos(positionComponentLocation.getX(), positionComponentLocation.getY());
            return new StringResult("You have arrived at " + locationName + " on a carriage");
        }).build().register();
    }
}
