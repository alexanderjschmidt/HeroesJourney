package heroes.journey.modlib.items

import heroes.journey.modlib.registries.Registrable
import heroes.journey.modlib.registries.Registries

/**
 * An ItemSubType, used for item categorization.
 * This is a simple data container with no complex functions.
 */
class ItemSubType(
    id: String,
    val parentType: ItemType
) : Registrable(id) {

    override fun register(): ItemSubType {
        Registries.ItemSubTypeManager.register(this)
        return this
    }
}

/**
 * Builder for defining an item subtype in a natural DSL style.
 */
class ItemSubTypeBuilder {
    var id: String = ""
    var type: ItemType? = null
}

/**
 * DSL entrypoint for defining a new item subtype.
 *
 * Example usage:
 * ```kotlin
 * itemSubType {
 *     id = Ids.ITEMSUBTYPE_SWORD
 *     type = ItemType.Weapon
 * }
 * ```
 */
fun itemSubType(init: ItemSubTypeBuilder.() -> Unit): ItemSubType {
    val builder = ItemSubTypeBuilder()
    builder.init()
    val coreType = builder.type ?: ItemType.Misc
    return ItemSubType(builder.id, coreType)
}

