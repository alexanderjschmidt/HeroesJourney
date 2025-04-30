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
import heroes.journey.entities.actions.Cost;
import heroes.journey.entities.actions.ShowAction;
import heroes.journey.initializers.InitializerInterface;
import heroes.journey.tilemap.FogUtils;
import heroes.journey.tilemap.features.Feature;
import heroes.journey.tilemap.features.FeatureManager;
import heroes.journey.tilemap.features.FeatureType;
import heroes.journey.ui.HUD;
import heroes.journey.ui.hudstates.ActionSelectState;
import heroes.journey.utils.Direction;
import heroes.journey.utils.ai.pathfinding.Cell;
import heroes.journey.utils.ai.pathfinding.EntityCursorPathing;
import heroes.journey.utils.worldgen.MapGenerationEffect;

public class TravelActions implements InitializerInterface {

    public static final Action explore, wayfare, journey, travel;
    // TODO Pilgrimage lose a turn but go anywhere?
    // TODO No direction explore that expands in a circle?
    private static final HashMap<Direction,Action> exploreActions = new HashMap<>();
    private static final HashMap<Integer,Action> journeyActionsList = new HashMap<>();
    private static final HashMap<Integer,Action> wayfareActionsList = new HashMap<>();
    private static final List<Action> travelActionOptions = new ArrayList<>();

    static {
        for (Direction dir : Direction.values()) {
            if (dir != Direction.NODIRECTION) {
                Action exploreAction = Action.builder().name("Explore " + dir).onSelect((gs, e) -> {
                    PositionComponent position = PositionComponent.get(gs.getWorld(), e);
                    MapComponent mapComponent = MapComponent.get(gs.getWorld(), e);

                    // TODO Based on Stamina/Body
                    int revealDistance = 8; // How far the cone extends
                    // TODO Based on Vision
                    int baseWidth = 0;       // Start width (0 = just 1 tile at origin)

                    FogUtils.revealCone(gs, mapComponent, position.getX(), position.getY(), revealDistance,
                        baseWidth, dir);

                    return "You have explored the " + dir;
                }).cost(Cost.builder().stamina(5).mana(1).build()).build().register();
                exploreActions.put(dir, exploreAction);
            }
        }

        explore = Action.builder().name("Explore").terminal(false).onSelect((gs, e) -> {
            HUD.get().setState(new ActionSelectState(exploreActions.values().stream().toList()));
            return null;
        }).build().register();
        wayfare = Action.builder().name("Wayfare").terminal(false).onSelect((gs, e) -> {
            Integer featureId = Utils.getLocation(gs, e);
            Feature feature = FeatureManager.get().get(featureId);
            List<Action> wayfareActions = getWayfareActions(gs, feature);
            HUD.get().setState(new ActionSelectState(wayfareActions));
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
                    journeyActions.add(journeyActionsList.get(knownLocation));
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
                    Integer locationId = gameState.getEntities()
                        .getLocation(feature.location.getX(), feature.location.getY());
                    if (locationId == null) {
                        System.out.println(feature);
                        continue;
                    }
                    createTravelActions(gameState, feature);
                }
            })
            .build()
            .register();
    }

    private static void createTravelActions(GameState gameState, Feature feature) {
        Integer locationId = gameState.getEntities()
            .getLocation(feature.location.getX(), feature.location.getY());
        if (locationId == null) {
            System.out.println(feature);
            return;
        }
        String locationName = NamedComponent.get(gameState.getWorld(), locationId, "Unknown Location");
        Action journeyAction = Action.builder().name(locationName).onHover((gs, e) -> {
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
            return "You have traveled to " + locationName;
        }).cost(Cost.builder().stamina(1).multiplier((gs, e) -> {
            PositionComponent positionComponent = PositionComponent.get(gs.getWorld(), e);
            Position entityPos = new Position(positionComponent.getX(), positionComponent.getY());
            return (double)entityPos.distanceTo(feature.location);
        }).build()).build().register();
        journeyActionsList.put(locationId, journeyAction);

        if (feature.type == FeatureType.KINGDOM || feature.type == FeatureType.TOWN) {
            Action wayfareAction = journeyAction.toBuilder()
                .name("Road to " + locationName)
                .cost(Cost.builder().stamina(2).build())
                .build()
                .register();
            wayfareActionsList.put(locationId, wayfareAction);
        }
    }

    private static List<Action> getWayfareActions(GameState gameState, Feature feature) {
        List<Action> actions = new ArrayList<>();
        for (Feature connection : feature.getConnections()) {
            actions.add(wayfareActionsList.get(connection.entityId));
        }
        return actions;
    }
}
