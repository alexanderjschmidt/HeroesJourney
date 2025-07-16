package heroes.journey.mods

import heroes.journey.modlib.IItem
import heroes.journey.modlib.IItemSubType
import heroes.journey.modlib.IAttributes
import heroes.journey.modlib.ItemDSL
import heroes.journey.modlib.attributes
import heroes.journey.entities.items.Item
import heroes.journey.entities.items.ItemSubType
import heroes.journey.entities.tagging.Attributes

class ItemDSLImpl : ItemDSL {
    override fun item(id: String, subType: IItemSubType, weight: Int, attributes: IAttributes): IItem {
        val coreSubType = subType as ItemSubType
        val coreAttributes = attributes as? Attributes ?: Attributes()
        return Item(id, coreSubType, weight, coreAttributes)
    }
} 