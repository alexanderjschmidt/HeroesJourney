package heroes.journey.mods.attributes

import heroes.journey.modlib.attributes.AttributesDSL
import heroes.journey.modlib.attributes.IAttributes
import heroes.journey.entities.tagging.Attributes

class AttributesDSLImpl : AttributesDSL {
    override fun attributes(vararg pairs: Pair<String, Int>): IAttributes {
        val attrs = Attributes()
        for ((statId, value) in pairs) {
            attrs.put(statId, value)
        }
        return attrs
    }
}
