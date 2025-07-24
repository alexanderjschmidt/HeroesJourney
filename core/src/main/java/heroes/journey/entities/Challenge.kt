package heroes.journey.entities

import heroes.journey.modlib.attributes.IStat
import heroes.journey.modlib.misc.IChallenge
import heroes.journey.modlib.registries.Registrable
import heroes.journey.mods.Registries

class Challenge(
    id: String,
    override val render: String,
    override val stats: List<IStat>
) : Registrable(id), IChallenge {
    
    override fun register(): Challenge {
        return Registries.ChallengeManager.register(this)
    }
}
