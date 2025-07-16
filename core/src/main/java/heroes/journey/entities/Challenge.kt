package heroes.journey.entities

import heroes.journey.entities.tagging.Attributes
import heroes.journey.entities.tagging.Stat
import heroes.journey.modlib.IChallenge
import heroes.journey.registries.Registrable
import heroes.journey.registries.Registries
import heroes.journey.registries.Registries.StatManager

class Challenge(
    id: String,
    override val render: String,
    override val approaches: List<String>,
    override val reward: Attributes, // Add reward property
) : Registrable(id), IChallenge {
    override fun register(): Challenge {
        return Registries.ChallengeManager.register(this)
    }

    fun approaches(): List<Stat> {
        return StatManager.get(approaches)
    }
}
