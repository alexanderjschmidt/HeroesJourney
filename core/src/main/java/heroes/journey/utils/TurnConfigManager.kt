package heroes.journey.utils

import heroes.journey.GameState
import heroes.journey.modlib.config.TurnConfig
import heroes.journey.modlib.registries.Registries

/**
 * Utility class for managing turn-based configurations.
 * Provides methods to get the appropriate configuration for a given turn.
 */
object TurnConfigManager {

    /**
     * Get the turn configuration for a specific turn number.
     * Returns the configuration with the highest turn number that is <= the current turn.
     * If no configuration is found, returns null.
     */
    fun getConfigForTurn(turnNumber: Int): TurnConfig? {
        val configs = Registries.TurnConfigManager.values.toList()
            .filter { it.turnNumber <= turnNumber }
            .sortedByDescending { it.turnNumber }

        return configs.firstOrNull()
    }

    /**
     * Apply all turn configurations for a specific turn.
     * This will execute all configuration functions that apply to the given turn.
     */
    fun applyTurnConfigurations(turnNumber: Int) {
        val configs = Registries.TurnConfigManager.values.toList()
            .filter { it.turnNumber == turnNumber }
            .sortedBy { it.turnNumber }

        for (config in configs) {
            val gameState = GameState.global()
            val context = heroes.journey.entities.actions.ActionContext(gameState, false)
            config.applyConfig(context)
        }
    }

    /**
     * Get all available turn configurations.
     */
    fun getAllConfigs(): List<TurnConfig> {
        return Registries.TurnConfigManager.values.toList().sortedBy { it.turnNumber }
    }

    /**
     * Get configurations that apply to a specific turn.
     */
    fun getConfigsForTurn(turnNumber: Int): List<TurnConfig> {
        return Registries.TurnConfigManager.values.toList()
            .filter { it.turnNumber == turnNumber }
            .sortedBy { it.turnNumber }
    }
}
