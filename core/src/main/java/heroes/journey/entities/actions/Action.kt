package heroes.journey.entities.actions

import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Table
import heroes.journey.GameState
import heroes.journey.components.PossibleActionsComponent
import heroes.journey.modlib.Registrable
import heroes.journey.modlib.actions.IAction
import heroes.journey.modlib.actions.IActionContext
import heroes.journey.modlib.actions.ShowAction
import heroes.journey.modlib.actions.results.ActionResult
import heroes.journey.registries.Registries
import heroes.journey.ui.HUD
import heroes.journey.ui.windows.InfoProvider
import java.util.*

open class Action(
    id: String,
    override val isReturnsActionList: Boolean = false,
    override val requirementsMetFn: (IActionContext) -> ShowAction = { ShowAction.YES },
    override val onHoverFn: (IActionContext) -> Unit = {},
    override val onSelectFn: (IActionContext) -> ActionResult,
    override val inputDisplayNameFn: ((IActionContext) -> String)? = null,
    override val turnCooldown: Int = 0,
    override val factionCooldown: Boolean = false
) : Registrable(id), InfoProvider, IAction {

    val hasInput: Boolean
        get() = inputDisplayNameFn != null

    fun requirementsMet(input: IActionContext): ShowAction {
        val ctx = input as? ActionContext ?: error("Expected ActionContext")
        val cooldownComponent = getCooldownComponent(ctx)
        if (cooldownComponent != null && cooldownComponent.cooldowns.containsKey(id)) return ShowAction.GRAYED
        return requirementsMetFn(ctx)
    }

    fun onHover(input: IActionContext) {
        val ctx = input as? ActionContext ?: error("Expected ActionContext")
        HUD.get().cursor.setMapPointerLoc(null)
        onHoverFn(ctx)
    }

    open fun onSelect(input: IActionContext): ActionResult {
        val ctx = input as? ActionContext ?: error("Expected ActionContext")
        val cooldownComponent = getCooldownComponent(ctx)
        cooldownComponent?.cooldowns?.set(id, turnCooldown)
        val result: ActionResult = onSelectFn(ctx)
        return result
    }

    override fun getTitle(input: ActionContext): String {
        if (inputDisplayNameFn == null)
            return getName()
        val title: String? = inputDisplayNameFn?.let { it(input) }
        return title!!
    }

    override fun getDescription(input: Map<String, String>): String = getDescription()

    private fun getCooldownComponent(input: ActionContext): PossibleActionsComponent? {
        val cooldownComponent: PossibleActionsComponent?
        if (factionCooldown) {
            val faction = UUID.fromString(input["owner"])
            cooldownComponent = PossibleActionsComponent.get(input.gameState.world, faction)
        } else {
            cooldownComponent = PossibleActionsComponent.get(
                input.gameState.world,
                input.entityId
            )
        }
        return cooldownComponent
    }

    private var cooldown: Label? = null

    override fun fillCustomContent(table: Table, skin: Skin, input: Map<String, String>) {
        if (cooldown == null) {
            cooldown = Label("", skin)
        }
        val actionContext: ActionContext =
            ActionContext(GameState.global(), GameState.global().currentEntity, false)
        actionContext.putAll(input)
        val cooldownComponent = getCooldownComponent(
            actionContext
        )
        var cooldownVal = cooldownComponent?.cooldowns?.get(id)
        cooldownVal = if (cooldownVal == null) turnCooldown else (turnCooldown - cooldownVal - 1)
        cooldown!!.setText("$cooldownVal/$turnCooldown")
        table.add(cooldown).fill().row()
    }

    override fun register(): Action {
        return Registries.ActionManager.register(this) as Action
    }
}
