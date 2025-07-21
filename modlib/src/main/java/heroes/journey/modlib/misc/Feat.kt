package heroes.journey.modlib.misc

import heroes.journey.modlib.actions.IActionContext
import heroes.journey.modlib.registries.IRegistrable

/**
 * Public interface for a Feat, used for character abilities and specializations.
 * Mods should only use this interface, not implementation classes.
 */
interface IFeat : IRegistrable {
    /** The type of feat (ACTIVE or PASSIVE). */
    val featType: FeatType
    
    /** Function called when the feat is earned. */
    val onEarnFn: (IActionContext) -> Unit
    
    /** Function called when the feat is lost. */
    val onLoseFn: (IActionContext) -> Unit
    
    override fun register(): IFeat
}

/**
 * Builder for defining a feat in a natural DSL style.
 */
interface FeatBuilder {
    var id: String
    var featType: FeatType
    var onEarnFn: (IActionContext) -> Unit
    var onLoseFn: (IActionContext) -> Unit
}

/**
 * Interface for the feat DSL implementation.
 * Now uses a builder lambda for a more natural DSL.
 */
interface FeatDSL {
    fun feat(init: FeatBuilder.() -> Unit): IFeat
}

/**
 * Singleton provider for the FeatDSL implementation.
 * The core game must set this before any mods are loaded.
 */
object FeatDSLProvider {
    lateinit var instance: FeatDSL
}

/**
 * DSL entrypoint for defining a feat using a builder lambda.
 *
 * Example usage:
 * ```kotlin
 * feat {
 *     id = Ids.FEAT_WEAPON_MASTERY
 *     featType = FeatType.PASSIVE
 *     onEarnFn = { context -> 
 *         // Add weapon mastery bonus
 *     }
 *     onLoseFn = { context -> 
 *         // Remove weapon mastery bonus
 *     }
 * }
 * ```
 */
fun feat(init: FeatBuilder.() -> Unit): IFeat = FeatDSLProvider.instance.feat(init) 