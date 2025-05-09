package heroes.journey.initializers.base.actions;

import heroes.journey.GameState;
import heroes.journey.components.NamedComponent;
import heroes.journey.components.PositionComponent;
import heroes.journey.components.character.ActionComponent;
import heroes.journey.components.character.MapComponent;
import heroes.journey.components.character.MovementComponent;
import heroes.journey.components.utils.Utils;
import heroes.journey.entities.Position;
import heroes.journey.entities.actions.Action;
import heroes.journey.entities.actions.Cost;
import heroes.journey.entities.actions.ShowAction;
import heroes.journey.entities.actions.results.ActionListResult;
import heroes.journey.entities.actions.results.MultiStepResult;
import heroes.journey.entities.actions.results.StringResult;
import heroes.journey.initializers.InitializerInterface;
import heroes.journey.tilemap.FogUtils;
import heroes.journey.tilemap.features.Feature;
import heroes.journey.tilemap.features.FeatureManager;
import heroes.journey.tilemap.features.FeatureType;
import heroes.journey.ui.HUD;
import heroes.journey.utils.Direction;
import heroes.journey.utils.ai.pathfinding.Cell;
import heroes.journey.utils.ai.pathfinding.EntityCursorPathing;
import heroes.journey.utils.worldgen.MapGenerationEffect;

import java.util.*;

import static heroes.journey.initializers.base.actions.BaseActions.popup;

public class TravelActions implements InitializerInterface {

    public static final Action explore, wayfare, journey, travel;
    // TODO Pilgrimage lose a turn but go anywhere?
    // TODO No direction explore that expands in a circle?
    private static final HashMap<Direction, Action> exploreActions = new HashMap<>();
    private static final HashMap<UUID, Action> journeyActionsList = new HashMap<>();
    private static final HashMap<UUID, Action> wayfareActionsList = new HashMap<>();
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

                    return new StringResult("You have explored the " + dir);
                }).cost(Cost.builder().stamina(5).mana(1).build()).build().register();
                exploreActions.put(dir, exploreAction);
            }
        }

        explore = Action.builder()
            .name("Explore")
            .returnsActionList(true)
            .onSelect((gs, e) -> new ActionListResult(exploreActions.values().stream().toList()))
            .build()
            .register();
        wayfare = Action.builder().name("Wayfare").returnsActionList(true).onSelect((gs, e) -> {
            UUID featureId = Utils.getLocation(gs, e);
            Feature feature = FeatureManager.get().get(featureId);
            List<Action> wayfareActions = getWayfareActions(gs, feature);
            return new ActionListResult(wayfareActions);
        }).requirementsMet((gs, e) -> {
            UUID featureId = Utils.getLocation(gs, e);
            Feature feature = FeatureManager.get().get(featureId);
            return feature.getType() == FeatureType.KINGDOM || feature.getType() == FeatureType.TOWN ?
                ShowAction.YES :
                ShowAction.GRAYED;
        }).build().register();
        journey = Action.builder().name("Journey").returnsActionList(true).onSelect((gs, e) -> {
            UUID feature = Utils.getLocation(gs, e);
            MapComponent mapComponent = MapComponent.get(gs.getWorld(), e);
            List<Action> journeyActions = new ArrayList<>();
            synchronized (mapComponent.getKnownLocations()) {
                for (UUID knownLocation : mapComponent.getKnownLocations()) {
                    if (!Objects.equals(feature, knownLocation))
                        journeyActions.add(journeyActionsList.get(knownLocation));
                }
            }
            return new ActionListResult(journeyActions);
        }).requirementsMet((gs, e) -> {
            MapComponent mapComponent = MapComponent.get(gs.getWorld(), e);
            //System.out.println(mapComponent.getKnownLocations().size());
            return mapComponent.getKnownLocations().size() > 1 ? ShowAction.YES : ShowAction.GRAYED;
        }).build().register();
        travelActionOptions.add(explore);
        travelActionOptions.add(wayfare);
        travelActionOptions.add(journey);
        travel = Action.builder()
            .name("Travel")
            .returnsActionList(true)
            .onSelect((gs, e) -> new ActionListResult(travelActionOptions))
            .build()
            .register();
        // Add Travel Actions
        MapGenerationEffect travel = MapGenerationEffect.builder()
            .name("travel")
            .dependsOn(new String[]{heroes.journey.initializers.base.Map.trees.getName()})
            .applyEffect(gameState -> {
                for (Feature feature : FeatureManager.get().values()) {
                    UUID locationId = gameState.getEntities()
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
        UUID locationId = gameState.getEntities()
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
            Cell path = new EntityCursorPathing().getPath(gs.getMap(), positionComponent.getX(),
                positionComponent.getY(), feature.location.getX(), feature.location.getY(), e);

            Queue<Runnable> events = new LinkedList<>();
            events.add(() -> gs.getWorld().edit(e).create(MovementComponent.class).path(path.reverse()));
            events.add(() -> {
                BaseActions.popupMessage = "You have traveled to " + locationName;
                gs.getWorld().edit(e).create(ActionComponent.class).action(popup);
            });

            return new MultiStepResult(events);
        }).cost(Cost.builder().stamina(1).multiplier((gs, e) -> {
            PositionComponent positionComponent = PositionComponent.get(gs.getWorld(), e);
            Position entityPos = new Position(positionComponent.getX(), positionComponent.getY());
            return (double) entityPos.distanceTo(feature.location);
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
