package heroes.journey.initializers.base.factories;

import com.artemis.EntityEdit;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import heroes.journey.GameState;
import heroes.journey.components.EquipmentComponent;
import heroes.journey.components.InventoryComponent;
import heroes.journey.components.QuestsComponent;
import heroes.journey.components.StatsComponent;
import heroes.journey.components.character.*;
import heroes.journey.components.place.CarriageComponent;
import heroes.journey.components.place.DungeonComponent;
import heroes.journey.components.place.LocationComponent;
import heroes.journey.entities.ai.AI;
import heroes.journey.initializers.base.BaseActions;
import heroes.journey.initializers.base.Items;
import heroes.journey.initializers.base.Quests;
import heroes.journey.initializers.base.TravelActions;
import heroes.journey.systems.GameWorld;
import heroes.journey.utils.worldgen.namegen.SyllableDungeonNameGenerator;
import heroes.journey.utils.worldgen.namegen.SyllableTownNameGenerator;

import static heroes.journey.initializers.base.factories.MonsterFactory.goblin;
import static heroes.journey.initializers.base.factories.MonsterFactory.hobGoblin;

public class EntityFactory {

    public static Integer overworldEntity(GameState gameState, int x, int y, TextureRegion render, AI ai) {
        EntityEdit entity = gameState.getWorld().createEntity().edit();
        entity.create(PositionComponent.class).setPos(x, y).sync();
        entity.create(RenderComponent.class).sprite(render);
        entity.create(ActorComponent.class);
        entity.create(PossibleActionsComponent.class);
        entity.create(AIComponent.class).ai(ai);
        entity.create(StatsComponent.class);
        entity.create(InventoryComponent.class);
        entity.create(EquipmentComponent.class);
        entity.create(QuestsComponent.class);
        return entity.getEntityId();
    }

    public static Integer generateTown(GameState gameState, int x, int y) {
        EntityEdit house = gameState.getWorld().createEntity().edit();
        house.create(LocationComponent.class);
        house.create(NamedComponent.class).name(SyllableTownNameGenerator.generateName());
        house.create(PositionComponent.class).setPos(x, y).sync();
        house.create(CarriageComponent.class);
        house.create(QuestsComponent.class).addQuest(Quests.delve);
        house.create(PossibleActionsComponent.class).addAction(BaseActions.questBoard).addAction(TravelActions.travel);
        return house.getEntityId();
    }

    public static Integer generateDungeon(GameState gameState, int x, int y) {
        GameWorld world = gameState.getWorld();
        EntityEdit dungeon = gameState.getWorld().createEntity().edit();
        dungeon.create(LocationComponent.class);
        dungeon.create(NamedComponent.class).name(SyllableDungeonNameGenerator.generateName());
        dungeon.create(PositionComponent.class).setPos(x, y).sync();
        dungeon.create(PossibleActionsComponent.class)
            .addAction(BaseActions.delve, gameState.getWorld(), dungeon.getEntityId()).addAction(TravelActions.travel);
        dungeon.create(InventoryComponent.class).add(Items.ironOre, 5);
        dungeon.create(DungeonComponent.class)
            .layout(new Integer[]{null, goblin(world), goblin(world), hobGoblin(world)});
        return dungeon.getEntityId();
    }
}
