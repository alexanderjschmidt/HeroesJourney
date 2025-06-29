package heroes.journey.entities

import heroes.journey.entities.tagging.Attributes
import heroes.journey.registries.Registrable
import heroes.journey.registries.Registries

class Challenge(
    id: String,
    nameInternal: String?,
    val description: String,
    val reward: Attributes
) : Registrable(id, nameInternal) {
    override fun register(): Challenge {
        return Registries.ChallengeManager.register(this)
    }
}
