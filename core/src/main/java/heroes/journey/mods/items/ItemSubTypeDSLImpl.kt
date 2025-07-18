package heroes.journey.mods.items

import heroes.journey.entities.items.ItemSubType
import heroes.journey.modlib.items.IItemSubType
import heroes.journey.modlib.items.ItemSubTypeDSL
import heroes.journey.modlib.items.ItemType

class ItemSubTypeDSLImpl : ItemSubTypeDSL {
    override fun itemSubType(id: String, parentType: ItemType): IItemSubType {
        return ItemSubType(id, parentType)
    }
}
