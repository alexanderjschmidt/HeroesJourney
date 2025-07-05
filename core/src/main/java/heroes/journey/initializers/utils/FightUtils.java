package heroes.journey.initializers.utils;

import java.util.UUID;

import heroes.journey.GameState;
import heroes.journey.components.EquipmentComponent;
import heroes.journey.components.StatsComponent;
import heroes.journey.entities.items.Item;
import heroes.journey.entities.tagging.Attributes;
import heroes.journey.entities.tagging.Operation;
import heroes.journey.entities.tagging.Stat;
import heroes.journey.entities.tagging.Stat;
import heroes.journey.systems.GameWorld;

public class FightUtils {

    public static boolean struggle(GameState gameState, UUID challenger, UUID challenged, Stat challengeType) {
        GameWorld world = gameState.getWorld();

        Attributes damages = getEquipmentStats(world, challenger);
        Attributes defenses = getEquipmentStats(world, challenged);

        damages.merge(defenses, Operation.SUBTRACT);

        return damages.get(challengeType) >= 0;
    }

    public static void faint(GameWorld world, UUID e) {
        Attributes statsComponent = StatsComponent.get(world, e);
        statsComponent.put(Stat.BODY, 1);
        // TODO remove most valuable item
    }

    public static Attributes getEquipmentStats(GameWorld world, UUID entity) {
        Attributes attackerStats = StatsComponent.get(world, entity);
        EquipmentComponent equipment = EquipmentComponent.get(world, entity);

        Attributes stats = new Attributes(attackerStats);
        if (equipment != null) {
            stats = safeMerge(stats, equipment.handOne());
            stats = safeMerge(stats, equipment.handTwo());
            stats = safeMerge(stats, equipment.head());
            stats = safeMerge(stats, equipment.chest());
            stats = safeMerge(stats, equipment.legs());
            stats = safeMerge(stats, equipment.boots());
        }
        return stats;
    }

    private static Attributes safeMerge(Attributes base, Item item) {
        if (item != null && item.getAttributes() != null) {
            return base.merge(item.getAttributes());
        }
        return base;
    }
}
