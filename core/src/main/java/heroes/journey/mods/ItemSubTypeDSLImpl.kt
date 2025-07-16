package heroes.journey.mods

import heroes.journey.modlib.IItemSubType
import heroes.journey.modlib.ItemSubTypeDSL
import heroes.journey.modlib.ItemType
import heroes.journey.entities.items.ItemSubType

class ItemSubTypeDSLImpl : ItemSubTypeDSL {
    override fun itemSubType(id: String, parentType: ItemType): IItemSubType {
        return ItemSubType(id, parentType)
    }
}
