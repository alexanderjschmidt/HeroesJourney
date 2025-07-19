package heroes.journey.entities

import heroes.journey.modlib.misc.IChallenge
import heroes.journey.modlib.misc.IChallengeType
import heroes.journey.modlib.registries.Registrable
import heroes.journey.mods.Registries

class Challenge(
    id: String,
    override val render: String,
    override val challengeType: String,
) : Registrable(id), IChallenge {
    
    override fun getChallengeType(): IChallengeType {
        return Registries.ChallengeTypeManager[challengeType] 
            ?: throw IllegalArgumentException("No challenge type found for $challengeType")
    }
    
    override fun register(): Challenge {
        return Registries.ChallengeManager.register(this)
    }
}
