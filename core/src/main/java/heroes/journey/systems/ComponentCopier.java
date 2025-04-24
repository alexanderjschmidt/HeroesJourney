package heroes.journey.systems;

import com.artemis.EntityEdit;
import com.artemis.World;

import heroes.journey.components.EquipmentComponent;
import heroes.journey.components.InventoryComponent;
import heroes.journey.components.QuestsComponent;
import heroes.journey.components.StatsComponent;
import heroes.journey.components.character.AIComponent;
import heroes.journey.components.character.ActorComponent;
import heroes.journey.components.character.CooldownComponent;
import heroes.journey.components.character.IdComponent;
import heroes.journey.components.character.NamedComponent;
import heroes.journey.components.character.PlayerComponent;
import heroes.journey.components.character.PositionComponent;
import heroes.journey.components.character.PossibleActionsComponent;
import heroes.journey.components.place.CarriageComponent;
import heroes.journey.components.place.DungeonComponent;
import heroes.journey.components.place.LocationComponent;
import heroes.journey.components.utils.PooledClonableComponent;

public class ComponentCopier {

    public static void copyEntity(World world, World newWorld, int entityId) {
        EntityEdit newEntity = newWorld.getEntity(entityId).edit();

        copyComponent(world, newEntity, entityId, ActorComponent.class);
        copyComponent(world, newEntity, entityId, AIComponent.class);
        copyComponent(world, newEntity, entityId, CooldownComponent.class);
        copyComponent(world, newEntity, entityId, IdComponent.class);
        copyComponent(world, newEntity, entityId, NamedComponent.class);
        copyComponent(world, newEntity, entityId, PlayerComponent.class);
        copyComponent(world, newEntity, entityId, PositionComponent.class);
        copyComponent(world, newEntity, entityId, PossibleActionsComponent.class);
        copyComponent(world, newEntity, entityId, CarriageComponent.class);
        copyComponent(world, newEntity, entityId, DungeonComponent.class);
        copyComponent(world, newEntity, entityId, LocationComponent.class);
        copyComponent(world, newEntity, entityId, EquipmentComponent.class);
        copyComponent(world, newEntity, entityId, InventoryComponent.class);
        copyComponent(world, newEntity, entityId, QuestsComponent.class);
        copyComponent(world, newEntity, entityId, StatsComponent.class);
    }

    private static <T extends PooledClonableComponent<T>> void copyComponent(
        World world,
        EntityEdit newEntity,
        int entityId,
        Class<T> type) {
        try {
            T oldComponent = world.getMapper(type).get(entityId);
            if (oldComponent != null) {
                newEntity.create(type).copy(oldComponent);
            }
        } catch (Exception e) {
            System.out.println(type);
            e.printStackTrace();
            throw e;
        }
    }

}
