package heroes.journey;

import static heroes.journey.registries.Registries.ActionManager;
import static heroes.journey.utils.serializers.Serializers.jsonGameState;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Json;

import heroes.journey.components.PositionComponent;
import heroes.journey.components.StatsComponent;
import heroes.journey.components.character.AITurnComponent;
import heroes.journey.components.character.IdComponent;
import heroes.journey.components.character.MovementComponent;
import heroes.journey.components.character.PlayerComponent;
import heroes.journey.entities.EntityManager;
import heroes.journey.entities.Position;
import heroes.journey.entities.actions.ActionContext;
import heroes.journey.entities.actions.QueuedAction;
import heroes.journey.entities.actions.history.ActionRecord;
import heroes.journey.entities.actions.history.History;
import heroes.journey.entities.tagging.Attributes;
import heroes.journey.entities.tagging.Stat;
import heroes.journey.models.MapData;
import heroes.journey.modlib.IGameState;
import heroes.journey.modlib.Ids;
import heroes.journey.modlib.actions.ActionEntry;
import heroes.journey.systems.GameWorld;
import heroes.journey.systems.TriggerableSystem;
import heroes.journey.tilemap.TileMap;
import heroes.journey.tilemap.TileMapSaveData;
import heroes.journey.ui.HUD;
import heroes.journey.ui.HUDEffectManager;
import heroes.journey.ui.WorldEffectManager;
import heroes.journey.utils.ai.pathfinding.Cell;
import heroes.journey.utils.serializers.ActionRecordSerializer;
import heroes.journey.utils.serializers.GameStateSaveDataSerializer;
import heroes.journey.utils.serializers.PlayerInfoSerializer;
import heroes.journey.utils.serializers.PositionSerializer;
import heroes.journey.utils.serializers.TileMapSaveDataSerializer;
import heroes.journey.utils.serializers.UUIDSerializer;

public class GameState implements Cloneable, IGameState {

    private static GameState gameState;
    private int width, height;
    private EntityManager entities;
    private GameWorld world;
    private TileMap map;
    private History history;
    private int turn;
    private UUID currentEntity;
    //private Integer currentEntity;
    private List<UUID> entitiesInActionOrder;
    private Attributes realmsAttention, realmsAttentionBase;
    private MapData mapData;

    private GameState() {
        entitiesInActionOrder = new ArrayList<>();
    }

    public static GameState global() {
        if (gameState == null) {
            gameState = new GameState();
        }
        return gameState;
    }

    public void init(MapData mapData) {
        entitiesInActionOrder.clear();
        if (world != null)
            world.getEntityManager().reset();
        world = GameWorld.initGameWorld(this);

        this.mapData = mapData;
        this.width = mapData.getMapSize();
        this.height = mapData.getMapSize();
        map = new TileMap(width);
        entities = new EntityManager(width, height);
        history = new History();
        realmsAttention = new Attributes();
        realmsAttentionBase = new Attributes();
        for (Stat renown : Stat.getByGroup(Ids.GROUP_RENOWN)) {
            realmsAttentionBase.put(renown, mapData.getRealmAttentionBase());
        }

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
        clone.mapData = mapData;
        clone.realmsAttention = new Attributes(realmsAttention);
        clone.realmsAttentionBase = new Attributes(realmsAttentionBase);
        return clone;
    }

