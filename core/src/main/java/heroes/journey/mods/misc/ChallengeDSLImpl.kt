package heroes.journey.mods.misc

import heroes.journey.entities.Challenge
import heroes.journey.entities.tagging.Attributes
import heroes.journey.modlib.attributes.IAttributes
import heroes.journey.modlib.misc.ChallengeDSL
import heroes.journey.modlib.misc.IChallenge
import heroes.journey.modlib.misc.ChallengeBuilder
import heroes.journey.modlib.attributes.AttributesBuilder
import heroes.journey.modlib.attributes.attributes

class ChallengeBuilderImpl : ChallengeBuilder {
    override var id: String = ""
    override var render: String = ""
    private val _approaches = mutableListOf<String>()
    private var _reward: IAttributes? = null
    override fun approach(id: String) {
        _approaches.add(id)
    }
    override fun reward(init: AttributesBuilder.() -> Unit) {
        _reward = attributes(init)
    }
    fun builtApproaches(): List<String> = _approaches
    fun builtReward(): Attributes = (_reward as? Attributes) ?: heroes.journey.entities.tagging.Attributes()
}

class ChallengeDSLImpl : ChallengeDSL {
    override fun challenge(init: ChallengeBuilder.() -> Unit): IChallenge {
        val builder = ChallengeBuilderImpl()
        builder.init()
        return Challenge(builder.id, builder.render, builder.builtApproaches(), builder.builtReward())
    }
}
