package heroes.journey.modlib.misc

import heroes.journey.modlib.attributes.IStat
import heroes.journey.modlib.registries.IRegistrable

/**
 * Public interface for a Challenge, used for challenge definitions and logic.
 * Mods should only use this interface, not implementation classes.
 */
interface IChallenge : IRegistrable {

    /** The animation/render ID for the challenge. */
    val render: String

    /** The stats that can be used to approach this challenge. */
    val stats: List<IStat>
    
    override fun register(): IChallenge
}

/**
 * Builder for defining a challenge in a natural DSL style.
 */
interface ChallengeBuilder {
    var id: String
    var render: String
    fun stat(statId: String)
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
 *     stat(Ids.STAT_BODY)
 *     stat(Ids.STAT_MIND)
 * }
 * ```
 */
fun challenge(init: ChallengeBuilder.() -> Unit): IChallenge = ChallengeDSLProvider.instance.challenge(init)
