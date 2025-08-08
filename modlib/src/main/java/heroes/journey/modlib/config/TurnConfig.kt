package heroes.journey.modlib.config

import heroes.journey.modlib.actions.IActionContext
import heroes.journey.modlib.registries.Registrable
import heroes.journey.modlib.registries.Registries

/**
 * A TurnConfig, used for turn-based game progression.
 * This is a simple data container with configuration data.
 */
class TurnConfig(
    id: String,
    val turnNumber: Int,
    val applyConfig: (IActionContext) -> Unit
) : Registrable(id) {

    override fun register(): TurnConfig {
        Registries.TurnConfigManager.register(this)
        return this
    }
}

/**
 * Builder for defining turn configuration in a natural DSL style.
 */
class TurnConfigBuilder {
    var id: String = ""
    var turnNumber: Int = 1
    var applyConfig: (IActionContext) -> Unit = {}
}

/**
 * DSL entrypoint for defining turn configuration.
 *
 * Example usage:
 * ```kotlin
 * turnConfig {
 *     id = "turn_5_power_increase"
 *     turnNumber = 5
 *     description = "Increase challenge power tiers to 1-3"
 *     applyConfig = { context ->
 *         // Modify game state here
 *         context.setMinChallengePowerTier(1)
 *         context.setMaxChallengePowerTier(3)
 *         context.setAmbientLighting(0.9f)
 *     }
 * }
 * ```
 */
fun turnConfig(init: TurnConfigBuilder.() -> Unit): TurnConfig {
    val builder = TurnConfigBuilder()
    builder.init()
    return TurnConfig(builder.id, builder.turnNumber, builder.applyConfig)
}
