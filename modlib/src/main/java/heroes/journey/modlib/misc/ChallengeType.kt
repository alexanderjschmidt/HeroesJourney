package heroes.journey.modlib.misc

import heroes.journey.modlib.registries.IRegistrable

/**
 * Public interface for a ChallengeType, used for defining challenge categories and their approaches.
 * Mods should only use this interface, not implementation classes.
 */
interface IChallengeType : IRegistrable {
    /** The primary approach for this challenge type. */
    val primaryApproach: IApproach
    
    /** The secondary approaches available for this challenge type. */
    val secondaryApproaches: List<IApproach>
    
    /**
     * Get all available approaches for this challenge type.
     * Returns the primary approach plus all secondary approaches.
     */
    fun getApproaches(): List<IApproach>
    
    override fun register(): IChallengeType
}

/**
 * Public interface for an Approach, used for challenge approach definitions.
 * Mods should only use this interface, not implementation classes.
 */
interface IApproach : IRegistrable {
    /** The base stat ID this approach is primarily associated with. */
    val baseStatId: String
    
    /** The secondary stat ID this approach is associated with (nullable). */
    val secondaryStatId: String?
    
    override fun register(): IApproach
}

/**
 * Builder for defining a challenge type in a natural DSL style.
 */
interface ChallengeTypeBuilder {
    var id: String
    var primaryApproachId: String
    fun secondaryApproach(approachId: String)
}

/**
 * Interface for the challenge type DSL implementation.
 * Now uses a builder lambda for a more natural DSL.
 */
interface ChallengeTypeDSL {
    fun challengeType(init: ChallengeTypeBuilder.() -> Unit): IChallengeType
}

/**
 * Singleton provider for the ChallengeTypeDSL implementation.
 * The core game must set this before any mods are loaded.
 */
object ChallengeTypeDSLProvider {
    lateinit var instance: ChallengeTypeDSL
}

/**
 * DSL entrypoint for defining a challenge type using a builder lambda.
 *
 * Example usage:
 * ```kotlin
 * challengeType {
 *     id = Ids.CHALLENGE_TYPE_BODY
 *     primaryApproachId = Ids.APPROACH_MIGHT
 *     secondaryApproach(Ids.APPROACH_SKILL)
 *     secondaryApproach(Ids.APPROACH_EMPOWERMENT)
 *     secondaryApproach(Ids.APPROACH_CHIVALRY)
 * }
 * ```
 */
fun challengeType(init: ChallengeTypeBuilder.() -> Unit): IChallengeType = ChallengeTypeDSLProvider.instance.challengeType(init)

/**
 * Builder for defining an approach in a natural DSL style.
 */
interface ApproachBuilder {
    var id: String
    var baseStatId: String
    var secondaryStatId: String?
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
 *     baseStatId = Ids.STAT_BODY
 *     secondaryStatId = Ids.STAT_BODY
 * }
 * ```
 */
fun approach(init: ApproachBuilder.() -> Unit): IApproach = ApproachDSLProvider.instance.approach(init) 