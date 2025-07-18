package heroes.journey.mods.items

import heroes.journey.entities.items.ItemSubType
import heroes.journey.modlib.items.IItemSubType
import heroes.journey.modlib.items.ItemSubTypeBuilder
import heroes.journey.modlib.items.ItemSubTypeDSL
import heroes.journey.modlib.items.ItemType
import heroes.journey.mods.Registries

class ItemSubTypeBuilderImpl : ItemSubTypeBuilder {
    override var id: String = ""
    override var type: ItemType? = null
}

class ItemSubTypeDSLImpl : ItemSubTypeDSL {
    override fun itemSubType(init: ItemSubTypeBuilder.() -> Unit): IItemSubType {
        val builder = ItemSubTypeBuilderImpl()
        builder.init()
        val coreType = builder.type ?: ItemType.Misc
        return ItemSubType(builder.id, coreType)
    }
}
