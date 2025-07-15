package heroes.journey.entities

import heroes.journey.entities.tagging.Attributes
import heroes.journey.entities.tagging.AttributesBuilder
import heroes.journey.entities.tagging.Stat
import heroes.journey.entities.tagging.attributes
import heroes.journey.registries.Registrable
import heroes.journey.registries.Registries
import heroes.journey.registries.Registries.StatManager

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
    private val approachTagIds = mutableListOf<String>()
    private var rewardAttributes = Attributes()

    fun approaches(vararg tagIds: String) {
        approachTagIds.addAll(tagIds)
    }

    fun reward(init: AttributesBuilder.() -> Unit) {
        rewardAttributes = attributes(init)
    }

    fun build(): Challenge {
        return Challenge(
            id = id,
            render = render,
            approaches = approachTagIds.mapNotNull { StatManager.get(it) }.toTypedArray(),
        )
    }
}

fun challenge(id: String, init: ChallengeBuilder.() -> Unit): Challenge {
    val builder = ChallengeBuilder(id)
    builder.init()
    return builder.build()
}
