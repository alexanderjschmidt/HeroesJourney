package heroes.journey.mods.misc

import heroes.journey.entities.Challenge
import heroes.journey.entities.tagging.Stat
import heroes.journey.modlib.attributes.IAttributes
import heroes.journey.modlib.misc.ChallengeBuilder
import heroes.journey.modlib.misc.ChallengeDSL
import heroes.journey.modlib.misc.IChallenge
import heroes.journey.modlib.misc.powerLevels
import heroes.journey.mods.Registries

class ChallengeBuilderImpl : ChallengeBuilder {
    override var id: String = ""
    override var render: String = ""
    override var powerTier: Int = 1
    override var rewards: IAttributes? = null
    private val statIds = mutableListOf<String>()

    override fun tag(vararg statIdsIn: String) {
        for (statId in statIdsIn)
            statIds.add(statId)
    }

    fun build(): Challenge {
        require(powerTier in 1..powerLevels.size) { "powerTier must be between 1 and ${powerLevels.size}" }
        require(rewards != null) { "rewards must be set" }

        val stats = statIds.map { statId ->
            Registries.StatManager[statId] as Stat
        }
        return Challenge(id, render, stats, powerTier, rewards!!)
    }
}

class ChallengeDSLImpl : ChallengeDSL {
    override fun challenge(init: ChallengeBuilder.() -> Unit): IChallenge {
        val builder = ChallengeBuilderImpl()
        builder.init()
        return builder.build()
    }
}
