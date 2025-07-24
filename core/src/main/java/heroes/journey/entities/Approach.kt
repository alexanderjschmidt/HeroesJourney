package heroes.journey.entities

import heroes.journey.modlib.attributes.IAttributes
import heroes.journey.modlib.attributes.IStat
import heroes.journey.modlib.misc.IApproach
import heroes.journey.modlib.registries.Registrable
import heroes.journey.mods.Registries

class Approach(
    id: String,
    override val stats: List<IStat>,
    override val cost: IAttributes?,
    override val requiredAllTags: List<IStat>,
    override val requiredAnyTags: List<IStat>,
    override val forbiddenTags: List<IStat>
) : Registrable(id), IApproach {
    override fun register(): IApproach {
        return Registries.ApproachManager.register(this)
    }
}
