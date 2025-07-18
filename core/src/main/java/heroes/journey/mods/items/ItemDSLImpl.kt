package heroes.journey.mods.items

import heroes.journey.entities.items.Item
import heroes.journey.entities.items.ItemSubType
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
    override var attributes: IAttributes
        get() = attributes(*attrMap.toList().toTypedArray())
        set(_) { /* ignore direct set, use attribute() */ }

    override fun attribute(statId: String, value: Int) {
        attrMap[statId] = value
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
