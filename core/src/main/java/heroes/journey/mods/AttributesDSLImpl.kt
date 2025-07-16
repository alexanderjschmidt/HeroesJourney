package heroes.journey.mods

import heroes.journey.modlib.AttributesDSL
import heroes.journey.modlib.IAttributes
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