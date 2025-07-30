package heroes.journey.systems;

import com.artemis.EntityEdit;
import com.artemis.World;
import heroes.journey.components.*;
import heroes.journey.components.character.*;
import heroes.journey.components.utils.PooledClonableComponent;
import heroes.journey.utils.Utils;

public class ComponentCopier {

    public static Class[] classes = {IdComponent.class};

    public static void copyEntity(World world, World newWorld, int oldId, int entityId) {
        EntityEdit newEntity = newWorld.getEntity(entityId).edit();

        for (Class c : classes) {
            copyComponent(world, newEntity, oldId, c);
        }
        copyComponent(world, newEntity, oldId, AITurnComponent.class);
        copyComponent(world, newEntity, oldId, AIWanderComponent.class);
        copyComponent(world, newEntity, oldId, NamedComponent.class);
        copyComponent(world, newEntity, oldId, PlayerComponent.class);
        copyComponent(world, newEntity, oldId, PositionComponent.class);
        copyComponent(world, newEntity, oldId, PossibleActionsComponent.class);
        copyComponent(world, newEntity, oldId, LocationComponent.class);
        copyComponent(world, newEntity, oldId, RegionComponent.class);
        copyComponent(world, newEntity, oldId, QuestsComponent.class);
        copyComponent(world, newEntity, oldId, StatsComponent.class);
        copyComponent(world, newEntity, oldId, BuffsComponent.class);
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
