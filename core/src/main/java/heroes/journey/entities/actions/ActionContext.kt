package heroes.journey.entities.actions

import heroes.journey.GameState
import heroes.journey.modlib.actions.IActionContext
import java.util.*

class ActionContext(
    private val innerGameState: GameState,
    entityId: UUID? = null,
    input: Map<String, String>
) :
    IActionContext(innerGameState, entityId, input) {

    constructor(innerGameState: GameState) : this(innerGameState, null, hashMapOf())
    constructor(innerGameState: GameState, entityId: UUID) : this(innerGameState, entityId, hashMapOf())

    override val gameState: GameState
        get() = innerGameState
}
