package heroes.journey.entities.ai;

import java.util.UUID;

import com.badlogic.ashley.core.Entity;

import heroes.journey.GameState;
import heroes.journey.components.GameStateComponent;
import heroes.journey.components.InventoryComponent;
import heroes.journey.components.LoyaltyComponent;
import heroes.journey.components.StatsComponent;
import heroes.journey.components.overworld.character.AIComponent;
import heroes.journey.components.overworld.character.ActorComponent;
import heroes.journey.components.overworld.character.PositionComponent;
import heroes.journey.components.overworld.character.RenderComponent;
import heroes.journey.components.overworld.place.FactionComponent;
import heroes.journey.entities.Position;
import heroes.journey.entities.actions.QueuedAction;
import heroes.journey.initializers.base.LoadTextures;
import heroes.journey.initializers.base.Loyalties;
import heroes.journey.systems.GameEngine;
import heroes.journey.utils.art.ResourceManager;

public class MonsterFactionAI implements AI {

    @Override
    public QueuedAction getMove(GameState gameState, Entity playingFaction) {
        FactionComponent faction = FactionComponent.get(playingFaction);
        for (Position location : faction.getOwnedLocations()) {
            Entity entity = gameState.getEntities().get(location.getX(), location.getY());
            if (entity == null && faction.getMembers().size() <= 3) {
                UUID id = GameEngine.getID(createMonster(gameState, playingFaction, location));
                faction.getMembers().add(id);
            }
        }

        return null;
    }

    private Entity createMonster(GameState gameState, Entity faction, Position position) {
        GameStateComponent gameStateComponent = GameStateComponent.get(faction);
        Entity entityFaction = gameState.get(gameStateComponent.getId());
        Entity goblin = new Entity();
        goblin.add(new PositionComponent(position.getX(), position.getY()))
            .add(new GameStateComponent())
            .add(new RenderComponent(ResourceManager.get(LoadTextures.Sprites)[2][8]))
            .add(new ActorComponent())
            .add(new AIComponent(new MCTSAI()))
            .add(StatsComponent.builder().handicapMult(1).build().init())
            .add(new InventoryComponent())
            .add(new LoyaltyComponent().putLoyalty(faction, Loyalties.ALLY));
        System.out.println(goblin);
        GameState.global().getEngine().addEntity(goblin);
        return goblin;
    }
}
