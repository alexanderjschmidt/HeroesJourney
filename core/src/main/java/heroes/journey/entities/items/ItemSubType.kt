package heroes.journey.entities.items

import heroes.journey.modlib.items.IItemSubType
import heroes.journey.modlib.items.ItemType
import heroes.journey.modlib.registries.Registrable
import heroes.journey.mods.Registries.ItemSubTypeManager
import lombok.Getter

@Getter
class ItemSubType(id: String, override val parentType: ItemType) : Registrable(id), IItemSubType {
    override fun register(): ItemSubType {
        return ItemSubTypeManager.register(this)
    }
}
