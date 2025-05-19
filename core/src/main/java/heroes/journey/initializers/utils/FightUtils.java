package heroes.journey.initializers.utils;

import java.util.UUID;

import heroes.journey.GameState;
import heroes.journey.components.EquipmentComponent;
import heroes.journey.components.StatsComponent;
import heroes.journey.entities.items.Item;
import heroes.journey.entities.tagging.Attributes;
import heroes.journey.entities.tagging.Group;
import heroes.journey.entities.tagging.Operation;
import heroes.journey.initializers.base.tags.ConversionSets;
import heroes.journey.initializers.base.tags.DamageTypes;
import heroes.journey.initializers.base.tags.DefenseTypes;
import heroes.journey.initializers.base.tags.Groups;
import heroes.journey.initializers.base.tags.Stats;
import heroes.journey.systems.GameWorld;

public class FightUtils {
    public static boolean fight(GameState gameState, UUID fighter, UUID enemy) {
        GameWorld world = gameState.getWorld();
        Attributes fighterStats = StatsComponent.get(world, fighter);
        Attributes enemyStats = StatsComponent.get(world, enemy);

        UUID attacker =
            StatsUtils.getSpeed(fighterStats) >= StatsUtils.getSpeed(enemyStats) ? fighter : enemy;
        UUID defender = attacker == fighter ? enemy : fighter;

        // TODO if fast enough attack multiple times
        while (fighterStats.get(Stats.HEALTH) > 0 && enemyStats.get(Stats.HEALTH) > 0) {
            attack(gameState, attacker, defender);
            UUID previousDefender = defender;
            defender = attacker;
            attacker = previousDefender;
        }
        return fighterStats.get(Stats.HEALTH) > 0;
    }

    private static void attack(GameState gameState, UUID attacker, UUID defender) {
        GameWorld world = gameState.getWorld();

        Attributes damages = getDamages(world, attacker).applyOperation(
            StatsComponent.getHandicap(world, attacker), Operation.MULTIPLY);
        Attributes defenses = getDefenses(world, defender).applyOperation(
                StatsComponent.getHandicap(world, defender), Operation.MULTIPLY)
            .convert(ConversionSets.DEFENSE_TO_DAMAGE);

        damages.merge(defenses, Operation.SUBTRACT);

        int damage = Math.max(1, damages.getTotal()); // always at least 1

        StatsUtils.adjustHealth(gameState, defender, -damage);
    }

    public static void faint(GameWorld world, UUID e) {
        Attributes statsComponent = StatsComponent.get(world, e);
        statsComponent.put(Stats.BODY, 1);
        // TODO remove most valuable item
    }

    public static Attributes getDamages(GameWorld world, UUID attacker) {
        Attributes attackerStats = StatsComponent.get(world, attacker);
        EquipmentComponent attackerEquipment = EquipmentComponent.get(world, attacker);

        Attributes damages = new Attributes().add(DamageTypes.PHYSICAL, attackerStats.get(Stats.BODY));
        if (attackerEquipment != null) {
            damages = safeMerge(damages, attackerEquipment.handOne(), Groups.Damage);
            damages = safeMerge(damages, attackerEquipment.handTwo(), Groups.Damage);
        }
        return damages;
    }

    public static Attributes getDefenses(GameWorld world, UUID defender) {
        Attributes defenderStats = StatsComponent.get(world, defender);
        EquipmentComponent defenderEquipment = EquipmentComponent.get(world, defender);

        Attributes defenses = new Attributes().add(DefenseTypes.PHYSICAL_DEF, defenderStats.get(Stats.BODY))
            .merge(defenderStats.getTagsWithGroup(Groups.Defense));
        if (defenderEquipment != null) {
            defenses = safeMerge(defenses, defenderEquipment.head(), Groups.Defense);
            defenses = safeMerge(defenses, defenderEquipment.chest(), Groups.Defense);
            defenses = safeMerge(defenses, defenderEquipment.legs(), Groups.Defense);
            defenses = safeMerge(defenses, defenderEquipment.boots(), Groups.Defense);
        }
        return defenses;
    }

    private static Attributes safeMerge(Attributes base, Item item, Group group) {
        if (item != null && item.getAttributes() != null) {
            return base.merge(item.getAttributes().getTagsWithGroup(group));
        }
        return base;
    }
}
