package heroes.journey.mods.items

import heroes.journey.entities.items.Item
import heroes.journey.entities.items.ItemSubType
import heroes.journey.entities.tagging.Attributes
import heroes.journey.modlib.attributes.IAttributes
import heroes.journey.modlib.items.IItem
import heroes.journey.modlib.items.IItemSubType
import heroes.journey.modlib.items.ItemDSL

class ItemDSLImpl : ItemDSL {
    override fun item(id: String, subType: IItemSubType, weight: Int, attributes: IAttributes): IItem {
        val coreSubType = subType as ItemSubType
        val coreAttributes = attributes as? Attributes ?: Attributes()
        return Item(id, coreSubType, weight, coreAttributes)
    }
}
