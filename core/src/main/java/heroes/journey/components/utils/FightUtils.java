package heroes.journey.components.utils;

import com.badlogic.ashley.core.Entity;

import heroes.journey.components.EquipmentComponent;
import heroes.journey.components.StatsComponent;
import heroes.journey.entities.items.Item;
import heroes.journey.entities.tagging.Attributes;
import heroes.journey.entities.tagging.Group;
import heroes.journey.entities.tagging.Operation;
import heroes.journey.initializers.base.ConversionSets;
import heroes.journey.initializers.base.DamageTypes;
import heroes.journey.initializers.base.DefenseTypes;
import heroes.journey.initializers.base.Groups;

public class FightUtils {
    public static boolean fight(Entity fighter, Entity enemy) {
        StatsComponent fighterStats = StatsComponent.get(fighter);
        StatsComponent enemyStats = StatsComponent.get(enemy);

        Entity attacker = fighterStats.getSpeed() >= enemyStats.getSpeed() ? fighter : enemy;
        Entity defender = attacker == fighter ? enemy : fighter;

        // TODO if fast enough attack multiple times
        while (fighterStats.getHealth() > 0 && enemyStats.getHealth() > 0) {
            attack(attacker, defender);
            Entity previousDefender = defender;
            defender = attacker;
            attacker = previousDefender;
        }
        return fighterStats.getHealth() > 0;
    }

    private static void attack(Entity attacker, Entity defender) {
        StatsComponent attackerStats = StatsComponent.get(attacker);
        StatsComponent defenderStats = StatsComponent.get(defender);

        Attributes damages = getDamages(attacker).applyOperation(attackerStats.getHandicapMult(),
            Operation.MULTIPLY);
        Attributes defenses = getDefenses(defender).applyOperation(defenderStats.getHandicapMult(),
            Operation.MULTIPLY).convert(ConversionSets.DEFENSE_TO_DAMAGE);

        damages.merge(defenses, Operation.SUBTRACT);

        int damage = Math.max(1, damages.getTotal()); // always at least 1

        defenderStats.adjustHealth(-damage);
    }

    public static void faint(Entity e) {
        StatsComponent statsComponent = StatsComponent.get(e);
        statsComponent.setHealth(1);
        // TODO remove most valuable item
    }

    public static Attributes getDamages(Entity attacker) {
        StatsComponent attackerStats = StatsComponent.get(attacker);
        EquipmentComponent attackerEquipment = EquipmentComponent.get(attacker);

        Attributes damages = new Attributes().add(DamageTypes.PHYSICAL, attackerStats.getBody());
        if (attackerEquipment != null) {
            damages = safeMerge(damages, attackerEquipment.getHandOne(), Groups.Damage);
            damages = safeMerge(damages, attackerEquipment.getHandTwo(), Groups.Damage);
        }
        return damages;
    }

    public static Attributes getDefenses(Entity defender) {
        StatsComponent defenderStats = StatsComponent.get(defender);
        EquipmentComponent defenderEquipment = EquipmentComponent.get(defender);

        Attributes defenses = new Attributes().add(DefenseTypes.PHYSICAL_DEF, defenderStats.getBody())
            .merge(defenderStats.getAttributes().getTagsWithGroup(Groups.Defense));
        if (defenderEquipment != null) {
            defenses = safeMerge(defenses, defenderEquipment.getHead(), Groups.Defense);
            defenses = safeMerge(defenses, defenderEquipment.getChest(), Groups.Defense);
            defenses = safeMerge(defenses, defenderEquipment.getLegs(), Groups.Defense);
            defenses = safeMerge(defenses, defenderEquipment.getBoots(), Groups.Defense);
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
