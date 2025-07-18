package heroes.journey.modlib.items

import heroes.journey.modlib.registries.IRegistrable

/**
 * Public interface for an ItemSubType, used for item categorization.
 * Mods should only use this interface, not implementation classes.
 */
interface IItemSubType : IRegistrable {

    /** The parent item type (e.g., Weapon, Armor, Misc, Consumable). */
    val parentType: ItemType
    override fun register(): IItemSubType
}

/**
 * Builder for defining an item subtype in a natural DSL style.
 *
 * Use type to specify the ItemType directly (not by ID).
 */
interface ItemSubTypeBuilder {
    var id: String
    var type: ItemType?
}

/**
 * Interface for the item subtype DSL implementation.
 * Now uses a builder lambda for a more natural DSL.
 */
interface ItemSubTypeDSL {
    fun itemSubType(init: ItemSubTypeBuilder.() -> Unit): IItemSubType
}

/**
 * Singleton provider for the ItemSubTypeDSL implementation.
 * The core game must set this before any mods are loaded.
 */
object ItemSubTypeDSLProvider {
    lateinit var instance: ItemSubTypeDSL
}

/**
 * DSL entrypoint for defining a new item subtype using a builder lambda.
 *
 * Example usage:
 * ```kotlin
 * itemSubType {
 *     id = Ids.ITEMSUBTYPE_SWORD
 *     typeId = Ids.ITEMTYPE_WEAPON
 * }
 * ```
 */
fun itemSubType(init: ItemSubTypeBuilder.() -> Unit): IItemSubType =
    ItemSubTypeDSLProvider.instance.itemSubType(init)

