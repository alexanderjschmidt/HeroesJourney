package heroes.journey.mods

import heroes.journey.modlib.BuffDSL
import heroes.journey.modlib.IBuff
import heroes.journey.modlib.IAttributes
import heroes.journey.entities.Buff
import heroes.journey.entities.tagging.Attributes

class BuffDSLImpl : BuffDSL {
    override fun buff(id: String, turnsBuffLasts: Int, attributes: IAttributes): IBuff {
        val coreAttributes = attributes as? Attributes ?: Attributes()
        return Buff(id, turnsBuffLasts, coreAttributes)
    }
} 