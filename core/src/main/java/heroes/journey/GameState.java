package heroes.journey;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import heroes.journey.components.PositionComponent;
import heroes.journey.components.StatsComponent;
import heroes.journey.components.character.AIComponent;
import heroes.journey.components.character.IdComponent;
import heroes.journey.components.character.MovementComponent;
import heroes.journey.components.character.PlayerComponent;
import heroes.journey.entities.EntityManager;
import heroes.journey.entities.Position;
import heroes.journey.entities.actions.Action;
import heroes.journey.entities.actions.QueuedAction;
import heroes.journey.entities.actions.history.History;
import heroes.journey.initializers.Initializer;
import heroes.journey.models.MapData;
import heroes.journey.systems.GameWorld;
import heroes.journey.systems.TriggerableSystem;
import heroes.journey.tilemap.TileMap;
import heroes.journey.ui.HUD;
import heroes.journey.ui.HUDEffectManager;
import heroes.journey.ui.WorldEffectManager;
import heroes.journey.utils.RangeManager;
import heroes.journey.utils.ai.pathfinding.Cell;
import lombok.Getter;

@Getter
public class GameState implements Cloneable {

    private int width, height;
    private EntityManager entities;
    private History history;
    private TileMap map;

    private GameWorld world;

    private RangeManager rangeManager;

    private int turn;

    @Getter private UUID currentEntity;
    //private Integer currentEntity;
    private List<UUID> entitiesInActionOrder;

    private static GameState gameState;

    public static GameState global() {
        if (gameState == null) {
            gameState = new GameState();
        }
        return gameState;
    }

    private GameState() {
        entitiesInActionOrder = new ArrayList<>();
    }

    public void init(MapData mapData) {
        Initializer.init();
        world = GameWorld.initGameWorld(this);

        this.width = mapData.getMapSize();
        this.height = mapData.getMapSize();
        map = new TileMap(width);
        entities = new EntityManager(width, height);
        history = new History();
        rangeManager = new RangeManager(this, width, height);

        HUDEffectManager.get();
        WorldEffectManager.get();

        turn = 0;
    }

    public GameState clone() {
        // TODO Make this better to fix ai
        long start = System.nanoTime();
        GameState clone = new GameState();
        clone.width = width;
        clone.height = height;
        clone.entities = entities.clone();
        //Utils.logTime("clone up to world", start, 200);
        clone.world = world.cloneWorld(clone);
        //Utils.logTime("clone after world", start, 200);
        clone.history = history.clone();
        // Since map layout never changes
        clone.map = map;
        //Utils.logTime("clone after map", start, 200);
        clone.turn = turn;
        clone.currentEntity = currentEntity;
        return clone;
    }

    // For AI to apply a flat action to movement just to update state
    public GameState applyAction(QueuedAction queuedAction) {
        long start = System.nanoTime();
        Cell path = queuedAction.getPath();

        UUID e = entities.get(path.x, path.y);
        //System.out.println(queuedAction + " " + path + " " + e);
        if (e != null) {
            // Move to the end of the Path
            Cell end = path.getEnd();
            entities.moveEntity(path.x, path.y, end.x, end.y);
            PositionComponent positionComponent = PositionComponent.get(world, e);
            positionComponent.setPos(end.x, end.y);
            positionComponent.sync();
            history.add(queuedAction.getPath(), e);

            // Apply chosen Action
            Action action = queuedAction.getAction();
            action.onSelect(this, e);
            history.add(queuedAction.getAction(),
                new Position(queuedAction.getTargetX(), queuedAction.getTargetY()), e);
            // If action adds movement
            MovementComponent movement = MovementComponent.get(world, e);
            if (movement != null) {
                end = movement.path().getEnd();
                entities.moveEntity(path.x, path.y, end.x, end.y);
                positionComponent.setPos(end.x, end.y);
                positionComponent.sync();
                history.add(movement.path(), e);
                world.getEntity(e).edit().remove(MovementComponent.class);
            }
        }

        incrementTurn();
        world.basicProcess();
        // System.out.println("apply took " + (System.nanoTime() - start) / 1_000_000.0 + " ms");
        return this;
    }

    public void update(float delta) {
        world.setDelta(delta);
        world.process();
    }

    public void render(SpriteBatch batch, float delta) {
        map.render(batch, delta);
        rangeManager.render(batch, turn);
    }

    private UUID incrementTurn() {
        if (entitiesInActionOrder == null || entitiesInActionOrder.isEmpty()) {
            entitiesInActionOrder = world.getEntitiesWith(StatsComponent.class, AIComponent.class,
                IdComponent.class);
            turn++;
            world.enableTriggerableSystems(TriggerableSystem.EventTrigger.TURN);
        }
        return entitiesInActionOrder.removeFirst();
    }

    private void updateCurrentEntity() {
        PlayerComponent player = PlayerComponent.get(world, currentEntity);
        if (player != null) {
            if (player.playerId().equals(PlayerInfo.get().getUuid())) {
                HUD.get().getCursor().setPosition(currentEntity);
                System.out.println("your turn");
            } else {
                System.out.println("opponent turn");
            }
        } else {
            System.out.println("ai turn");
            AIComponent ai = AIComponent.get(world, currentEntity);
            ai.startProcessingNextMove(this, currentEntity);
        }
    }

    // UI sets up the players next turn
    public void nextMove() {
        currentEntity = incrementTurn();
        System.out.println("turn " + turn + ", " + currentEntity + " " + entitiesInActionOrder);

        world.enableTriggerableSystems(TriggerableSystem.EventTrigger.MOVE);
        updateCurrentEntity();

        getRangeManager().clearRange();
        HUD.get().getCursor().clearSelected();
    }

    public UUID getNextPlayer(UUID player) {
        if (entitiesInActionOrder == null || entitiesInActionOrder.isEmpty()) {
            return world.getEntitiesWith(StatsComponent.class, AIComponent.class, IdComponent.class)
                .getFirst();
        }
        return entitiesInActionOrder.getFirst();
    }
}
