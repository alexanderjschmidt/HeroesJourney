package heroes.journey.modlib.actions

import heroes.journey.modlib.IGameState
import java.util.*

abstract class IActionContext(
    open val gameState: IGameState,
    val entityId: UUID? = null,
    input: Map<String, String>
) : HashMap<String, String>() {

    init {
        for ((key, value) in input) {
            this[key] = value
        }
    }

    fun getHashMapCopy(): HashMap<String, String> {
        val copy: HashMap<String, String> = HashMap()
        for ((key, value) in this) {
            copy[key] = value
        }
        return copy
    }
}
