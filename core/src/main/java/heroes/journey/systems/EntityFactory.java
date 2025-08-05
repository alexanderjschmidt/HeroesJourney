package heroes.journey.systems;

import com.artemis.EntityEdit;
import heroes.journey.components.*;
import heroes.journey.components.character.*;
import heroes.journey.components.utils.WanderType;
import heroes.journey.entities.Challenge;
import heroes.journey.entities.tagging.Attributes;
import heroes.journey.modlib.Ids;
import heroes.journey.modlib.attributes.IStat;
import heroes.journey.modlib.attributes.Relation;
import heroes.journey.modlib.utils.Position;
import heroes.journey.tilemap.TileManager;
import heroes.journey.utils.worldgen.namegen.MarkovTownNameGenerator;
import heroes.journey.utils.worldgen.namegen.SyllableDungeonNameGenerator;

import java.util.UUID;

import static heroes.journey.modlib.Ids.*;
import static heroes.journey.mods.Registries.StatManager;

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

    public void addMovableComponents(UUID entityId, WanderType wanderType) {
        EntityEdit entity = world.getEntity(entityId).edit();
        entity.create(ActorComponent.class);
        if (wanderType != null) {
            AIWanderComponent wander = entity.create(AIWanderComponent.class).setWanderType(wanderType);
            if (wanderType == WanderType.Local) {
                PositionComponent positionComponent = PositionComponent.get(world, entityId);
                wander.setLocalPosition(
                    new Position(positionComponent.getTargetX(), positionComponent.getTargetY()));
            }
        }

    }

    public UUID createChallenge(Challenge challenge, int x, int y) {
        UUID entityId = createEntity();
        addRenderComponents(entityId, challenge.getName(), x, y, challenge.getRender());
        addMovableComponents(entityId, WanderType.Local);

        EntityEdit entity = world.getEntity(entityId).edit();
        entity.create(ChallengeComponent.class).challenge(challenge);

        Attributes stats = entity.create(StatsComponent.class).getAttributes();
        stats.add(STAT_CHALLENGE_POWER_TIER, challenge.getPowerTier());
        IStat health = StatManager.get(STAT_CHALLENGE_HEALTH);
        IStat healthMax = health.getRelation(Relation.MAX);
        stats.add(Ids.STAT_CHALLENGE_HEALTH, stats.get(healthMax.getId()));
        return entityId;
    }

    public void addPlayerComponents(UUID entityId) {
        EntityEdit entity = world.getEntity(entityId).edit();
        entity.create(PossibleActionsComponent.class)
            .addAction(REST)
            .addAction(TRAVEL)
            .addAction(FACE_CHALLENGES)
            .addAction(Ids.COMPLETE_QUEST)
            .addAction(TRAINING)
            .addApproach(Ids.APPROACH_FIGHT)
            .addApproach(Ids.APPROACH_NEGOTIATE)
            .addApproach(Ids.APPROACH_TRICK)
            .addApproach(Ids.APPROACH_MAGIC_MISSILE);
        entity.create(BuffsComponent.class);
        entity.create(MapComponent.class);
        entity.create(AITurnComponent.class);
        StatsComponent stats = entity.create(StatsComponent.class);
        stats.getAttributes().put(STAT_BODY, true);
        stats.getAttributes().put(STAT_MIND, true);
        stats.getAttributes().put(STAT_MAGIC, true);
        stats.getAttributes().put(STAT_CHARISMA, true);
        stats.getAttributes().put(STAT_FAME, true);
        entity.create(QuestsComponent.class);
    }

    public UUID createRegion(int ringIndex, int ringPos) {
        UUID regionId = this.createEntity();
        EntityEdit region = world.getEntity(regionId).edit();
        region.create(RegionComponent.class).ring(ringIndex).ringPos(ringPos);
        region.create(NamedComponent.class).name(MarkovTownNameGenerator.get().generateTownName());
        region.create(QuestsComponent.class);
        region.create(PossibleActionsComponent.class).addAction(QUEST_BOARD);
        return regionId;
    }

    public void addLocationsComponent(UUID entityId, int x, int y) {
        UUID region = world.getGameState().getMap().getRegionMap()[x][y];
        EntityEdit entity = world.getEntity(entityId).edit();
        entity.create(LocationComponent.class).region(region);

        world.getGameState().getMap().setEnvironment(x, y, TileManager.BASE_TILES.get(BASE_TILE_NULL));
    }

    public UUID generateBasicLocation(String name, int x, int y, String render) {
        UUID id = createEntity();
        addRenderComponents(id, name, x, y, render);
        addLocationsComponent(id, x, y);
        return id;
    }

    public UUID generateDungeon(int x, int y) {
        UUID dungeonId = generateBasicLocation(SyllableDungeonNameGenerator.generateName(), x, y,
            DUNGEON_SPRITE);

        EntityEdit dungeon = world.getEntity(dungeonId).edit();
        dungeon.create(PossibleActionsComponent.class).addAction(DELVE);
        dungeon.create(StatsComponent.class);
        return dungeonId;
    }

}
