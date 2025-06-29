package heroes.journey.initializers.base;

import static heroes.journey.registries.Registries.ItemManager;
import static heroes.journey.registries.Registries.QuestManager;

import java.util.UUID;

import com.artemis.EntityEdit;

import heroes.journey.GameState;
import heroes.journey.components.BuffsComponent;
import heroes.journey.components.EquipmentComponent;
import heroes.journey.components.InventoryComponent;
import heroes.journey.components.LocationComponent;
import heroes.journey.components.NamedComponent;
import heroes.journey.components.PositionComponent;
import heroes.journey.components.PossibleActionsComponent;
import heroes.journey.components.QuestsComponent;
import heroes.journey.components.StatsComponent;
import heroes.journey.components.character.AIComponent;
import heroes.journey.components.character.ActorComponent;
import heroes.journey.components.character.IdComponent;
import heroes.journey.components.character.MapComponent;
import heroes.journey.components.character.RenderComponent;
import heroes.journey.entities.ai.AI;
import heroes.journey.initializers.base.actions.BaseActions;
import heroes.journey.initializers.base.actions.DelveAction;
import heroes.journey.initializers.base.actions.TravelActions;
import heroes.journey.systems.GameWorld;
import heroes.journey.utils.worldgen.namegen.SyllableDungeonNameGenerator;
import heroes.journey.utils.worldgen.namegen.SyllableTownNameGenerator;

public class EntityFactory {

    public static UUID addOverworldComponents(
        GameWorld world,
        EntityEdit entity,
        int x,
        int y,
        String render,
        AI ai) {
        entity.create(PositionComponent.class).setPos(x, y);
        UUID id = entity.create(IdComponent.class).register(world, entity.getEntityId()).uuid();
        entity.create(RenderComponent.class).sprite(render);
        entity.create(ActorComponent.class);
        entity.create(PossibleActionsComponent.class)
            .addAction(BaseActions.rest)
            .addAction(TravelActions.travel);

        entity.create(BuffsComponent.class);
        entity.create(MapComponent.class);
        entity.create(AIComponent.class).ai(ai);
        entity.create(StatsComponent.class);
        entity.create(InventoryComponent.class);
        entity.create(EquipmentComponent.class);
        entity.create(QuestsComponent.class);
        return id;
    }

    public static UUID generateCapital(GameState gameState, int x, int y) {
        EntityEdit house = gameState.getWorld().createEntity().edit();
        UUID id = house.create(IdComponent.class).uuid();
        UUID region = gameState.getMap().getRegionMap()[x][y];
        house.create(LocationComponent.class).featureType("kingdom").region(region);
        house.create(NamedComponent.class).name(SyllableTownNameGenerator.generateName());
        house.create(PositionComponent.class).setPos(x, y);
        house.create(QuestsComponent.class).addQuest(QuestManager.get("delve_dungeon"));
        house.create(PossibleActionsComponent.class).addAction(BaseActions.questBoard);
        return id;
    }

    public static UUID generateTown(GameState gameState, int x, int y) {
        EntityEdit house = gameState.getWorld().createEntity().edit();
        UUID id = house.create(IdComponent.class).uuid();
        UUID region = gameState.getMap().getRegionMap()[x][y];
        house.create(LocationComponent.class).featureType("town").region(region);
        house.create(NamedComponent.class).name(SyllableTownNameGenerator.generateName());
        house.create(PositionComponent.class).setPos(x, y);
        return id;
    }

    public static UUID generateDungeon(GameState gameState, int x, int y) {
        EntityEdit dungeon = gameState.getWorld().createEntity().edit();
        UUID id = dungeon.create(IdComponent.class).uuid();
        UUID region = gameState.getMap().getRegionMap()[x][y];
        dungeon.create(LocationComponent.class).featureType("dungeon").region(region);
        dungeon.create(NamedComponent.class).name(SyllableDungeonNameGenerator.generateName());
        dungeon.create(PositionComponent.class).setPos(x, y);
        dungeon.create(PossibleActionsComponent.class).addAction(DelveAction.delve);
        dungeon.create(InventoryComponent.class).add(ItemManager.get("iron_ore"), 5);
        dungeon.create(StatsComponent.class);
        return id;
    }

    public static UUID generateMine(GameState gameState, int x, int y) {
        EntityEdit dungeon = gameState.getWorld().createEntity().edit();
        UUID id = dungeon.create(IdComponent.class).uuid();
        UUID region = gameState.getMap().getRegionMap()[x][y];
        dungeon.create(LocationComponent.class).featureType("mine").region(region);
        dungeon.create(NamedComponent.class).name("Mine");
        dungeon.create(PositionComponent.class).setPos(x, y);
        return id;
    }
}
