package heroes.journey.mods.misc

import heroes.journey.entities.Challenge
import heroes.journey.entities.tagging.Attributes
import heroes.journey.modlib.attributes.IAttributes
import heroes.journey.modlib.misc.ChallengeDSL
import heroes.journey.modlib.misc.IChallenge

class ChallengeDSLImpl : ChallengeDSL {
    override fun challenge(
        id: String,
        render: String,
        approaches: List<String>,
        reward: IAttributes
    ): IChallenge {
        val coreReward = reward as? Attributes ?: Attributes()
        return Challenge(id, render, approaches, coreReward)
    }
}
