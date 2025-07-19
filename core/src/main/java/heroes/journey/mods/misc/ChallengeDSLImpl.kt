package heroes.journey.mods.misc

import heroes.journey.entities.Challenge
import heroes.journey.modlib.misc.IChallenge
import heroes.journey.modlib.misc.ChallengeDSL
import heroes.journey.modlib.misc.ChallengeBuilder

class ChallengeBuilderImpl : ChallengeBuilder {
    override var id: String = ""
    override var render: String = ""
    override var challengeType: String = ""
}

class ChallengeDSLImpl : ChallengeDSL {
    override fun challenge(init: ChallengeBuilder.() -> Unit): IChallenge {
        val builder = ChallengeBuilderImpl()
        builder.init()
        return Challenge(builder.id, builder.render, builder.challengeType)
    }
}
