package heroes.journey.mods.attributes

import heroes.journey.entities.tagging.Attributes
import heroes.journey.modlib.attributes.AttributesBuilder
import heroes.journey.modlib.attributes.AttributesDSL
import heroes.journey.modlib.attributes.IAttributes

class AttributesBuilderImpl : AttributesBuilder {
    private val attrMap = mutableMapOf<String, Int>()
    override fun stat(id: String, value: Int) {
        attrMap[id] = value
    }

    fun build(): Map<String, Int> = attrMap
}

class AttributesDSLImpl : AttributesDSL {
    override fun attributes(init: AttributesBuilder.() -> Unit): IAttributes {
        val builder = AttributesBuilderImpl()
        builder.init()
        val attrs = Attributes()
        for ((statId, value) in builder.build()) {
            attrs.put(statId, value, false)
        }
        return attrs
    }
}
