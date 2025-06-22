package heroes.journey.entities.actions

import heroes.journey.registries.Registrable
import heroes.journey.registries.Registries.ActionManager

class ActionEntry(private val action: String, val input: Map<String, String>) : Registrable(action) {

    init {
        nameInternal = getAction().getTitle(input)
    }

    fun getAction(): Action {
        return ActionManager.get(action)!!
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ActionEntry) return false

        val thisAction: Action = getAction()
        val otherAction: Action = other.getAction()

        return if (thisAction.hasInput) thisAction.id == otherAction.id && input == other.input else thisAction.id == otherAction.id
    }

    override fun hashCode(): Int {
        val action: Action = getAction()

        // Start with the hash code of the action's ID, since it's always used.
        var result = action.id.hashCode()

        // If the action has input, the 'input' field was used in equals().
        // Therefore, it MUST also be included in the hashCode calculation.
        if (action.hasInput) {
            // This is a standard way to combine hash codes.
            // We use the safe-call operator `?.` and the elvis operator `?:`
            // to handle cases where 'input' might be null.
            result = 31 * result + (input.hashCode() ?: 0)
        }

        return result
    }

    override fun register(): Registrable {
        // Im aware this isnt great but it works
        error("Ephemeral Object, should not get registered.")
    }
}
