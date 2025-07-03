package heroes.journey.systems;

import static heroes.journey.initializers.base.LoadTextures.CAPITAL_SPRITE;
import static heroes.journey.initializers.base.LoadTextures.DUNGEON_SPRITE;
import static heroes.journey.registries.Registries.ItemManager;
import static heroes.journey.registries.Registries.QuestManager;

import java.util.UUID;

import com.artemis.EntityEdit;

import heroes.journey.components.BuffsComponent;
import heroes.journey.components.EquipmentComponent;
import heroes.journey.components.InventoryComponent;
import heroes.journey.components.LocationComponent;
import heroes.journey.components.NamedComponent;
import heroes.journey.components.PositionComponent;
import heroes.journey.components.PossibleActionsComponent;
import heroes.journey.components.QuestsComponent;
import heroes.journey.components.RegionComponent;
import heroes.journey.components.StatsComponent;
import heroes.journey.components.character.AITurnComponent;
import heroes.journey.components.character.AIWanderComponent;
import heroes.journey.components.character.ActorComponent;
import heroes.journey.components.character.IdComponent;
import heroes.journey.components.character.MapComponent;
import heroes.journey.components.character.RenderComponent;
import heroes.journey.components.utils.WanderType;
import heroes.journey.initializers.base.Tiles;
import heroes.journey.initializers.base.actions.BaseActions;
import heroes.journey.initializers.base.actions.DelveAction;
import heroes.journey.initializers.base.actions.TravelActions;
import heroes.journey.utils.worldgen.namegen.MarkovTownNameGenerator;
import heroes.journey.utils.worldgen.namegen.SyllableDungeonNameGenerator;
import heroes.journey.utils.worldgen.namegen.SyllableTownNameGenerator;

public class EntityFactory {

    private final GameWorld world;

    public EntityFactory(GameWorld world) {
        this.world = world;
    }

    public UUID createEntity() {
        EntityEdit entity = world.createEntity().edit();
        return entity.create(IdComponent.class).register(world, entity.getEntityId()).uuid();
    }

    public void addRenderComponents(UUID entityId, String name, int x, int y, String render) {
        EntityEdit entity = world.getEntity(entityId).edit();
        entity.create(NamedComponent.class).name(name);
        entity.create(PositionComponent.class).setPos(x, y);
        entity.create(RenderComponent.class).sprite(render);
    }

    public void addMovableComponents(UUID entityId) {
        addMovableComponents(entityId, null);
    }

    public void addMovableComponents(UUID entityId, WanderType wanderType) {
        EntityEdit entity = world.getEntity(entityId).edit();
        entity.create(ActorComponent.class);
        if (wanderType != null)
            entity.create(AIWanderComponent.class).setWanderType(wanderType);
    }

    public void addPlayerComponents(UUID entityId) {
        EntityEdit entity = world.getEntity(entityId).edit();
        entity.create(PossibleActionsComponent.class)
            .addAction(BaseActions.rest)
            .addAction(TravelActions.travel)
            .addAction(BaseActions.faceChallenges);
        entity.create(BuffsComponent.class);
        entity.create(MapComponent.class);
        entity.create(AITurnComponent.class);
        entity.create(StatsComponent.class);
        entity.create(InventoryComponent.class);
        entity.create(EquipmentComponent.class);
        entity.create(QuestsComponent.class);
    }

    public UUID createRegion(int ringIndex, int ringPos) {
        UUID regionId = this.createEntity();
        EntityEdit region = world.getEntity(regionId).edit();
        region.create(RegionComponent.class)
            .ring(ringIndex)
            .ringPos(ringPos)
            .addChallenge("fight_monsters")
            .addChallenge("solve_mystery")
            .addChallenge("cure_curse")
            .addChallenge("resolve_dispute");
        region.create(NamedComponent.class).name(MarkovTownNameGenerator.get().generateTownName());
        return regionId;
    }

    public void addLocationsComponent(UUID entityId, int x, int y) {
        UUID region = world.getGameState().getMap().getRegionMap()[x][y];
        EntityEdit entity = world.getEntity(entityId).edit();
        entity.create(LocationComponent.class).region(region);

        world.getGameState().getMap().setEnvironment(x, y, Tiles.NULL);
    }

    public UUID generateBasicLocation(String name, int x, int y, String render) {
        UUID id = createEntity();
        addRenderComponents(id, name, x, y, render);
        addLocationsComponent(id, x, y);
        return id;
    }

    public UUID generateCapital(int x, int y) {
        UUID houseId = generateBasicLocation(SyllableTownNameGenerator.generateName(), x, y, CAPITAL_SPRITE);

        EntityEdit house = world.getEntity(houseId).edit();
        house.create(QuestsComponent.class).addQuest(QuestManager.get("delve_dungeon"));
        house.create(PossibleActionsComponent.class).addAction(BaseActions.questBoard);

        return houseId;
    }

    public UUID generateDungeon(int x, int y) {
        UUID dungeonId = generateBasicLocation(SyllableDungeonNameGenerator.generateName(), x, y,
            DUNGEON_SPRITE);

        EntityEdit dungeon = world.getEntity(dungeonId).edit();
        dungeon.create(PossibleActionsComponent.class).addAction(DelveAction.delve);
        dungeon.create(InventoryComponent.class).add(ItemManager.get("iron_ore"), 5);
        dungeon.create(StatsComponent.class);
        return dungeonId;
    }

}
