package heroes.journey.mods.misc

import heroes.journey.entities.Buff
import heroes.journey.entities.tagging.Attributes
import heroes.journey.modlib.attributes.IAttributes
import heroes.journey.modlib.misc.BuffDSL
import heroes.journey.modlib.misc.IBuff

class BuffDSLImpl : BuffDSL {
    override fun buff(id: String, turnsBuffLasts: Int, attributes: IAttributes): IBuff {
        val coreAttributes = attributes as? Attributes ?: Attributes()
        return Buff(id, turnsBuffLasts, coreAttributes)
    }
}
