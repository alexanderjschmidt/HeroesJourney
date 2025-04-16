package heroes.journey.components.utils;

import com.badlogic.ashley.core.Entity;

import heroes.journey.components.StatsComponent;

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

        int damage = (attackerStats.getBody() * attackerStats.getHandicapMult()) -
            (defenderStats.getBody() * defenderStats.getHandicapMult());
        damage = Math.max(1, damage); // always at least 1

        defenderStats.adjustHealth(-damage);
    }

    public static void faint(Entity e) {
        StatsComponent statsComponent = StatsComponent.get(e);
        statsComponent.setHealth(1);
    }
}
