package heroes.journey.modlib.attributes

import heroes.journey.modlib.registries.IRegistrable

/**
 * Public interface for a Group, used for stat grouping and categorization.
 * Mods should only use this interface, not implementation classes.
 */
interface IGroup : IRegistrable {
    override fun register(): IGroup
}

/**
 * Builder for defining a group in a natural DSL style.
 */
interface GroupBuilder {
    var id: String
}

/**
 * Interface for the group DSL implementation.
 * Now uses a builder lambda for a more natural DSL.
 */
interface GroupDSL {
    fun group(init: GroupBuilder.() -> Unit): IGroup
}

/**
 * Singleton provider for the GroupDSL implementation.
 * The core game must set this before any mods are loaded.
 */
object GroupDSLProvider {
    lateinit var instance: GroupDSL
}

/**
 * DSL entrypoint for defining a group using a builder lambda.
 *
 * Example usage:
 * ```kotlin
 * group {
 *     id = Ids.GROUP_BODY
 * }
 * ```
 */
fun group(init: GroupBuilder.() -> Unit): IGroup = GroupDSLProvider.instance.group(init)
