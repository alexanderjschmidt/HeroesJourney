package heroes.journey.entities.items

import heroes.journey.entities.tagging.Attributes
import heroes.journey.modlib.IItem
import heroes.journey.modlib.ItemType
import heroes.journey.registries.Registrable
import heroes.journey.registries.Registries.ItemManager
import lombok.Getter

@Getter
open class Item(
    id: String,
    override val subType: ItemSubType,
    override val weight: Int,
    override val attributes: Attributes = Attributes()
) :
    Registrable(id), IItem {

    override fun register(): Item {
        return ItemManager.register(this)
    }

    val type: ItemType
        get() = subType.parentType
}
