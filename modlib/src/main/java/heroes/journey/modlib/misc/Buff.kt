package heroes.journey.modlib.misc

import heroes.journey.modlib.attributes.Attributes
import heroes.journey.modlib.registries.Registrable
import heroes.journey.modlib.registries.Registries

/**
 * A Buff, used for temporary stat modifications.
 * This is a simple data container with no complex functions.
 */
class Buff(
    id: String,
    val turnsBuffLasts: Int,
    val attributes: Attributes
) : Registrable(id) {

    override fun register(): Buff {
        Registries.BuffManager.register(this)
        return this
    }
}

/**
 * Builder for defining a buff in a natural DSL style.
 */
class BuffBuilder {
    var id: String = ""
    var turnsBuffLasts: Int = 1
    private var _attributes: Attributes? = null

    fun attributes(init: heroes.journey.modlib.attributes.AttributesBuilder.() -> Unit) {
        _attributes = heroes.journey.modlib.attributes.attributes(init)
    }

    fun builtAttributes(): Attributes = _attributes ?: heroes.journey.modlib.attributes.attributes {}
}

/**
 * DSL entrypoint for defining a buff.
 *
 * Example usage:
 * ```kotlin
 * buff {
 *     id = "rested"
 *     turnsBuffLasts = 3
 *     attributes {
 *         stat(Ids.STAT_BODY, 2)
 *     }
 * }
 * ```
 */
fun buff(init: BuffBuilder.() -> Unit): Buff {
    val builder = BuffBuilder()
    builder.init()
    return Buff(builder.id, builder.turnsBuffLasts, builder.builtAttributes())
}
