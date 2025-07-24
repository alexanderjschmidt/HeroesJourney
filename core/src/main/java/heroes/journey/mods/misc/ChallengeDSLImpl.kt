package heroes.journey.mods.misc

import heroes.journey.entities.Challenge
import heroes.journey.entities.tagging.Stat
import heroes.journey.modlib.attributes.IStat
import heroes.journey.modlib.misc.IChallenge
import heroes.journey.modlib.misc.ChallengeDSL
import heroes.journey.modlib.misc.ChallengeBuilder
import heroes.journey.mods.Registries

class ChallengeBuilderImpl : ChallengeBuilder {
    override var id: String = ""
    override var render: String = ""
    private val statIds = mutableListOf<String>()
    
    override fun stat(statId: String) {
        statIds.add(statId)
    }
    
    fun build(): Challenge {
        val stats = statIds.map { statId ->
            Registries.StatManager[statId] as Stat
        }
        return Challenge(id, render, stats)
    }
}

class ChallengeDSLImpl : ChallengeDSL {
    override fun challenge(init: ChallengeBuilder.() -> Unit): IChallenge {
        val builder = ChallengeBuilderImpl()
        builder.init()
        return builder.build()
    }
}
