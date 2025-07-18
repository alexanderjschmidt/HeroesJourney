package heroes.journey.systems.constantsystems;

import static heroes.journey.utils.worldgen.utils.MapGenUtils.isLandTile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.artemis.annotations.All;
import com.artemis.systems.IteratingSystem;

import heroes.journey.PlayerInfo;
import heroes.journey.components.PositionComponent;
import heroes.journey.components.RegionComponent;
import heroes.journey.components.character.AIWanderComponent;
import heroes.journey.components.character.IdComponent;
import heroes.journey.components.character.MovementComponent;
import heroes.journey.components.utils.WanderType;
import heroes.journey.modlib.utils.Position;
import heroes.journey.systems.GameWorld;
import heroes.journey.utils.Direction;
import heroes.journey.utils.Random;
import heroes.journey.utils.ai.pathfinding.Cell;
import heroes.journey.utils.gamestate.Utils;

@All({AIWanderComponent.class, PositionComponent.class, IdComponent.class})
public class AIWanderSystem extends IteratingSystem {

    @Override
    protected void process(int entityId) {
        GameWorld world = (GameWorld)getWorld();
        UUID id = IdComponent.get(world, entityId);

        if (PlayerInfo.isPlayer(id) && PlayerInfo.isCurrentlyPlaying())
            return;

        AIWanderComponent aiWander = AIWanderComponent.get(world, id);

        if (aiWander.incrementTime(world.getDelta())) {
            return; // not time to move yet
        }
        aiWander.resetTime();

        PositionComponent positionComponent = PositionComponent.get(world, id);
        List<Direction> validDirections = getValidDirections(aiWander, positionComponent, id, world);

        if (!validDirections.isEmpty()) {
            Direction dir = validDirections.get(Random.get().nextInt(validDirections.size()));
            world.edit(entityId)
                .create(MovementComponent.class)
                .path(new Cell((int)(positionComponent.getX() + dir.getDirVector().x),
                    (int)(positionComponent.getY() + dir.getDirVector().y)));
        }
    }

    private List<Direction> getValidDirections(
        AIWanderComponent aiWanderComponent,
        PositionComponent positionComponent,
        UUID id,
        GameWorld world) {
        List<Direction> validDirections = new ArrayList<>();
        for (Direction dir : Direction.getDirections()) {
            if (!dir.isCardinal())
                continue;
            int nx = (int)(positionComponent.getX() + dir.getDirVector().x);
            int ny = (int)(positionComponent.getY() + dir.getDirVector().y);

            if (!isLandTile(world.getGameState().getMap().getTileMap()[nx][ny]))
                continue;

            UUID region = Utils.getRegion(world.getGameState(), id);
            RegionComponent regionComponent = RegionComponent.get(world, region);
            boolean inRegion = false;
            for (Position tile : regionComponent.getTiles()) {
                if (tile.getX() == nx && tile.getY() == ny) {
                    inRegion = true;
                    break;
                }
            }
            if (!inRegion) {
                continue;
            }
            if (aiWanderComponent.getWanderType() == WanderType.Region) {
                validDirections.add(dir);
                continue;
            }
            // Has to be a local wander type at this point
            if (aiWanderComponent.getLocalPosition().distanceTo(new Position(nx, ny)) < 3) {
                validDirections.add(dir);
            }
        }
        return validDirections;
    }

}
