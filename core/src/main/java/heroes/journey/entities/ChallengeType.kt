package heroes.journey.entities

import heroes.journey.modlib.misc.IApproach
import heroes.journey.modlib.misc.IChallengeType
import heroes.journey.modlib.registries.Registrable
import heroes.journey.mods.Registries

class ChallengeType(
    id: String,
    override val primaryApproach: IApproach,
    override val secondaryApproaches: List<IApproach>,
) : Registrable(id), IChallengeType {
    
    override fun getApproaches(): List<IApproach> {
        return listOf(primaryApproach) + secondaryApproaches
    }
    
    override fun register(): IChallengeType {
        return Registries.ChallengeTypeManager.register(this)
    }
}

class Approach(
    id: String,
    override val baseStatId: String,
    override val secondaryStatId: String?
) : Registrable(id), IApproach {
    override fun register(): IApproach {
        return Registries.ApproachManager.register(this)
    }
}