    // For AI to apply a flat action to movement just to update state
    public GameState applyAction(QueuedAction queuedAction) {
        long start = System.nanoTime();

        UUID e = queuedAction.getEntityId();
        //System.out.println(queuedAction + " " + path + " " + e);
        // Apply chosen Action
        ActionEntry actionEntry = queuedAction.getAction();
        history.add(actionEntry, e);
        ActionManager.get(actionEntry.getActionId())
            .onSelect(new ActionContext(this, e, true, actionEntry.getInput()));
        // If action adds movement
        // TODO remove this, onSelect should handle this
        MovementComponent movement = MovementComponent.get(world, e);
        if (movement != null) {
            PositionComponent positionComponent = PositionComponent.get(world, e);
            Cell end = entities.moveEntity(e, movement.path());
            positionComponent.setPos(end.x, end.y);
            positionComponent.sync();
            world.getEntity(e).edit().remove(MovementComponent.class);
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
    }

    private UUID incrementTurn() {
        if (entitiesInActionOrder == null || entitiesInActionOrder.isEmpty()) {
            entitiesInActionOrder = world.getEntitiesWith(StatsComponent.class, AITurnComponent.class,
                IdComponent.class);
            turn++;
            world.enableTriggerableSystems(TriggerableSystem.EventTrigger.TURN);
            realmsAttention.clear();
            realmsAttention.putAll(realmsAttentionBase);
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
            AITurnComponent ai = AITurnComponent.get(world, currentEntity);
            ai.startProcessingNextMove(this, currentEntity);
        }
    }

    // UI sets up the players next turn
    public void nextMove() {
        currentEntity = incrementTurn();
        System.out.print("turn " + turn + ", " + currentEntity + " " + entitiesInActionOrder + " ");

        world.enableTriggerableSystems(TriggerableSystem.EventTrigger.MOVE);
        // TODO make this happen after the triggerable systems execute so that the cursor is on the player
        updateCurrentEntity();

        HUD.get().getCursor().clearSelected();
    }

    public UUID getNextPlayer(UUID player) {
        if (entitiesInActionOrder == null || entitiesInActionOrder.isEmpty()) {
            return world.getEntitiesWith(StatsComponent.class, AITurnComponent.class, IdComponent.class)
                .getFirst();
        }
        return entitiesInActionOrder.getFirst();
    }

    public boolean isGlobal() {
        return this == GameState.global();
    }

    public void save(String save, boolean useJson) {
        world.saveWorld(save, useJson);
        TileMapSaveData mapSaveData = map.getSaveData();

        GameStateSaveData gameStateSaveData = new GameStateSaveData(width, height, mapSaveData, history, turn,
            currentEntity, entitiesInActionOrder, PlayerInfo.get());

        Json json = jsonGameState();

        String prettyJson = json.toJson(gameStateSaveData);
        try (FileWriter writer = new FileWriter("saves/" + save + "/gamestate.json")) {
            writer.write(prettyJson);
        } catch (IOException e) {
            e.printStackTrace(); // or your logging system
        }
    }

    public static void loadFromFile(String save) {
        gameState = new GameState();

        Json json = new Json();
        json.setSerializer(GameStateSaveData.class, new GameStateSaveDataSerializer());
        json.setSerializer(PlayerInfo.class, new PlayerInfoSerializer());
        json.setSerializer(TileMapSaveData.class, new TileMapSaveDataSerializer());
        json.setSerializer(ActionRecord.class, new ActionRecordSerializer());
        json.setSerializer(UUID.class, new UUIDSerializer());
        json.setSerializer(Position.class, new PositionSerializer());

        try (FileReader reader = new FileReader("saves/" + save + "/gamestate.json")) {
            GameStateSaveData gameStateSaveData = json.fromJson(GameStateSaveData.class, reader);
            // TODO Serialize width and height
            gameState.width = gameStateSaveData.getWidth();
            gameState.height = gameStateSaveData.getHeight();
            gameState.map = new TileMap(gameState.width);
            gameState.entities = new EntityManager(gameState.width, gameState.height);
            gameState.map.load(gameStateSaveData.getMap());
            gameState.history = gameStateSaveData.getHistory();
            gameState.turn = gameStateSaveData.getTurn();
            gameState.currentEntity = gameStateSaveData.getCurrentEntity();
            gameState.entitiesInActionOrder = gameStateSaveData.getEntitiesInActionOrder();

            PlayerInfo.load(gameStateSaveData.getPlayerInfo());
        } catch (IOException e) {
            e.printStackTrace();
        }
        gameState.world = GameWorld.initGameWorld(gameState);
        gameState.world.loadWorld(save, true);

        HUD.get().getCursor().setPosition(gameState.currentEntity);

        HUDEffectManager.get();
        WorldEffectManager.get();
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public EntityManager getEntities() {
        return this.entities;
    }

    public GameWorld getWorld() {
        return this.world;
    }

    public TileMap getMap() {
        return this.map;
    }

    public History getHistory() {
        return this.history;
    }

    public int getTurn() {
        return this.turn;
    }

    public List<UUID> getEntitiesInActionOrder() {
        return this.entitiesInActionOrder;
    }

    public UUID getCurrentEntity() {
        return this.currentEntity;
    }

    public Attributes getRealmsAttention() {
        return this.realmsAttention;
    }

    public MapData getMapData() {
        return this.mapData;
    }
}
