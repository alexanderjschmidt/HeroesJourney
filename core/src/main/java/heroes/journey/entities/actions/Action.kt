package heroes.journey.entities.actions

import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Table
import heroes.journey.entities.actions.inputs.ActionInput
import heroes.journey.entities.actions.results.AIOnSelectNotFound
import heroes.journey.entities.actions.results.ActionResult
import heroes.journey.registries.Registrable
import heroes.journey.registries.Registries
import heroes.journey.ui.HUD
import heroes.journey.ui.windows.InfoProvider

abstract class Action(
    id: String,
    name: String?,
    private val description: String = "",
    val isReturnsActionList: Boolean = false,
    cost: Cost? = null
) :
    Registrable(id, name), InfoProvider {

    protected val cost: Cost = cost ?: Cost()

    open fun internalRequirementsMet(input: ActionInput?): ShowAction {
        return ShowAction.YES
    }

    open fun internalOnHover(input: ActionInput?) {
    }

    abstract fun internalOnSelect(input: ActionInput): ActionResult?

    open fun internalOnSelectAI(input: ActionInput?): ActionResult {
        return AIOnSelectNotFound()
    }

    open fun requirementsMet(input: ActionInput): ShowAction? {
        return internalRequirementsMet(input).and(cost.requirementsMet(input))
    }

    fun onHover(input: ActionInput?) {
        HUD.get().cursor.setMapPointerLoc(null)
        internalOnHover(input)
    }

    open fun onSelect(input: ActionInput?, ai: Boolean): ActionResult? {
        if (input != null) cost.onUse(input)
        if (ai) {
            val aiResult = internalOnSelectAI(input)
            if (aiResult !is AIOnSelectNotFound) {
                return aiResult
            }
        }
        return internalOnSelect(input!!)
    }

    fun onSelect(input: ActionInput?): ActionResult? {
        return onSelect(input, false)
    }

    override fun register(): Action {
        return Registries.ActionManager.register(this)
    }

    override fun getTitle(): String {
        return toString()
    }

    override fun getDescription(): String {
        return description
    }

    override fun fillCustomContent(table: Table, skin: Skin) {
        table.add(cost.display).center().fill().expand()
    }
}
