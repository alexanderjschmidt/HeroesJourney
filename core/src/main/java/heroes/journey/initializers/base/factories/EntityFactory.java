package heroes.journey.initializers.base.factories;

import static heroes.journey.initializers.base.factories.MonsterFactory.goblinId;
import static heroes.journey.initializers.base.factories.MonsterFactory.hobgoblinId;
import static heroes.journey.registries.Registries.ItemManager;
import static heroes.journey.registries.Registries.ItemSubTypeManager;
import static heroes.journey.registries.Registries.QuestManager;

import java.util.UUID;

import com.artemis.EntityEdit;

import heroes.journey.GameState;
import heroes.journey.components.BuffsComponent;
import heroes.journey.components.EquipmentComponent;
import heroes.journey.components.InventoryComponent;
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
import heroes.journey.components.place.DungeonComponent;
import heroes.journey.components.place.LocationComponent;
import heroes.journey.entities.ai.AI;
import heroes.journey.initializers.base.actions.BaseActions;
import heroes.journey.initializers.base.actions.CarriageActions;
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
        entity.create(PossibleActionsComponent.class);
        entity.create(BuffsComponent.class);
        entity.create(MapComponent.class);
        entity.create(AIComponent.class).ai(ai);
        entity.create(StatsComponent.class);
        entity.create(InventoryComponent.class);
        entity.create(EquipmentComponent.class);
        entity.create(QuestsComponent.class);
        return id;
    }

    public static UUID generateTown(GameState gameState, int x, int y, boolean capital) {
        EntityEdit house = gameState.getWorld().createEntity().edit();
        UUID id = house.create(IdComponent.class).uuid();
        house.create(LocationComponent.class).capital(capital);
        house.create(NamedComponent.class).name(SyllableTownNameGenerator.generateName());
        house.create(PositionComponent.class).setPos(x, y);
        house.create(QuestsComponent.class).addQuest(QuestManager.get("delve_dungeon"));
        PossibleActionsComponent actions = house.create(PossibleActionsComponent.class)
            .addAction(BaseActions.questBoard)
            .addAction(TravelActions.travel);
        if (capital)
            actions.addAction(CarriageActions.carriage);
        return id;
    }

    public static UUID generateDungeon(GameState gameState, int x, int y) {
        EntityEdit dungeon = gameState.getWorld().createEntity().edit();
        UUID id = dungeon.create(IdComponent.class).uuid();
        dungeon.create(LocationComponent.class);
        dungeon.create(NamedComponent.class).name(SyllableDungeonNameGenerator.generateName());
        dungeon.create(PositionComponent.class).setPos(x, y);
        dungeon.create(PossibleActionsComponent.class)
            .addAction(DelveAction.delve)
            .addAction(TravelActions.travel);
        System.out.println(ItemManager);
        System.out.println(ItemSubTypeManager);
        dungeon.create(InventoryComponent.class).add(ItemManager.get("iron_ore"), 5);
        dungeon.create(DungeonComponent.class).layout(new UUID[] {null, goblinId, goblinId, hobgoblinId});
        return id;
    }
}
