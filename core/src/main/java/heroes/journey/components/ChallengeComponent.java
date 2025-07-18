package heroes.journey.components;

import static heroes.journey.mods.Registries.ChallengeManager;

import java.util.UUID;

import heroes.journey.components.utils.PooledClonableComponent;
import heroes.journey.entities.Challenge;
import heroes.journey.systems.GameWorld;

public class ChallengeComponent extends PooledClonableComponent<ChallengeComponent> {

    private String challenge;

    public String toString() {
        return challenge;
    }

    public static ChallengeComponent get(GameWorld world, UUID entityId) {
        return world.getEntity(ChallengeComponent.class, entityId);
    }

    public Challenge challenge() {
        return ChallengeManager.get(challenge);
    }

    public ChallengeComponent challenge(Challenge challenge) {
        this.challenge = challenge.getId();
        return this;
    }

    @Override
    protected void reset() {
        challenge = null;
    }

    @Override
    public void copy(ChallengeComponent from) {
        challenge = from.challenge;
    }
}
