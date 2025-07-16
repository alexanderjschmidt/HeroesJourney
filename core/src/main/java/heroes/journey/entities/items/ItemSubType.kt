package heroes.journey.entities.items

import heroes.journey.modlib.IItemSubType
import heroes.journey.modlib.ItemType
import heroes.journey.registries.Registrable
import heroes.journey.registries.Registries.ItemSubTypeManager
import lombok.Getter

@Getter
class ItemSubType(id: String, override val parentType: ItemType) : Registrable(id), IItemSubType {
    override fun register(): ItemSubType {
        return ItemSubTypeManager.register(this)
    }
}
