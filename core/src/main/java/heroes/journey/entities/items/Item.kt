package heroes.journey.entities.items

import heroes.journey.entities.tagging.Attributes
import heroes.journey.modlib.items.IItem
import heroes.journey.modlib.items.ItemSubType
import heroes.journey.modlib.items.ItemType
import heroes.journey.modlib.registries.Registrable
import heroes.journey.mods.Registries.ItemManager
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
