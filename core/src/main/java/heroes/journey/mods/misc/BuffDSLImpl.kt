package heroes.journey.mods.misc

import heroes.journey.entities.Buff
import heroes.journey.entities.tagging.Attributes
import heroes.journey.modlib.attributes.AttributesBuilder
import heroes.journey.modlib.attributes.IAttributes
import heroes.journey.modlib.misc.BuffBuilder
import heroes.journey.modlib.misc.BuffDSL
import heroes.journey.modlib.misc.IBuff

class BuffBuilderImpl : BuffBuilder {
    override var id: String = ""
    override var turnsBuffLasts: Int = 1
    private var _attributes: IAttributes? = null
    override fun attributes(init: AttributesBuilder.() -> Unit) {
        _attributes = heroes.journey.modlib.attributes.attributes(init)
    }

    fun builtAttributes(): Attributes =
        (_attributes as? Attributes) ?: heroes.journey.entities.tagging.Attributes()
}

class BuffDSLImpl : BuffDSL {
    override fun buff(init: BuffBuilder.() -> Unit): IBuff {
        val builder = BuffBuilderImpl()
        builder.init()
        return Buff(builder.id, builder.turnsBuffLasts, builder.builtAttributes())
    }
}
