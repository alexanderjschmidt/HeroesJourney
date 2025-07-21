package heroes.journey.mods.misc

import heroes.journey.entities.Feat
import heroes.journey.modlib.actions.IActionContext
import heroes.journey.modlib.misc.FeatType
import heroes.journey.modlib.misc.IFeat
import heroes.journey.modlib.misc.FeatBuilder
import heroes.journey.modlib.misc.FeatDSL

class FeatBuilderImpl : FeatBuilder {
    override var id: String = ""
    override var featType: FeatType = FeatType.PASSIVE
    override var onEarnFn: (IActionContext) -> Unit = {}
    override var onLoseFn: (IActionContext) -> Unit = {}
}

class FeatDSLImpl : FeatDSL {
    override fun feat(init: FeatBuilder.() -> Unit): IFeat {
        val builder = FeatBuilderImpl()
        builder.init()
        return Feat(builder.id, builder.featType, builder.onEarnFn, builder.onLoseFn)
    }
} 