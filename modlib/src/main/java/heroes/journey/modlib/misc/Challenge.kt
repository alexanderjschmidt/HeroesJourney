package heroes.journey.modlib.misc

import heroes.journey.modlib.attributes.IAttributes
import heroes.journey.modlib.attributes.IStat
import heroes.journey.modlib.registries.IRegistrable


/**
Fibonacci * 10 with some rounding
1,  2,  3,  4,  5,   6,   7,   8,   9,   10,   11?
10, 20, 30, 50, 75, 125, 200, 350, 500, 1000, 1500
 */
val powerLevels = listOf(10, 20, 30, 50, 75, 125, 200, 350, 500, 1000, 1500)

/**
 * Public interface for a Challenge, used for challenge definitions and logic.
 * Mods should only use this interface, not implementation classes.
 *
 * Example usage:
 * ```kotlin
 * challenge {
 *     id = Ids.MY_CHALLENGE
 *     render = Ids.PLAYER_SPRITE
 *     tag(Ids.STAT_PHYSICAL)
 *     tag(Ids.STAT_SENTIENT)
 *     powerTier = 3 // Tier 3 challenge (30 power)
 *     rewards = attributes {
 *         stat(Ids.STAT_GOLD, 50)
 *         stat(Ids.STAT_XP, 100)
 *     }
 * }
 * ```
 */
interface IChallenge : IRegistrable {

    /** The animation/render ID for the challenge. */
    val render: String

    /** The stats that can be used to approach this challenge. */
    val stats: List<IStat>

    /** The power tier of the challenge (1-11). See powerLevels list for actual power values. */
    val powerTier: Int

    /** The rewards for completing this challenge. */
    val rewards: IAttributes

    override fun register(): IChallenge
}

/**
 * Builder for defining a challenge in a natural DSL style.
 */
interface ChallengeBuilder {
    var id: String
    var render: String
    var powerTier: Int
    var rewards: IAttributes?
    fun tag(vararg statIdsIn: String)
}

/**
 * Interface for the challenge DSL implementation.
 * Now uses a builder lambda for a more natural DSL.
 */
interface ChallengeDSL {
    fun challenge(init: ChallengeBuilder.() -> Unit): IChallenge
}

/**
 * Singleton provider for the ChallengeDSL implementation.
 * The core game must set this before any mods are loaded.
 */
object ChallengeDSLProvider {
    lateinit var instance: ChallengeDSL
}

/**
 * DSL entrypoint for defining a challenge using a builder lambda.
 *
 * Example usage:
 * ```kotlin
 * challenge {
 *     id = Ids.MY_CHALLENGE
 *     render = Ids.PLAYER_SPRITE
 *     tag(Ids.STAT_PHYSICAL)
 *     tag(Ids.STAT_SENTIENT)
 *     powerTier = 3 // Tier 3 challenge (30 power)
 *     rewards = attributes {
 *         stat(Ids.STAT_GOLD, 50)
 *         stat(Ids.STAT_XP, 100)
 *     }
 * }
 * ```
 */
fun challenge(init: ChallengeBuilder.() -> Unit): IChallenge = ChallengeDSLProvider.instance.challenge(init)
