package heroes.journey;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import heroes.journey.components.*;
import heroes.journey.components.quests.QuestsComponent;
import heroes.journey.entities.EntityManager;
import heroes.journey.entities.actions.Action;
import heroes.journey.entities.actions.QueuedAction;
import heroes.journey.entities.actions.TargetAction;
import heroes.journey.entities.actions.history.History;
import heroes.journey.entities.quests.Quest;
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
    private History history;
    private TileMap map;

    private GameEngine engine;

    private RangeManager rangeManager;

    private int turn;

    private UUID currentEntity;
    private List<Entity> entitiesInActionOrder;

    private static GameState gameState;

    public static GameState global() {
        if (gameState == null) {
            gameState = new GameState();
            gameState.engine.initSystems(gameState);
        }
        return gameState;
    }

    private GameState() {
        entitiesInActionOrder = new ArrayList<>();
        engine = new GameEngine();
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
        history = new History();
        rangeManager = new RangeManager(this, width, height);

        new Map().init();

        turn = 0;
    }

    public GameState clone() {
        GameState clone = new GameState(width, height);
        clone.entities = entities.clone();
        for (Entity e : clone.entities.getEntites().values()) {
            clone.engine.addEntity(e);
        }
        clone.history = history.clone();
        clone.map = map.clone(clone.entities);
        clone.rangeManager = rangeManager.clone(clone);
        clone.turn = turn;
        clone.currentEntity = currentEntity;
        return clone;
    }

    public GameState applyAction(QueuedAction queuedAction) {
        Cell path = queuedAction.getPath();

        Entity e = entities.get(path.x, path.y);
        //System.out.println(queuedAction + " " + path + " " + e);
        // Move to the end of the Path
        if (e != null) {
            Cell end = path.getEnd();
            entities.moveEntity(e, end.x, end.y);
            history.add(queuedAction.getPath(), GameStateComponent.get(e).getId());
        }

        // Apply chosen Action
        Action action = queuedAction.getAction();
        if (action instanceof TargetAction targetAction) {
            targetAction.targetEffect(this, e, queuedAction.getTargetX(), queuedAction.getTargetY());
        } else {
            action.onSelect(this, e);
            history.add(queuedAction.getAction(), new PositionComponent(queuedAction.getTargetX(), queuedAction.getTargetY()), GameStateComponent.get(e).getId());
        }
        incrementTurn();
        return this;
    }

    public void update(float delta) {
        engine.update(delta);
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
        ImmutableArray<Entity> entitiesImmutableArray = engine
            .getEntitiesFor(
                Family.all(StatsComponent.class, AIComponent.class).get());
        Entity[] array = entitiesImmutableArray.toArray(Entity.class);
        return Arrays.stream(array)
            .filter(Objects::nonNull)
            .sorted(Comparator.comparing((Entity e) -> StatsComponent.get(e).getSpeed())
                .thenComparing(Object::toString))
            .collect(Collectors.toList());
    }

    private void processQuests() {
        Family family = Family.all(QuestsComponent.class).get();
        ImmutableArray<Entity> entities = engine.getEntitiesFor(family);
        for (Entity entity : entities) {
            QuestsComponent quests = QuestsComponent.get(entity);
            List<Quest> completedQuests = new ArrayList<>();
            for (Quest quest : quests) {
                if (quest.isComplete(this, entity)) {
                    quest.onComplete(this, entity);
                    completedQuests.add(quest);
                }
            }
            quests.removeAll(completedQuests);
        }
    }

    private Entity incrementTurn() {
        if (entitiesInActionOrder == null || entitiesInActionOrder.isEmpty()) {
            entitiesInActionOrder = getEntitiesInActionOrder();
            turn++;
            if (GameState.global() == this) {
                engine.enableEndOfTurnSystems();
            }
        }
        processQuests();
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

    public GameEngine getEngine() {
        return engine;
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
        engine.removeAllEntities();
    }

    public String getId() {
        return "id";
    }

    public History getHistory() {
        return history;
    }
}
