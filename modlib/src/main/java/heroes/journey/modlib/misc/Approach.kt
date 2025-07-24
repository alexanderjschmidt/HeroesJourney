package heroes.journey.modlib.misc

import heroes.journey.modlib.attributes.IAttributes
import heroes.journey.modlib.attributes.IStat
import heroes.journey.modlib.registries.IRegistrable

/**
 * Public interface for an Approach, used for challenge approach definitions.
 * Mods should only use this interface, not implementation classes.
 */
interface IApproach : IRegistrable {
    val stats: List<IStat>

    val requiredAllTags: List<IStat>
    val requiredAnyTags: List<IStat>
    val forbiddenTags: List<IStat>

    /** The cost attributes required to use this approach. */
    val cost: IAttributes?

    override fun register(): IApproach
}

/**
 * Builder for defining an approach in a natural DSL style.
 */
interface ApproachBuilder {
    var id: String
    var cost: IAttributes?
    fun stat(vararg statIds: String)
    fun requiresAll(vararg tags: String)
    fun requiresAny(vararg tags: String)
    fun forbids(vararg tags: String)
}

/**
 * Interface for the approach DSL implementation.
 * Now uses a builder lambda for a more natural DSL.
 */
interface ApproachDSL {
    fun approach(init: ApproachBuilder.() -> Unit): IApproach
}

/**
 * Singleton provider for the ApproachDSL implementation.
 * The core game must set this before any mods are loaded.
 */
object ApproachDSLProvider {
    lateinit var instance: ApproachDSL
}

/**
 * DSL entrypoint for defining an approach using a builder lambda.
 *
 * Example usage:
 * ```kotlin
 * approach {
 *     id = Ids.APPROACH_MIGHT
 *     stat(Ids.STAT_BODY)
 *     stat(Ids.STAT_MIND)
 *     cost = attributes {
 *         stat(Ids.STAT_STAMINA, 2)
 *     }
 * }
 * ```
 */
fun approach(init: ApproachBuilder.() -> Unit): IApproach = ApproachDSLProvider.instance.approach(init)
