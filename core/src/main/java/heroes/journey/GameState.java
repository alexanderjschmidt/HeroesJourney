package heroes.journey;

import com.artemis.utils.IntBag;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import heroes.journey.components.QuestsComponent;
import heroes.journey.components.StatsComponent;
import heroes.journey.components.character.AIComponent;
import heroes.journey.components.character.PlayerComponent;
import heroes.journey.entities.EntityManager;
import heroes.journey.entities.Position;
import heroes.journey.entities.actions.Action;
import heroes.journey.entities.actions.QueuedAction;
import heroes.journey.entities.actions.history.History;
import heroes.journey.entities.quests.Quest;
import heroes.journey.initializers.Initializer;
import heroes.journey.systems.GameWorld;
import heroes.journey.tilemap.MapData;
import heroes.journey.tilemap.TileMap;
import heroes.journey.ui.HUD;
import heroes.journey.utils.RangeManager;
import heroes.journey.utils.ai.pathfinding.Cell;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Getter
public class GameState implements Cloneable {

    private int width, height;
    private EntityManager entities;
    private History history;
    private TileMap map;

    private GameWorld world;

    private RangeManager rangeManager;

    private int turn;

    @Getter
    private Integer currentEntity;
    private List<Integer> entitiesInActionOrder;

    private final List<Integer> playableEntities = new ArrayList<>();

    private static GameState gameState;

    public static GameState global() {
        if (gameState == null) {
            gameState = new GameState();
        }
        return gameState;
    }

    private GameState() {
        entitiesInActionOrder = new ArrayList<>();
        world = GameWorld.initGameWorld(this);
    }

    public void init(MapData mapData) {
        Initializer.init();

        this.width = mapData.getMapSize();
        this.height = mapData.getMapSize();
        map = new TileMap(width);
        entities = new EntityManager(width, height);
        history = new History();
        rangeManager = new RangeManager(this, width, height);

        turn = 0;
    }

    public GameState clone() {
        GameState clone = new GameState();
        clone.width = width;
        clone.height = height;
        clone.entities = entities.clone();
        clone.world = world.cloneWorld(clone);
        clone.history = history.clone();
        clone.map = map.clone(clone.entities);
        clone.rangeManager = rangeManager.clone(clone);
        clone.turn = turn;
        clone.currentEntity = currentEntity;
        return clone;
    }

    // For AI to apply a flat action to movement just to update state
    public GameState applyAction(QueuedAction queuedAction) {
        Cell path = queuedAction.getPath();

        Integer e = entities.get(path.x, path.y);
        //System.out.println(queuedAction + " " + path + " " + e);
        // Move to the end of the Path
        if (e != null) {
            Cell end = path.getEnd();
            entities.moveEntity(path.x, path.y, end.x, end.y);
            history.add(queuedAction.getPath(), e);
        }

        // Apply chosen Action
        Action action = queuedAction.getAction();
        action.onSelect(this, e);
        history.add(queuedAction.getAction(),
            new Position(queuedAction.getTargetX(), queuedAction.getTargetY()), e);
        incrementTurn();
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

    public Integer setCurrentEntity(Integer currentEntity) {
        this.currentEntity = currentEntity;
        return currentEntity;
    }

    private List<Integer> getEntitiesInActionOrder() {
        IntBag entities = world.getEntitiesWith(StatsComponent.class, AIComponent.class);
        int[] ids = entities.getData();
        int size = entities.size();  // important! IntBag may over-allocate.

        return IntStream.range(0, size).mapToObj(i -> ids[i])  // convert int index to Integer entityId
            .sorted(Comparator.comparing((Integer e) -> StatsComponent.get(world, e).getSpeed())
                .thenComparing(Object::toString)).collect(Collectors.toList());
    }

    private void processQuests() {
        IntBag entities = world.getEntitiesWith(QuestsComponent.class);
        for (Integer entityId : entities.getData()) {
            QuestsComponent quests = QuestsComponent.get(world, entityId);
            if (quests == null) {
                continue;
            }
            List<Quest> completedQuests = new ArrayList<>();
            for (Quest quest : quests.getQuests()) {
                if (quest.isComplete(this, entityId)) {
                    quest.onComplete(this, entityId);
                    completedQuests.add(quest);
                }
            }
            quests.getQuests().removeAll(completedQuests);
        }
    }

    private Integer incrementTurn() {
        if (entitiesInActionOrder == null || entitiesInActionOrder.isEmpty()) {
            entitiesInActionOrder = getEntitiesInActionOrder();
            turn++;
            if (GameState.global() == this) {
                world.enableEndOfTurnSystems();
            }
        }
        processQuests();
        return setCurrentEntity(entitiesInActionOrder.removeFirst());
    }

    // UI sets up the players next turn
    public void nextTurn() {
        Integer currentEntity = incrementTurn();
        System.out.println("turn " + turn + ", " + currentEntity + " " + entitiesInActionOrder);

        PlayerComponent player = PlayerComponent.get(world, currentEntity);
        if (player != null) {
            if (player.playerId().equals(getId())) {
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
        getRangeManager().clearRange();
        HUD.get().getCursor().clearSelected();
    }

    public String getId() {
        return "id";
    }
}
