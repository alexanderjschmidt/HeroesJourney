package heroes.journey.modlib

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
 * Interface for the item subtype DSL implementation.
 */
interface ItemSubTypeDSL {
    fun itemSubType(id: String, parentType: ItemType): IItemSubType
}

/**
 * Singleton provider for the ItemSubTypeDSL implementation.
 * The core game must set this before any mods are loaded.
 */
object ItemSubTypeDSLProvider {
    lateinit var instance: ItemSubTypeDSL
}

/**
 * DSL entrypoint for mods. Always delegates to the core implementation.
 */
fun itemSubType(id: String, parentType: ItemType): IItemSubType =
    ItemSubTypeDSLProvider.instance.itemSubType(id, parentType)
