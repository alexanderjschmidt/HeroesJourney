package heroes.journey.entities

import heroes.journey.modlib.actions.IActionContext
import heroes.journey.modlib.misc.FeatType
import heroes.journey.modlib.misc.IFeat
import heroes.journey.modlib.registries.Registrable
import heroes.journey.mods.Registries

class Feat(
    id: String,
    override val featType: FeatType,
    override val onEarnFn: (IActionContext) -> Unit = {},
    override val onLoseFn: (IActionContext) -> Unit = {},
) : Registrable(id), IFeat {
    
    override fun register(): IFeat {
        return Registries.FeatManager.register(this)
    }
}
