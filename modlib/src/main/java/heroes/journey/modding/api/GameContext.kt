package heroes.journey.modding.api

import heroes.journey.modding.api.gamestate.GameState
import heroes.journey.modding.api.registration.Registries

class GameContext(
    val gameState: GameState, val registries: Registries
)
