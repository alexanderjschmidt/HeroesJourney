package heroes.journey.modlib.misc

import heroes.journey.modlib.attributes.IAttributes
import heroes.journey.modlib.registries.IRegistrable

/**
 * Public interface for a Challenge, used for challenge definitions and logic.
 * Mods should only use this interface, not implementation classes.
 */
interface IChallenge : IRegistrable {

    /** The animation/render ID for the challenge. */
    val render: String

    /** The list of approach stat IDs for the challenge. */
    val approaches: List<String>

    /** The reward attributes granted by the challenge. */
    val reward: IAttributes
    override fun register(): IChallenge
}

/**
 * Interface for the challenge DSL implementation.
 */
interface ChallengeDSL {
    fun challenge(id: String, render: String, approaches: List<String>, reward: IAttributes): IChallenge
}

/**
 * Singleton provider for the ChallengeDSL implementation.
 * The core game must set this before any mods are loaded.
 */
object ChallengeDSLProvider {
    lateinit var instance: ChallengeDSL
}

/**
 * DSL entrypoint for mods. Always delegates to the core implementation.
 */
fun challenge(id: String, render: String, approaches: List<String>, reward: IAttributes): IChallenge =
    ChallengeDSLProvider.instance.challenge(id, render, approaches, reward)
