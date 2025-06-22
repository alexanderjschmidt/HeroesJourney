package heroes.journey.entities.actions

import heroes.journey.GameState
import java.util.*

class ActionInput(val gameState: GameState, val entityId: UUID? = null, input: Map<String, String>) :
    HashMap<String, String>() {

    constructor(gameState: GameState) : this(gameState, null, hashMapOf())
    constructor(gameState: GameState, entityId: UUID) : this(gameState, entityId, hashMapOf())

    init {
        for ((key, value) in input) {
            this[key] = value
        }
    }

    fun getInput(): String? {
        return this["input"]
    }

    fun hasInput(): Boolean {
        return getInput() != null && getInput() != ""
    }

    fun getHashMapCopy(): HashMap<String, String> {
        val copy: HashMap<String, String> = HashMap()
        for ((key, value) in this) {
            copy[key] = value
        }
        return copy
    }
}
