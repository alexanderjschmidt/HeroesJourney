package heroes.journey.systems.triggerable;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.artemis.annotations.All;

import heroes.journey.components.QuestsComponent;
import heroes.journey.components.RegionComponent;
import heroes.journey.components.character.IdComponent;
import heroes.journey.modlib.misc.Challenge;
import heroes.journey.modlib.misc.Quest;
import heroes.journey.modlib.utils.Position;
import heroes.journey.mods.Registries;
import heroes.journey.systems.GameWorld;
import heroes.journey.systems.TriggerableSystem;
import heroes.journey.utils.Random;

@All({RegionComponent.class, IdComponent.class})
public class RegionManagementSystem extends TriggerableSystem {

    @Override
    protected void process(int entityId) {
        GameWorld world = (GameWorld)getWorld();
        UUID regionId = IdComponent.get(world, entityId);
        RegionComponent regionComponent = RegionComponent.get(world, regionId);

        // Get current turn number
        int currentTurn = world.getGameState().getTurnNumber();

        // Get quests for this region (regions can have quest boards)
        QuestsComponent questsComponent = QuestsComponent.get(world, regionId);
        List<Quest> quests = questsComponent != null ? questsComponent.getQuests() : List.of();

        // Get challenges for this region
        List<UUID> challenges = regionComponent.getChallenges();

        // Quest management: Add random quest if no quests available (only if region has QuestsComponent)
        if (questsComponent != null && quests.isEmpty()) {
            String randomQuestId = Registries.QuestManager.keySet().toArray(new String[0])[Random.get()
                .nextInt(Registries.QuestManager.size())];
            Quest randomQuest = Registries.QuestManager.get(randomQuestId);
            questsComponent.addQuest(randomQuest);
            System.out.println("Added random quest '" + randomQuest.getName() + "' to region " + regionId);
        }

        // Challenge management: Add random challenges if less than 3
        while (challenges.size() < 3) {
            String[] keySet;
            try {
                keySet = Registries.ChallengeManager.keySet()
                    .toArray(new String[Registries.ChallengeManager.size()]);
            } catch (Exception e) {
                System.out.println(Registries.ChallengeManager.size());
                throw e;
            }

            // Get turn-based power tier constraints from GameState (set by dynamic turn configs)
            int minPowerTier = world.getGameState().getMinChallengePowerTier();
            int maxPowerTier = world.getGameState().getMaxChallengePowerTier();

            // Find a challenge that fits the power tier constraints
            Challenge randomChallenge = null;
            int attempts = 0;
            final int maxAttempts = 50; // Prevent infinite loops

            while (randomChallenge == null && attempts < maxAttempts) {
                String randomChallengeId = keySet[Random.get().nextInt(keySet.length)];
                if (randomChallengeId == null) {
                    attempts++;
                    continue;
                }

                Challenge candidate = Registries.ChallengeManager.get(randomChallengeId);
                if (candidate != null && candidate.getPowerTier() >= minPowerTier &&
                    candidate.getPowerTier() <= maxPowerTier) {
                    randomChallenge = candidate;
                }
                attempts++;
            }

            if (randomChallenge == null) {
                continue;
            }

            Set<Position> regionTiles = regionComponent.getTiles();

            // Find a random position from the region tiles
            // TODO make this use poison disk sampling so the new challenge is far away from existing entities
            Position[] tilesArray = regionTiles.toArray(new Position[0]);
            Position randomPosition = tilesArray[Random.get().nextInt(tilesArray.length)];

            UUID challengeEntityId = world.getEntityFactory()
                .createChallenge(randomChallenge, randomPosition.x, randomPosition.y);
            regionComponent.addChallenge(challengeEntityId);
            System.out.println("Added random challenge '" + randomChallenge.getName() + "' (Power Tier: " +
                randomChallenge.getPowerTier() + ") to region " + regionId + " at position (" +
                randomPosition.x + ", " + randomPosition.y + ") " + "[Turn " + currentTurn +
                ", Power Tier Range: " + minPowerTier + "-" + maxPowerTier + "]");

            // Refresh the challenges list after adding a new challenge
            challenges = regionComponent.getChallenges();
        }
    }

    @Override
    public EventTrigger getTrigger() {
        return EventTrigger.TURN;
    }
}
