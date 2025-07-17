package heroes.journey.mods.items

import heroes.journey.entities.items.ItemSubType
import heroes.journey.modlib.IItemSubType
import heroes.journey.modlib.ItemSubTypeDSL
import heroes.journey.modlib.ItemType

class ItemSubTypeDSLImpl : ItemSubTypeDSL {
    override fun itemSubType(id: String, parentType: ItemType): IItemSubType {
        return ItemSubType(id, parentType)
    }
}
