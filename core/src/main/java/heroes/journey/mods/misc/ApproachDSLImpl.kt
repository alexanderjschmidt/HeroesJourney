package heroes.journey.mods.misc

import heroes.journey.entities.Approach
import heroes.journey.modlib.misc.IApproach
import heroes.journey.modlib.misc.ApproachBuilder
import heroes.journey.modlib.misc.ApproachDSL

class ApproachBuilderImpl : ApproachBuilder {
    override var id: String = ""
    override var baseStatId: String = ""
    override var secondaryStatId: String? = null
}

class ApproachDSLImpl : ApproachDSL {
    override fun approach(init: ApproachBuilder.() -> Unit): IApproach {
        val builder = ApproachBuilderImpl()
        builder.init()
        return Approach(builder.id, builder.baseStatId, builder.secondaryStatId)
    }
} 