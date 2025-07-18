package heroes.journey.modlib.items

import heroes.journey.modlib.attributes.IAttributes
import heroes.journey.modlib.attributes.attributes
import heroes.journey.modlib.registries.IRegistrable

/**
 * Public interface for an Item, used for inventory and world objects.
 * Mods should only use this interface, not implementation classes.
 *
 * Example usage:
 * ```kotlin
 * item(id = Ids.MY_ITEM, subType = ItemType.Misc, weight = 1).register()
 * ```
 */
interface IItem : IRegistrable {

    /** The item subtype (category). */
    val subType: IItemSubType

    /** The weight of the item. */
    val weight: Int

    /** The attributes of the item. */
    val attributes: IAttributes
    override fun register(): IItem
}

/**
 * Interface for the item DSL implementation.
 */
interface ItemDSL {
    fun item(id: String, subType: IItemSubType, weight: Int, attributes: IAttributes = attributes()): IItem
}

/**
 * Singleton provider for the ItemDSL implementation.
 * The core game must set this before any mods are loaded.
 */
object ItemDSLProvider {
    lateinit var instance: ItemDSL
}

/**
 * DSL entrypoint for defining a new item.
 * @param id The unique item ID.
 * @param subType The item subtype/category.
 * @param weight The item weight.
 * @param attributes The item's attributes (optional).
 * @return The created [IItem] instance.
 *
 * Example usage:
 * ```kotlin
 * item(id = Ids.MY_ITEM, subType = ItemType.Misc, weight = 1).register()
 * ```
 */
fun item(id: String, subType: IItemSubType, weight: Int, attributes: IAttributes = attributes()): IItem =
    ItemDSLProvider.instance.item(id, subType, weight, attributes)
