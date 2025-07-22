package heroes.journey.modlib.actions

class ActionEntry(val actionId: String, val input: Map<String, String>) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ActionEntry) return false

        return actionId == other.actionId && input == other.input
    }

    override fun hashCode(): Int {

        // Start with the hash code of the action's ID, since it's always used.
        var result = actionId.hashCode()

        // This is a standard way to combine hash codes.
        // We use the safe-call operator `?.` and the elvis operator `?:`
        // to handle cases where 'input' might be null.
        result = (31 * result) + input.hashCode()

        return result
    }

    override fun toString(): String {
        return actionId
    }

}
