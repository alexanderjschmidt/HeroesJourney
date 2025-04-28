package heroes.journey.components.utils;

import com.artemis.World;

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

public class FightUtils {
    public static boolean fight(World world, Integer fighter, Integer enemy) {
        StatsComponent fighterStats = StatsComponent.get(world, fighter);
        StatsComponent enemyStats = StatsComponent.get(world, enemy);

        Integer attacker = fighterStats.getSpeed() >= enemyStats.getSpeed() ? fighter : enemy;
        Integer defender = attacker == fighter ? enemy : fighter;

        // TODO if fast enough attack multiple times
        while (fighterStats.getHealth() > 0 && enemyStats.getHealth() > 0) {
            attack(world, attacker, defender);
            Integer previousDefender = defender;
            defender = attacker;
            attacker = previousDefender;
        }
        return fighterStats.getHealth() > 0;
    }

    private static void attack(World world, Integer attacker, Integer defender) {
        StatsComponent attackerStats = StatsComponent.get(world, attacker);
        StatsComponent defenderStats = StatsComponent.get(world, defender);

        Attributes damages = getDamages(world, attacker).applyOperation(attackerStats.getHandicapMult(),
            Operation.MULTIPLY);
        Attributes defenses = getDefenses(world, defender).applyOperation(defenderStats.getHandicapMult(),
            Operation.MULTIPLY).convert(ConversionSets.DEFENSE_TO_DAMAGE);

        damages.merge(defenses, Operation.SUBTRACT);

        int damage = Math.max(1, damages.getTotal()); // always at least 1

        defenderStats.adjustHealth(-damage);
    }

    public static void faint(World world, Integer e) {
        StatsComponent statsComponent = StatsComponent.get(world, e);
        statsComponent.setHealth(1);
        // TODO remove most valuable item
    }

    public static Attributes getDamages(World world, Integer attacker) {
        StatsComponent attackerStats = StatsComponent.get(world, attacker);
        EquipmentComponent attackerEquipment = EquipmentComponent.get(world, attacker);

        Attributes damages = new Attributes().add(DamageTypes.PHYSICAL, attackerStats.getBody());
        if (attackerEquipment != null) {
            damages = safeMerge(damages, attackerEquipment.handOne(), Groups.Damage);
            damages = safeMerge(damages, attackerEquipment.handTwo(), Groups.Damage);
        }
        return damages;
    }

    public static Attributes getDefenses(World world, Integer defender) {
        StatsComponent defenderStats = StatsComponent.get(world, defender);
        EquipmentComponent defenderEquipment = EquipmentComponent.get(world, defender);

        Attributes defenses = new Attributes().add(DefenseTypes.PHYSICAL_DEF, defenderStats.getBody())
            .merge(defenderStats.getAttributes().getTagsWithGroup(Groups.Defense));
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
