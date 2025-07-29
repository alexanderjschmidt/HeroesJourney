package heroes.journey.mods.items

import heroes.journey.entities.items.Item
import heroes.journey.modlib.items.ItemSubType
import heroes.journey.entities.tagging.Attributes
import heroes.journey.modlib.attributes.IAttributes
import heroes.journey.modlib.attributes.attributes
import heroes.journey.modlib.items.IItem
import heroes.journey.modlib.items.ItemBuilder
import heroes.journey.modlib.items.ItemDSL
import heroes.journey.mods.Registries

class ItemBuilderImpl : ItemBuilder {
    override var id: String = ""
    override var subTypeId: String? = null
    override var weight: Int = 1
    private val attrMap = mutableMapOf<String, Int>()
    private var _attributes: IAttributes? = null
    override var attributes: IAttributes
        get() = _attributes ?: heroes.journey.entities.tagging.Attributes()
        set(value) { _attributes = value }

    override fun attribute(statId: String, value: Int) {
        attrMap[statId] = value
        _attributes = heroes.journey.entities.tagging.Attributes().apply {
            for ((k, v) in attrMap) put(k, v)
        }
    }
}

class ItemDSLImpl : ItemDSL {
    override fun item(init: ItemBuilder.() -> Unit): IItem {
        val builder = ItemBuilderImpl()
        builder.init()
        val coreSubType = builder.subTypeId?.let { Registries.ItemSubTypeManager[it] as ItemSubType }
        val coreAttributes = builder.attributes as? Attributes ?: Attributes()
        return Item(builder.id, coreSubType!!, builder.weight, coreAttributes)
    }
}
