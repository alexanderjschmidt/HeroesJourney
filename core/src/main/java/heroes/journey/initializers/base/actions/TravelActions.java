package heroes.journey.initializers.base.actions;

import static heroes.journey.initializers.base.Map.KINGDOM;
import static heroes.journey.initializers.base.Map.TOWN;
import static heroes.journey.initializers.base.actions.BaseActions.popup;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.UUID;

import heroes.journey.GameState;
import heroes.journey.components.NamedComponent;
import heroes.journey.components.PositionComponent;
import heroes.journey.components.character.ActionComponent;
import heroes.journey.components.character.MapComponent;
import heroes.journey.components.character.MovementComponent;
import heroes.journey.entities.Position;
import heroes.journey.entities.actions.Action;
import heroes.journey.entities.actions.Cost;
import heroes.journey.entities.actions.ShowAction;
import heroes.journey.entities.actions.TargetAction;
import heroes.journey.entities.actions.inputs.ActionInput;
import heroes.journey.entities.actions.inputs.TargetInput;
import heroes.journey.entities.actions.results.ActionListResult;
import heroes.journey.entities.actions.results.ActionResult;
import heroes.journey.entities.actions.results.MultiStepResult;
import heroes.journey.entities.actions.results.StringResult;
import heroes.journey.initializers.InitializerInterface;
import heroes.journey.initializers.utils.Utils;
import heroes.journey.registries.FeatureManager;
import heroes.journey.tilemap.Feature;
import heroes.journey.tilemap.FogUtils;
import heroes.journey.ui.HUD;
import heroes.journey.utils.Direction;
import heroes.journey.utils.ai.pathfinding.Cell;
import heroes.journey.utils.ai.pathfinding.EntityCursorPathing;

public class TravelActions implements InitializerInterface {

    public static Action travel;
    public static TargetAction<UUID> journey, wayfare;
    public static TargetAction<Direction> explore;
    // TODO Pilgrimage lose a turn but go anywhere?
    // TODO No direction explore that expands in a circle?

    // TODO FIX JOURNEY
    @Override
    public void init() {
        explore = new TargetAction<Direction>("explore", "Explore", "Explore in a direction",
            new Cost(5, 2, 0, 0)) {
            @Override
            public List<Direction> getTargets(ActionInput input) {
                return List.of(Direction.getDirections());
            }

            @Override
            public String getTargetDisplayName(TargetInput<Direction> input) {
                return "Explore " + input;
            }

            @Override
            protected ActionResult onSelectTarget(TargetInput<Direction> input) {
                GameState gs = input.getGameState();
                UUID e = input.getEntityId();
                PositionComponent position = PositionComponent.get(gs.getWorld(), e);
                MapComponent mapComponent = MapComponent.get(gs.getWorld(), e);

                // TODO Based on Stamina/Body
                int revealDistance = 8; // How far the cone extends
                // TODO Based on Vision
                int baseWidth = 0;       // Start width (0 = just 1 tile at origin)

                FogUtils.revealCone(gs, mapComponent, position.getX(), position.getY(), revealDistance,
                    baseWidth, input.getInput());

                return new StringResult("You have explored the " + input.getInput());
            }
        }.register();
        // TODO clean up journey to use more utils?
        journey = new TargetAction<UUID>("journey", "Journey", "Travel to a known location",
            new Cost(1, 0, 0, 0)) {
            @Override
            public List<UUID> getTargets(ActionInput input) {
                UUID currentLocation = Utils.getLocation(input);
                MapComponent mapComponent = MapComponent.get(input.getGameState().getWorld(),
                    input.getEntityId());
                List<UUID> journeyLocations = new ArrayList<>();
                for (UUID knownLocation : mapComponent.getKnownLocations()) {
                    if (!Objects.equals(currentLocation, knownLocation))
                        journeyLocations.add(knownLocation);
                }
                return journeyLocations;
            }

            @Override
            public String getTargetDisplayName(TargetInput<UUID> input) {
                return NamedComponent.get(input.getGameState().getWorld(), input.getInput(),
                    "Unknown Location");
            }

            @Override
            protected void onHoverTarget(TargetInput<UUID> input) {
                Feature feature = FeatureManager.getFeature(input.getInput());
                HUD.get()
                    .getCursor()
                    .setMapPointerLoc(new Position(feature.location.getX(), feature.location.getY()));
            }

            @Override
            protected ActionResult onSelectTarget(TargetInput<UUID> input) {
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
            }

            @Override
            protected ActionResult onSelectAITarget(TargetInput<UUID> input) {
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
            }
        }.register();
        wayfare = new TargetAction<UUID>("wayfare", "Wayfare", "Travel to a connected location",
            new Cost(2, 0, 0, 0)) {
            @Override
            public List<UUID> getTargets(ActionInput input) {
                UUID featureId = Utils.getLocation(input);
                Feature feature = FeatureManager.get().get(featureId);
                List<UUID> wayfareLocations = new ArrayList<>();
                for (UUID connectionId : feature.getConnections()) {
                    Feature connection = FeatureManager.getFeature(connectionId);
                    wayfareLocations.add(connection.entityId);
                }
                return wayfareLocations;
            }

            @Override
            public ShowAction internalRequirementsMet(ActionInput input) {
                UUID featureId = Utils.getLocation(input);
                Feature feature = FeatureManager.get().get(featureId);
                return feature.getType() == KINGDOM || feature.getType() == TOWN ?
                    ShowAction.YES :
                    ShowAction.GRAYED;
            }

            @Override
            public String getTargetDisplayName(TargetInput<UUID> input) {
                return NamedComponent.get(input.getGameState().getWorld(), input.getInput(),
                    "Unknown Location");
            }

            @Override
            protected void onHoverTarget(TargetInput<UUID> input) {
                Feature feature = FeatureManager.getFeature(input.getInput());
                HUD.get()
                    .getCursor()
                    .setMapPointerLoc(new Position(feature.location.getX(), feature.location.getY()));
            }

            @Override
            protected ActionResult onSelectTarget(TargetInput<UUID> input) {
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
            }

            @Override
            protected ActionResult onSelectAITarget(TargetInput<UUID> input) {
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
            }
        }.register();

        List<Action> travelActionOptions = new ArrayList<>();
        travelActionOptions.add(explore);
        travelActionOptions.add(wayfare);
        travelActionOptions.add(journey);
        travel = new Action("travel", "Travel", "Choose a travel option", true, null) {
            @Override
            public ActionResult internalOnSelect(ActionInput input) {
                return new ActionListResult(travelActionOptions);
            }
        }.register();
    }
}
