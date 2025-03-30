package heroes.journey;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import heroes.journey.components.*;
import heroes.journey.entities.EntityManager;
import heroes.journey.entities.actions.Action;
import heroes.journey.entities.actions.QueuedAction;
import heroes.journey.entities.actions.TargetAction;
import heroes.journey.initializers.Initializer;
import heroes.journey.initializers.base.Map;
import heroes.journey.systems.GameEngine;
import heroes.journey.tilemap.MapData;
import heroes.journey.tilemap.TileMap;
import heroes.journey.ui.HUD;
import heroes.journey.ui.hudstates.States;
import heroes.journey.utils.RangeManager;
import heroes.journey.utils.ai.pathfinding.Cell;

import java.util.*;
import java.util.stream.Collectors;

public class GameState implements Cloneable {

    private int width, height;
    private EntityManager entities;
    private TileMap map;
    private RangeManager rangeManager;

    private int turn;

    private static GameState gameState;

    private UUID currentEntity;
    private List<Entity> entitiesInActionOrder;

    public static GameState global() {
        if (gameState == null)
            gameState = new GameState();
        return gameState;
    }

    private GameState() {
        entitiesInActionOrder = new ArrayList<>();
    }

    private GameState(int width, int height) {
        this();
        this.width = width;
        this.height = height;
    }

    public void init(MapData mapData) {
        Initializer.init();

        this.width = mapData.getMapSize();
        this.height = mapData.getMapSize();
        map = new TileMap(width);
        entities = new EntityManager(width, height);
        rangeManager = new RangeManager(this, width, height);

        new Map().init();

        turn = 0;
    }

    public GameState clone() {
        GameState clone = new GameState(width, height);
        clone.entities = entities.clone();
        clone.map = map.clone(clone.entities);
        clone.rangeManager = rangeManager.clone(clone);
        clone.turn = turn;
        clone.currentEntity = currentEntity;
        return clone;
    }

    public GameState applyAction(QueuedAction queuedAction) {
        Cell path = queuedAction.getPath();
        Action action = queuedAction.getAction();

        Entity e = getEntities().get(path.i, path.j);
        // Move to the end of the Path
        // TODO does this remove the entity from the previous location?
        if (e != null) {
            while (path.parent != null) {
                path = path.parent;
            }
            getEntities().addEntity(e);
        }

        // Apply chosen Action
        if (action instanceof TargetAction targetAction) {
            targetAction.targetEffect(this, e, queuedAction.getTargetX(), queuedAction.getTargetY());
        } else {
            action.onSelect(this, e);
        }
        incrementTurn();
        return this;
    }

    public void render(SpriteBatch batch, float delta) {
        map.render(batch, delta);
        rangeManager.render(batch, turn);
    }

    public Entity setCurrentEntity(Entity currentEntity) {
        this.currentEntity = GameEngine.getID(currentEntity);
        return currentEntity;
    }

    public Entity get(UUID id) {
        return entities.getEntity(id);
    }

    public Entity getCurrentEntity() {
        return get(currentEntity);
    }

    private List<Entity> getEntitiesInActionOrder() {
        ImmutableArray<Entity> entitiesImmutableArray = GameEngine.get()
            .getEntitiesFor(
                Family.all(GlobalGameStateComponent.class, StatsComponent.class, AIComponent.class).get());
        Entity[] array = entitiesImmutableArray.toArray(Entity.class);
        return Arrays.stream(array)
            .filter(Objects::nonNull)
            .sorted(Comparator.comparing((Entity e) -> StatsComponent.get(e).getSpeed())
                .thenComparing(Object::toString))
            .collect(Collectors.toList());
    }

    private Entity incrementTurn() {
        if (entitiesInActionOrder == null || entitiesInActionOrder.isEmpty()) {
            entitiesInActionOrder = getEntitiesInActionOrder();
            turn++;
            if (GameState.global() == this) {
                GameEngine.get().enableEndOfTurnSystems();
            }
        }
        return setCurrentEntity(entitiesInActionOrder.removeFirst());
    }

    public void nextTurn() {
        Entity currentEntity = incrementTurn();
        System.out.println("turn " + turn + ", " + currentEntity + " " + entitiesInActionOrder);

        HUD.get().revertToInitialState();
        PlayerComponent player = PlayerComponent.get(currentEntity);
        if (player != null) {
            if (player.getPlayerId().equals(getId())) {
                HUD.get().getCursor().setPosition(currentEntity);
                HUD.get().setState(States.CURSOR_MOVE);
                System.out.println("your turn");
            } else {
                System.out.println("opponent turn");
            }
        } else {
            System.out.println("ai turn");
            AIComponent ai = AIComponent.get(currentEntity);
            QueuedAction action = ai.getAI().getMove(this, currentEntity);

            currentEntity.add(new MovementComponent(action.getPath()));
            currentEntity.add(new ActionComponent(action.getAction(), action.getTargetX(), action.getTargetY()));
        }
        getRangeManager().clearRange();
        HUD.get().getCursor().clearSelected();
    }

    public int getTurn() {
        return turn;
    }

    public EntityManager getEntities() {
        return entities;
    }

    public TileMap getMap() {
        return map;
    }

    public RangeManager getRangeManager() {
        return rangeManager;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void dispose() {
        entities.dispose();
    }

    public String getId() {
        return "id";
    }
}
