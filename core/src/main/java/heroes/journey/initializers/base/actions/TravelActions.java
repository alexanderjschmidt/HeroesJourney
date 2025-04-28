package heroes.journey.initializers.base.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import com.artemis.EntityEdit;

import heroes.journey.GameState;
import heroes.journey.components.character.MapComponent;
import heroes.journey.components.character.MovementComponent;
import heroes.journey.components.character.NamedComponent;
import heroes.journey.components.character.PositionComponent;
import heroes.journey.components.utils.Utils;
import heroes.journey.entities.Position;
import heroes.journey.entities.actions.Action;
import heroes.journey.entities.actions.ShowAction;
import heroes.journey.initializers.InitializerInterface;
import heroes.journey.tilemap.features.Feature;
import heroes.journey.tilemap.features.FeatureManager;
import heroes.journey.tilemap.features.FeatureType;
import heroes.journey.ui.HUD;
import heroes.journey.ui.hudstates.ActionSelectState;
import heroes.journey.utils.ai.pathfinding.Cell;
import heroes.journey.utils.ai.pathfinding.EntityCursorPathing;
import heroes.journey.utils.worldgen.MapGenerationEffect;

public class TravelActions implements InitializerInterface {

    public static Action explore, wayfare, journey, travel;
    // TODO Pilgrimage lose a turn but go anywhere?
    private static HashMap<Integer,Action> travelActions = new HashMap<>();
    static List<Action> travelActionOptions = new ArrayList<>();

    static {
        explore = Action.builder().name("Explore").terminal(false).onSelect((gs, e) -> {
            return null;
        }).build().register();
        wayfare = Action.builder().name("Wayfare").terminal(false).onSelect((gs, e) -> {
            Integer featureId = Utils.getLocation(gs, e);
            Feature feature = FeatureManager.get().get(featureId);
            List<Action> travelActions = getTravelActions(gs, feature);
            HUD.get().setState(new ActionSelectState(travelActions));
            return null;
        }).requirementsMet((gs, e) -> {
            Integer featureId = Utils.getLocation(gs, e);
            Feature feature = FeatureManager.get().get(featureId);
            return feature.getType() == FeatureType.KINGDOM || feature.getType() == FeatureType.TOWN ?
                ShowAction.YES :
                ShowAction.GRAYED;
        }).build().register();
        journey = Action.builder().name("Journey").terminal(false).onSelect((gs, e) -> {
            Integer feature = Utils.getLocation(gs, e);
            MapComponent mapComponent = MapComponent.get(gs.getWorld(), e);
            List<Action> journeyActions = new ArrayList<>();
            for (Integer knownLocation : mapComponent.getKnownLocations()) {
                if (!Objects.equals(feature, knownLocation))
                    journeyActions.add(travelActions.get(knownLocation));
            }
            HUD.get().setState(new ActionSelectState(journeyActions));
            return null;
        }).requirementsMet((gs, e) -> {
            MapComponent mapComponent = MapComponent.get(gs.getWorld(), e);
            System.out.println(mapComponent.getKnownLocations().size());
            return mapComponent.getKnownLocations().size() > 1 ? ShowAction.YES : ShowAction.GRAYED;
        }).build().register();
        travelActionOptions.add(explore);
        travelActionOptions.add(wayfare);
        travelActionOptions.add(journey);
        travel = Action.builder().name("Travel").terminal(false).onSelect((gs, e) -> {
            HUD.get().setState(new ActionSelectState(travelActionOptions));
            return null;
        }).build().register();
        // Add Travel Actions
        MapGenerationEffect travel = MapGenerationEffect.builder()
            .name("travel")
            .dependsOn(new String[] {heroes.journey.initializers.base.Map.trees.getName()})
            .applyEffect(gameState -> {
                for (Feature feature : FeatureManager.get().values()) {
                    if (feature.connections.size() < 3) {
                        feature.makeConnections(3);
                    }
                }
                for (Feature feature : FeatureManager.get().values()) {
                    Integer locationId = gameState.getEntities()
                        .getLocation(feature.location.getX(), feature.location.getY());
                    if (locationId == null) {
                        System.out.println(feature);
                        continue;
                    }
                    travelActions.put(locationId, createTravelAction(gameState, feature));
                }
            })
            .build()
            .register();
    }

    private static Action createTravelAction(GameState gameState, Feature feature) {
        Integer locationId = gameState.getEntities()
            .getLocation(feature.location.getX(), feature.location.getY());
        if (locationId == null) {
            System.out.println(feature);
            return null;
        }
        String locationName = NamedComponent.get(gameState.getWorld(), locationId, "Unknown Location");
        return Action.builder().name(locationName).onHover((gs, e) -> {
            HUD.get()
                .getCursor()
                .setMapPointerLoc(new Position(feature.location.getX(), feature.location.getY()));
        }).onSelect((gs, e) -> {
            PositionComponent positionComponent = PositionComponent.get(gs.getWorld(), e);
            Cell path = new EntityCursorPathing().getPath(GameState.global().getMap(),
                positionComponent.getX(), positionComponent.getY(), feature.location.getX(),
                feature.location.getY(), e);
            EntityEdit entity = GameState.global().getWorld().edit(e);
            entity.create(MovementComponent.class).path(path.reverse());
            return null;
        }).build().register();
    }

    private static Action getTravelAction(GameState gameState, Integer featureId) {
        String featureName = NamedComponent.get(gameState.getWorld(), featureId, "---");
        return travelActions.get(featureName);
    }

    private static List<Action> getTravelActions(GameState gameState, Feature feature) {
        List<Action> actions = new ArrayList<>();
        for (Feature connection : feature.getConnections()) {
            actions.add(travelActions.get(connection.entityId));
        }
        return actions;
    }
}
