package heroes.journey.entities.actions

import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Table
import heroes.journey.entities.actions.results.AIOnSelectNotFound
import heroes.journey.entities.actions.results.ActionResult
import heroes.journey.registries.Registrable
import heroes.journey.registries.Registries
import heroes.journey.ui.HUD
import heroes.journey.ui.windows.InfoProvider

open class Action(
    id: String,
    name: String? = null,
    private val description: String = "",
    val isReturnsActionList: Boolean = false,
    val cost: Cost = Cost(),
    private val requirementsMetFn: (ActionInput) -> ShowAction = { ShowAction.YES },
    private val onHoverFn: (ActionInput) -> Unit = {},
    private val onSelectFn: (ActionInput) -> ActionResult,
    private val onSelectAIFn: (ActionInput) -> ActionResult = { AIOnSelectNotFound() },
    private val inputDisplayNameFn: ((String) -> String)? = null,
) : Registrable(id, name), InfoProvider {

    val hasInput: Boolean
        get() = inputDisplayNameFn != null

    open fun requirementsMet(input: ActionInput): ShowAction {
        return requirementsMetFn(input).and(cost.requirementsMet(input))
    }

    fun onHover(input: ActionInput) {
        HUD.get().cursor.setMapPointerLoc(null)
        onHoverFn(input)
    }

    open fun onSelect(input: ActionInput, ai: Boolean = false): ActionResult? {
        cost.onUse(input)
        if (ai) {
            val aiResult = onSelectAIFn(input)
            if (aiResult !is AIOnSelectNotFound) {
                return aiResult
            }
        }
        return onSelectFn(input)
    }

    override fun getTitle(input: String): String {
        if (inputDisplayNameFn == null)
            return getName()
        return inputDisplayNameFn.invoke(input)
    }

    override fun getDescription(input: String): String = description

    override fun fillCustomContent(table: Table, skin: Skin) {
        table.add(cost.display).center().fill().expand()
    }

    override fun register(): Action {
        return Registries.ActionManager.register(this) as Action
    }
}
