package heroes.journey.modlib.attributes

import heroes.journey.modlib.registries.Registrable
import heroes.journey.modlib.registries.Registries

/**
 * A Group, used for stat grouping and categorization.
 * This is a simple data container with no complex functions.
 */
class Group(id: String) : Registrable(id) {

    override fun register(): Group {
        Registries.GroupManager.register(this)
        return this
    }
}

/**
 * Builder for defining a group in a natural DSL style.
 */
class GroupBuilder {
    var id: String = ""
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
fun group(init: GroupBuilder.() -> Unit): Group {
    val builder = GroupBuilder()
    builder.init()
    return Group(builder.id)
}
