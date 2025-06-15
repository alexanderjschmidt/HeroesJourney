package heroes.journey.entities.actions.inputs

import heroes.journey.GameState
import java.util.*

class TargetInput<I>(gameState: GameState, entityId: UUID?, val input: I) : ActionInput(gameState, entityId) {
    override fun toString(): String {
        return input.toString()
    }
}
