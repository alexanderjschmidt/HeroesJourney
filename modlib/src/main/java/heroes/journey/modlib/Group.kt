package heroes.journey.modlib

/**
 * Public interface for a Group, used for stat grouping and categorization.
 * Mods should only use this interface, not implementation classes.
 */
interface IGroup : IRegistrable {
    override fun register(): IGroup
}

/**
 * Interface for the group DSL implementation.
 */
interface GroupDSL {
    fun group(id: String): IGroup
}

/**
 * Singleton provider for the GroupDSL implementation.
 * The core game must set this before any mods are loaded.
 */
object GroupDSLProvider {
    lateinit var instance: GroupDSL
}

/**
 * DSL entrypoint for mods. Always delegates to the core implementation.
 */
fun group(id: String): IGroup = GroupDSLProvider.instance.group(id)
