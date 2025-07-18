package heroes.journey.modlib.actions

interface ActionResult

class ActionListResult(val list: List<ActionEntry>) : ActionResult {
    override fun toString(): String {
        return list.toString()
    }
}

class StringResult(private val result: String, val isEndTurn: Boolean = true) :
    ActionResult {
    override fun toString(): String {
        return result
    }
}

class NullResult : ActionResult

class EndTurnResult : ActionResult
