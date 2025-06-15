package heroes.journey.entities.items

import heroes.journey.entities.tagging.Attributes
import heroes.journey.registries.Registrable
import heroes.journey.registries.Registries.ItemManager
import lombok.Getter

@Getter
open class Item(
    id: String,
    name: String?,
    val subType: ItemSubType,
    val weight: Int,
    val attributes: Attributes = Attributes()
) :
    Registrable(id, name) {

    val type: ItemType
        get() = subType.parentType

    override fun register(): Item {
        return ItemManager.register(this)
    }
}
