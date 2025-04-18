package heroes.journey.initializers.base.factories;

import static heroes.journey.initializers.base.factories.MonsterFactory.goblin;
import static heroes.journey.initializers.base.factories.MonsterFactory.hobGoblin;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import heroes.journey.GameState;
import heroes.journey.components.EquipmentComponent;
import heroes.journey.components.GameStateComponent;
import heroes.journey.components.InventoryComponent;
import heroes.journey.components.LoyaltyComponent;
import heroes.journey.components.StatsComponent;
import heroes.journey.components.overworld.character.AIComponent;
import heroes.journey.components.overworld.character.ActorComponent;
import heroes.journey.components.overworld.character.PositionComponent;
import heroes.journey.components.overworld.character.PossibleActionsComponent;
import heroes.journey.components.overworld.character.RenderComponent;
import heroes.journey.components.overworld.place.CarriageComponent;
import heroes.journey.components.overworld.place.DungeonComponent;
import heroes.journey.components.overworld.place.FactionComponent;
import heroes.journey.components.quests.QuestsComponent;
import heroes.journey.components.utils.Utils;
import heroes.journey.entities.Position;
import heroes.journey.entities.ai.AI;
import heroes.journey.entities.quests.Quest;
import heroes.journey.initializers.base.BaseActions;
import heroes.journey.initializers.base.Items;
import heroes.journey.utils.worldgen.namegen.SyllableDungeonNameGenerator;
import heroes.journey.utils.worldgen.namegen.SyllableTownNameGenerator;

public class EntityFactory {

    public static Entity overworldEntity(int x, int y, TextureRegion render, AI ai) {
        Entity hero = new Entity();
        hero.add(new GameStateComponent())
            .add(new PositionComponent(x, y))
            .add(new RenderComponent(render))
            .add(new ActorComponent())
            .add(new PossibleActionsComponent())
            .add(new AIComponent(ai))
            .add(StatsComponent.builder().build())
            .add(new InventoryComponent())
            .add(new EquipmentComponent())
            .add(new QuestsComponent())
            .add(new LoyaltyComponent());
        return hero;
    }

    public static Entity generateHouse(GameState gameState, int x, int y) {
        Quest quest = Quest.builder()
            .name("Delve a dungeon")
            .onComplete((gs, e) -> Utils.addItem(e, Items.ironSword, 1))
            .isComplete((gs, e) -> Utils.justCompletedAction(gs, e, BaseActions.delve))
            .build();

        Entity house = new Entity();
        house.add(
                new FactionComponent(SyllableTownNameGenerator.generateName()).addOwnedLocation(gameState, house,
                    new Position(x, y)))
            .add(new GameStateComponent())
            .add(new PositionComponent(x, y))
            .add(new CarriageComponent())
            .add(new QuestsComponent().addQuest(quest))
            .add(new PossibleActionsComponent().addAction(BaseActions.questBoard));
        return house;
    }

    public static Entity generateDungeon(GameState gameState, int x, int y) {
        Entity dungeon = new Entity();
        dungeon.add(
                new FactionComponent(SyllableDungeonNameGenerator.generateName()).addOwnedLocation(gameState,
                    dungeon, new Position(x, y)))
            .add(new GameStateComponent())
            .add(new PossibleActionsComponent().addAction(BaseActions.delve, dungeon))
            .add(new InventoryComponent().add(Items.ironOre, 5))
            .add(DungeonComponent.builder()
                .layout(new Entity[] {null, goblin(), goblin(), hobGoblin()}) //TODO add a trap
                .build());
        return dungeon;
    }
}
