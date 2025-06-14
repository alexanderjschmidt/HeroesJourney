package heroes.journey.entities.items

import heroes.journey.registries.Registrable
import heroes.journey.registries.Registries.ItemSubTypeManager
import lombok.Getter

@Getter
class ItemSubType(id: String, name: String?, val parentType: ItemType) : Registrable(id, name) {
    override fun register(): ItemSubType {
        return ItemSubTypeManager.register(this)
    }
}
