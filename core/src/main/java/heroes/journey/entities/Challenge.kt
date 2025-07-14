package heroes.journey.entities

import heroes.journey.entities.tagging.Attributes
import heroes.journey.entities.tagging.AttributesBuilder
import heroes.journey.entities.tagging.Stat
import heroes.journey.entities.tagging.attributes
import heroes.journey.registries.Registrable
import heroes.journey.registries.Registries

class Challenge(
    id: String,
    val render: String,
    val approaches: Array<Stat>,
) : Registrable(id) {
    override fun register(): Challenge {
        return Registries.ChallengeManager.register(this)
    }
}

class ChallengeBuilder(private val id: String) {
    var render: String = ""
    private val approachTags = mutableListOf<Stat>()
    private var rewardAttributes = Attributes()

    fun approaches(vararg tags: Stat) {
        approachTags.addAll(tags)
    }

    fun reward(init: AttributesBuilder.() -> Unit) {
        rewardAttributes = attributes(init)
    }

    fun build(): Challenge {
        return Challenge(
            id = id,
            render = render,
            approaches = approachTags.toTypedArray(),
        )
    }
}

fun challenge(id: String, init: ChallengeBuilder.() -> Unit): Challenge {
    val builder = ChallengeBuilder(id)
    builder.init()
    return builder.build()
}
