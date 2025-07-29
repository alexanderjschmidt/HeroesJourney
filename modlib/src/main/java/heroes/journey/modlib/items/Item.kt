package heroes.journey.modlib.items

import heroes.journey.modlib.attributes.IAttributes
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
    val subType: ItemSubType

    /** The weight of the item. */
    val weight: Int

    /** The attributes of the item. */
    val attributes: IAttributes
    override fun register(): IItem
}

/**
 * Builder for defining an item in a natural DSL style.
 *
 * Instead of referencing an ItemSubType directly, use subTypeId to specify the ID.
 */
interface ItemBuilder {
    var id: String
    var subTypeId: String?
    var weight: Int
    var attributes: IAttributes
    fun attribute(statId: String, value: Int)
}

/**
 * Interface for the item DSL implementation.
 * Now uses a builder lambda for a more natural DSL.
 */
interface ItemDSL {
    fun item(init: ItemBuilder.() -> Unit): IItem
}

/**
 * Singleton provider for the ItemDSL implementation.
 * The core game must set this before any mods are loaded.
 */
object ItemDSLProvider {
    lateinit var instance: ItemDSL
}

/**
 * DSL entrypoint for defining a new item using a builder lambda.
 *
 * Example usage:
 * ```kotlin
 * item {
 *     id = Ids.MY_ITEM
 *     subType = ItemType.Misc
 *     weight = 1
 *     attribute(Ids.STAT_BODY, 3)
 * }
 * ```
 */
fun item(init: ItemBuilder.() -> Unit): IItem = ItemDSLProvider.instance.item(init)
