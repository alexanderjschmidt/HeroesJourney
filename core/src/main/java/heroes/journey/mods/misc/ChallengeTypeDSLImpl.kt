package heroes.journey.mods.misc

import heroes.journey.entities.Approach
import heroes.journey.entities.ChallengeType
import heroes.journey.modlib.misc.ChallengeTypeBuilder
import heroes.journey.modlib.misc.ChallengeTypeDSL
import heroes.journey.modlib.misc.IChallengeType
import heroes.journey.mods.Registries

class ChallengeTypeBuilderImpl : ChallengeTypeBuilder {
    override var id: String = ""
    override var primaryApproachId: String = ""
    val secondaryApproachIds = mutableListOf<String>()

    override fun secondaryApproach(approachId: String) {
        secondaryApproachIds.add(approachId)
    }
}

class ChallengeTypeDSLImpl : ChallengeTypeDSL {
    override fun challengeType(init: ChallengeTypeBuilder.() -> Unit): IChallengeType {
        val builder = ChallengeTypeBuilderImpl()
        builder.init()

        // Look up the primary approach from registry
        val primaryApproach = Registries.ApproachManager[builder.primaryApproachId] as Approach

        // Look up the secondary approaches from registry
        val secondaryApproaches = builder.secondaryApproachIds.map { approachId ->
            Registries.ApproachManager[approachId] as Approach
        }

        return ChallengeType(builder.id, primaryApproach, secondaryApproaches)
    }
}
