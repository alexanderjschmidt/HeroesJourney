package heroes.journey.initializers.base.actions;

import heroes.journey.GameState;
import heroes.journey.components.character.NamedComponent;
import heroes.journey.components.character.PositionComponent;
import heroes.journey.components.utils.Utils;
import heroes.journey.entities.Position;
import heroes.journey.entities.actions.Action;
import heroes.journey.initializers.InitializerInterface;
import heroes.journey.initializers.base.Map;
import heroes.journey.ui.HUD;
import heroes.journey.ui.ScrollPaneEntry;
import heroes.journey.ui.hudstates.ActionSelectState;
import heroes.journey.utils.Direction;
import heroes.journey.utils.worldgen.MapGenerationEffect;

import java.util.*;

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
            for (Position feature : heroes.journey.initializers.base.Map.features) {
                Integer locationId = gameState.getEntities().getLocation(feature.getX(), feature.getY());
                if (locationId == null) {
                    System.out.println(feature);
                    continue;
                }
                String name = NamedComponent.get(gameState.getWorld(), locationId, "Unknown Location");
                EnumMap<Direction, Position> travelableFeatures = findClosestFeaturesGroupedByDirection(feature, Map.features, 8, 2, 10);
                List<Action> connections = new ArrayList<>(travelableFeatures.size());
                for (Direction dir : travelableFeatures.keySet()) {
                    connections.add(getTravelAction(gameState, dir, travelableFeatures.get(dir)));
                }
                travelConnections.put(name, connections);
            }
        }).build().register();
    }

    private static Action getTravelAction(GameState gameState, Direction dir, Position position) {
        Integer locationId = gameState.getEntities().getLocation(position.getX(), position.getY());
        if (locationId == null) {
            System.out.println(position);
            return null;
        }
        String locationName = NamedComponent.get(gameState.getWorld(), locationId, "Unknown Location");
        return Action.builder().name(dir + " to " + locationName)
            .onSelect((gs, e) -> {
                PositionComponent positionComponent = PositionComponent.get(gs.getWorld(), e);
                positionComponent.setPos(position.getX(), position.getY());
                return "You have arrived at " + locationName + " on foot";
            }).build().register();
    }

    private static List<Action> getTravelActions(GameState gameState, Integer featureId) {
        String featureName = NamedComponent.get(gameState.getWorld(), featureId, "---");
        return travelConnections.get(featureName);
    }

    private static EnumMap<Direction, Position> findClosestFeaturesGroupedByDirection(Position start, List<Position> features, int maxFeatures, int minFeatures, int maxDistAfterMinFeatures) {
        // Step 1: Sort all features by distance from the start
        List<Position> sorted = features.stream()
            .filter(p -> !p.equals(start)) // skip yourself
            .sorted(Comparator.comparingDouble(p -> start.distanceTo(p)))
            .toList();

        // Step 2: Assign closest features to directions
        EnumMap<Direction, Position> result = new EnumMap<Direction, Position>(Direction.class);
        int featuresAdded = 0;

        for (Position position : sorted) {
            if (featuresAdded >= maxFeatures) break;

            int dist = start.distanceTo(position);
            // After we have a minimum number of connections it shouldnt find anything further than maxDistAfterMinFeatures
            // this is if a feature is really far out it will still find minFeatures connections but no more.
            if (featuresAdded >= minFeatures && dist > maxDistAfterMinFeatures) {
                break;
            }
            Direction dir = approximateDirection(start, position);

            if (!result.containsKey(dir)) {
                result.put(dir, position);
                featuresAdded++;
            }
        }

        return result;
    }

    private static Direction approximateDirection(Position from, Position to) {
        int dx = to.getX() - from.getX();
        int dy = to.getY() - from.getY();

        double angle = Math.atan2(dy, dx); // Note: Y axis is flipped (top-down map)
        double degrees = Math.toDegrees(angle);
        degrees = (degrees + 360) % 360; // Normalize to [0, 360)

        if (degrees >= 337.5 || degrees < 22.5) return Direction.EAST;
        if (degrees >= 22.5 && degrees < 67.5) return Direction.NORTHEAST;
        if (degrees >= 67.5 && degrees < 112.5) return Direction.NORTH;
        if (degrees >= 112.5 && degrees < 157.5) return Direction.NORTHWEST;
        if (degrees >= 157.5 && degrees < 202.5) return Direction.WEST;
        if (degrees >= 202.5 && degrees < 247.5) return Direction.SOUTHWEST;
        if (degrees >= 247.5 && degrees < 292.5) return Direction.SOUTH;
        if (degrees >= 292.5 && degrees < 337.5) return Direction.SOUTHEAST;

        return Direction.NORTH; // fallback
    }
}
