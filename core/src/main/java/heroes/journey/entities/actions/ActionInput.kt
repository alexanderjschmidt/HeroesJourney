package heroes.journey.entities.actions

import heroes.journey.GameState
import java.util.*

open class ActionInput(val gameState: GameState, val entityId: UUID? = null, val input: String? = null) {
    constructor(gameState: GameState) : this(gameState, null, null)
    constructor(gameState: GameState, entityId: UUID?) : this(gameState, entityId, null)

    fun hasInput(): Boolean {
        return input != null && input != ""
    }
}
