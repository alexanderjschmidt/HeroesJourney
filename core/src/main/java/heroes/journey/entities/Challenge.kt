package heroes.journey.entities

import heroes.journey.entities.tagging.Attributes
import heroes.journey.entities.tagging.AttributesBuilder
import heroes.journey.entities.tagging.Tag
import heroes.journey.entities.tagging.attributes
import heroes.journey.registries.Registrable
import heroes.journey.registries.Registries

class Challenge(
    id: String,
    nameInternal: String?,
    val description: String,
    val render: String,
    val approaches: Array<Tag>,
    val reward: Attributes
) : Registrable(id, nameInternal) {
    override fun register(): Challenge {
        return Registries.ChallengeManager.register(this)
    }
}

class ChallengeBuilder(private val id: String) {
    var name: String? = null
    var description: String = ""
    var render: String = ""
    private val approachTags = mutableListOf<Tag>()
    private var rewardAttributes = Attributes()

    fun approaches(vararg tags: Tag) {
        approachTags.addAll(tags)
    }

    fun reward(init: AttributesBuilder.() -> Unit) {
        rewardAttributes = attributes(init)
    }

    fun build(): Challenge {
        return Challenge(
            id = id,
            nameInternal = name,
            description = description,
            render = render,
            approaches = approachTags.toTypedArray(),
            reward = rewardAttributes
        )
    }
}

fun challenge(id: String, init: ChallengeBuilder.() -> Unit): Challenge {
    val builder = ChallengeBuilder(id)
    builder.init()
    return builder.build()
}
