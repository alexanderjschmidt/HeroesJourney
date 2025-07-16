package heroes.journey.mods

import heroes.journey.entities.Challenge
import heroes.journey.entities.tagging.Attributes
import heroes.journey.modlib.ChallengeDSL
import heroes.journey.modlib.IAttributes
import heroes.journey.modlib.IChallenge

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
