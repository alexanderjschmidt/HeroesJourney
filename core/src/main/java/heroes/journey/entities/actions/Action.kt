package heroes.journey.entities.actions

import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Table
import heroes.journey.GameState
import heroes.journey.components.PossibleActionsComponent
import heroes.journey.modlib.actions.ShowAction
import heroes.journey.modlib.actions.results.ActionResult
import heroes.journey.registries.Registrable
import heroes.journey.registries.Registries
import heroes.journey.ui.HUD
import heroes.journey.ui.windows.InfoProvider
import java.util.*

open class Action(
    id: String,
    val isReturnsActionList: Boolean = false,
    private val requirementsMetFn: (ActionContext) -> ShowAction = { ShowAction.YES },
    private val onHoverFn: (ActionContext) -> Unit = {},
    private val onSelectFn: (ActionContext) -> ActionResult,
    private val inputDisplayNameFn: ((Map<String, String>) -> String)? = null,
    private val turnCooldown: Int = 0,
    private val factionCooldown: Boolean = false
) : Registrable(id), InfoProvider {

    val hasInput: Boolean
        get() = inputDisplayNameFn != null

    open fun requirementsMet(input: ActionContext): ShowAction {
        val cooldownComponent = getCooldownComponent(input)
        if (cooldownComponent != null && cooldownComponent.cooldowns.containsKey(id)) return ShowAction.GRAYED
        return requirementsMetFn(input)
    }

    fun onHover(input: ActionContext) {
        HUD.get().cursor.setMapPointerLoc(null)
        onHoverFn(input)
    }

    open fun onSelect(input: ActionContext): ActionResult? {
        val cooldownComponent = getCooldownComponent(input)
        cooldownComponent?.cooldowns?.set(id, turnCooldown)
        val result: ActionResult = onSelectFn(input)
        return result
    }

    override fun getTitle(input: Map<String, String>): String {
        if (inputDisplayNameFn == null)
            return getName()
        val title: String = inputDisplayNameFn.invoke(input)
        return title
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
