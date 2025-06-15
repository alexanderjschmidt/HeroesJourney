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

open class Action(
    id: String,
    name: String? = null,
    private val description: String = "",
    val isReturnsActionList: Boolean = false,
    val cost: Cost = Cost(),
    val requirementsMetFn: (ActionInput?) -> ShowAction = { ShowAction.YES },
    val onHoverFn: (ActionInput?) -> Unit = {},
    val onSelectFn: (ActionInput) -> ActionResult,
    val onSelectAIFn: (ActionInput?) -> ActionResult = { AIOnSelectNotFound() }
) : Registrable(id, name), InfoProvider {

    open fun requirementsMet(input: ActionInput): ShowAction {
        return requirementsMetFn(input).and(cost.requirementsMet(input))
    }

    fun onHover(input: ActionInput?) {
        HUD.get().cursor.setMapPointerLoc(null)
        onHoverFn(input)
    }

    open fun onSelect(input: ActionInput?, ai: Boolean = false): ActionResult? {
        if (input != null) cost.onUse(input)
        if (ai) {
            val aiResult = onSelectAIFn(input)
            if (aiResult !is AIOnSelectNotFound) {
                return aiResult
            }
        }
        return onSelectFn(input!!)
    }

    override fun getTitle(): String = getName()
    override fun getDescription(): String = description

    override fun fillCustomContent(table: Table, skin: Skin) {
        table.add(cost.display).center().fill().expand()
    }

    override fun register(): Action {
        return Registries.ActionManager.register(this)
    }
}
