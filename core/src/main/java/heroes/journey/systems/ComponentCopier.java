package heroes.journey.systems;

import com.artemis.EntityEdit;
import com.artemis.World;

import heroes.journey.components.EquipmentComponent;
import heroes.journey.components.InventoryComponent;
import heroes.journey.components.NamedComponent;
import heroes.journey.components.PositionComponent;
import heroes.journey.components.PossibleActionsComponent;
import heroes.journey.components.QuestsComponent;
import heroes.journey.components.StatsComponent;
import heroes.journey.components.character.AIComponent;
import heroes.journey.components.character.IdComponent;
import heroes.journey.components.character.MapComponent;
import heroes.journey.components.character.PlayerComponent;
import heroes.journey.components.place.DungeonComponent;
import heroes.journey.components.place.LocationComponent;
import heroes.journey.components.utils.PooledClonableComponent;
import heroes.journey.initializers.utils.Utils;

public class ComponentCopier {

    public static void copyEntity(World world, World newWorld, int oldId, int entityId) {
        EntityEdit newEntity = newWorld.getEntity(entityId).edit();

        copyComponent(world, newEntity, oldId, IdComponent.class);
        copyComponent(world, newEntity, oldId, AIComponent.class);
        copyComponent(world, newEntity, oldId, NamedComponent.class);
        copyComponent(world, newEntity, oldId, PlayerComponent.class);
        copyComponent(world, newEntity, oldId, PositionComponent.class);
        copyComponent(world, newEntity, oldId, PossibleActionsComponent.class);
        copyComponent(world, newEntity, oldId, DungeonComponent.class);
        copyComponent(world, newEntity, oldId, LocationComponent.class);
        copyComponent(world, newEntity, oldId, EquipmentComponent.class);
        copyComponent(world, newEntity, oldId, InventoryComponent.class);
        copyComponent(world, newEntity, oldId, QuestsComponent.class);
        copyComponent(world, newEntity, oldId, StatsComponent.class);
        copyComponent(world, newEntity, oldId, MapComponent.class);
    }

    private static <T extends PooledClonableComponent<T>> void copyComponent(
        World world,
        EntityEdit newEntity,
        int oldId,
        Class<T> type) {
        long startTime = System.nanoTime();
        try {
            T oldComponent = world.getMapper(type).get(oldId);
            if (oldComponent != null) {
                newEntity.create(type).copy(oldComponent);
            }
        } catch (Exception e) {
            System.out.println(type);
            e.printStackTrace();
            throw e;
        }
        Utils.logTime("Copy " + type, startTime, 20);
    }

}
