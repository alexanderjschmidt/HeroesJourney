package heroes.journey.initializers.base.actions;

import com.artemis.EntityEdit;
import heroes.journey.GameState;
import heroes.journey.components.character.MovementComponent;
import heroes.journey.components.character.NamedComponent;
import heroes.journey.components.character.PositionComponent;
import heroes.journey.components.utils.Utils;
import heroes.journey.entities.Position;
import heroes.journey.entities.actions.Action;
import heroes.journey.initializers.InitializerInterface;
import heroes.journey.initializers.base.Feature;
import heroes.journey.ui.HUD;
import heroes.journey.ui.ScrollPaneEntry;
import heroes.journey.ui.hudstates.ActionSelectState;
import heroes.journey.utils.Direction;
import heroes.journey.utils.ai.pathfinding.Cell;
import heroes.journey.utils.ai.pathfinding.EntityCursorPathing;
import heroes.journey.utils.worldgen.MapGenerationEffect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static heroes.journey.initializers.base.Map.features;

public class TravelActions implements InitializerInterface {

    public static Action travel;
    public static HashMap<String, List<Action>> travelConnections = new HashMap<>();

    static {
        travel = Action.builder().name("Travel").terminal(false).onSelect((gs, e) -> {
            Integer feature = Utils.getLocation(gs, e);
            List<Action> carriageActions = getTravelActions(gs, feature);
            List<ScrollPaneEntry<Action>> options = Utils.convertToScrollEntries(carriageActions);
            HUD.get().setState(new ActionSelectState(options));
            return null;
        }).build().register();

        // Add Travel Actions
        MapGenerationEffect travel = MapGenerationEffect.builder().name("travel").dependsOn(new String[]{heroes.journey.initializers.base.Map.trees.getName()}).applyEffect(gameState -> {
            for (Feature feature : features) {
                if (feature.connections.size() < 3) {
                    feature.makeConnections(features, 3);
                }
            }
            for (Feature feature : features) {
                Integer locationId = gameState.getEntities().getLocation(feature.location.getX(), feature.location.getY());
                if (locationId == null) {
                    System.out.println(feature);
                    continue;
                }
                String name = NamedComponent.get(gameState.getWorld(), locationId, "Unknown Location");
                List<Action> connections = new ArrayList<>(feature.connections.size());
                for (Feature connection : feature.connections) {
                    Direction dir = Direction.approximateDirection(feature.location, connection.location);
                    connections.add(getTravelAction(gameState, dir, connection));
                }
                travelConnections.put(name, connections);
            }
        }).build().register();
    }

    private static Action getTravelAction(GameState gameState, Direction dir, Feature feature) {
        Integer locationId = gameState.getEntities().getLocation(feature.location.getX(), feature.location.getY());
        if (locationId == null) {
            System.out.println(feature);
            return null;
        }
        String locationName = NamedComponent.get(gameState.getWorld(), locationId, "Unknown Location");
        return Action.builder().name(dir + " to " + locationName)
            .onHover((gs, e) -> {
                HUD.get().getCursor().setMapPointerLoc(new Position(feature.location.getX(), feature.location.getY()));
            })
            .onSelect((gs, e) -> {
                PositionComponent positionComponent = PositionComponent.get(gs.getWorld(), e);
                Cell path = new EntityCursorPathing().getPath(GameState.global().getMap(), positionComponent.getX(), positionComponent.getY(), feature.location.getX(), feature.location.getY(), e);
                EntityEdit entity = GameState.global().getWorld().edit(e);
                entity.create(MovementComponent.class).path(path.reverse());
                return null;
            }).build().register();
    }

    private static List<Action> getTravelActions(GameState gameState, Integer featureId) {
        String featureName = NamedComponent.get(gameState.getWorld(), featureId, "---");
        return travelConnections.get(featureName);
    }
}
