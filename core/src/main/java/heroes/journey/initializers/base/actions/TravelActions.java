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
import heroes.journey.entities.actions.TargetAction;
import heroes.journey.entities.actions.inputs.TargetInput;
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

import java.util.*;

import static heroes.journey.initializers.base.actions.BaseActions.popup;

public class TravelActions implements InitializerInterface {

    public static final Action explore, travel;
    public static final TargetAction<UUID> journey, wayfare;
    // TODO Pilgrimage lose a turn but go anywhere?
    // TODO No direction explore that expands in a circle?
    private static final HashMap<Direction, Action> exploreActions = new HashMap<>();

    static {
        for (Direction dir : Direction.values()) {
            if (dir != Direction.NODIRECTION) {
                Action exploreAction = Action.builder().name("Explore " + dir).onSelect((input) -> {
                    GameState gs = input.getGameState();
                    UUID e = input.getEntityId();
                    PositionComponent position = PositionComponent.get(gs.getWorld(), e);
                    MapComponent mapComponent = MapComponent.get(gs.getWorld(), e);

                    // TODO Based on Stamina/Body
                    int revealDistance = 8; // How far the cone extends
                    // TODO Based on Vision
                    int baseWidth = 0;       // Start width (0 = just 1 tile at origin)

                    FogUtils.revealCone(gs, mapComponent, position.getX(), position.getY(), revealDistance,
                        baseWidth, dir);

                    return new StringResult("You have explored the " + dir);
                }).cost(Cost.builder().stamina(5).health(2).build()).build().register();
                exploreActions.put(dir, exploreAction);
            }
        }

        explore = Action.builder()
            .name("Explore")
            .returnsActionList(true)
            .onSelect((input) -> new ActionListResult(exploreActions.values().stream().toList()))
            .build()
            .register();
        journey = TargetAction.<UUID>targetBuilder().name("Journey")
            .getTargets((input) -> {
                UUID currentLocation = Utils.getLocation(input);
                MapComponent mapComponent = MapComponent.get(input.getGameState().getWorld(), input.getEntityId());
                List<UUID> journeyLocations = new ArrayList<>();
                for (UUID knownLocation : mapComponent.getKnownLocations()) {
                    if (!Objects.equals(currentLocation, knownLocation))
                        journeyLocations.add(knownLocation);
                }
                return journeyLocations;
            }).getTargetDisplayName((input) -> NamedComponent.get(input.getGameState().getWorld(), input.getInput(), "Unknown Location"))
            .onHoverTarget((input) -> {
                Feature feature = FeatureManager.getFeature(input.getInput());
                HUD.get()
                    .getCursor()
                    .setMapPointerLoc(new Position(feature.location.getX(), feature.location.getY()));
            }).onSelectTarget((input) -> {
                GameState gs = input.getGameState();
                UUID e = input.getEntityId();
                Feature feature = FeatureManager.getFeature(input.getInput());
                String locationName = NamedComponent.get(gs.getWorld(), input.getInput(), "Unknown Location");
                PositionComponent positionComponent = PositionComponent.get(gs.getWorld(), e);
                Cell path = new EntityCursorPathing().getPath(gs.getMap(), positionComponent.getX(),
                    positionComponent.getY(), feature.location.getX(), feature.location.getY(), e);

                Queue<Runnable> events = new LinkedList<>();
                events.add(() -> {
                    gs.getWorld().edit(e).create(MovementComponent.class).path(path.reverse());
                });
                events.add(() -> {
                    BaseActions.popupMessage = "You have traveled to " + locationName;
                    gs.getWorld().edit(e).create(ActionComponent.class).action(popup);
                });
                return new MultiStepResult(events);
            }).onSelectAITarget((input) -> {
                GameState gs = input.getGameState();
                UUID e = input.getEntityId();
                Feature feature = FeatureManager.getFeature(input.getInput());
                String locationName = NamedComponent.get(gs.getWorld(), input.getInput(), "Unknown Location");
                PositionComponent positionComponent = PositionComponent.get(gs.getWorld(), e);
                Cell path = new EntityCursorPathing().getPath(gs.getMap(), positionComponent.getX(),
                    positionComponent.getY(), feature.location.getX(), feature.location.getY(), e);

                Cell end = gs.getEntities().moveEntity(e, path);
                positionComponent.setPos(end.x, end.y);
                positionComponent.sync();
                return new StringResult("You have traveled to " + locationName);
            }).costTarget(Cost.<TargetInput<UUID>>builder().stamina(1).multiplier((input) -> {
                UUID e = input.getEntityId();
                Feature feature = FeatureManager.getFeature(input.getInput());
                PositionComponent positionComponent = PositionComponent.get(input.getGameState().getWorld(), e);
                Position entityPos = new Position(positionComponent.getX(), positionComponent.getY());
                return (double) entityPos.distanceTo(feature.location);
            }).build()).build().register();
        wayfare = journey.toBuilder().name("Wayfare")
            .getTargets((input) -> {
                UUID featureId = Utils.getLocation(input);
                Feature feature = FeatureManager.get().get(featureId);
                List<UUID> wayfareLocations = new ArrayList<>();
                for (UUID connectionId : feature.getConnections()) {
                    Feature connection = FeatureManager.getFeature(connectionId);
                    wayfareLocations.add(connection.entityId);
                }
                return wayfareLocations;
            }).requirementsMet((input) -> {
                UUID featureId = Utils.getLocation(input);
                Feature feature = FeatureManager.get().get(featureId);
                return feature.getType() == FeatureType.KINGDOM || feature.getType() == FeatureType.TOWN ?
                    ShowAction.YES :
                    ShowAction.GRAYED;
            }).costTarget(Cost.<TargetInput<UUID>>builder().stamina(2).build())
            .build().register();
        List<Action> travelActionOptions = new ArrayList<>();
        travelActionOptions.add(explore);
        travelActionOptions.add(wayfare);
        travelActionOptions.add(journey);
        travel = Action.builder()
            .name("Travel")
            .returnsActionList(true)
            .onSelect((input) -> new ActionListResult(travelActionOptions))
            .build()
            .register();
    }
}
