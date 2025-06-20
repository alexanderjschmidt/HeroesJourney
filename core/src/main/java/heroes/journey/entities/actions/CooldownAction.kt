package heroes.journey.entities.actions

import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Table
import heroes.journey.GameState
import heroes.journey.components.PossibleActionsComponent
import heroes.journey.entities.actions.results.AIOnSelectNotFound
import heroes.journey.entities.actions.results.ActionResult
import heroes.journey.registries.Registries
import java.util.*

open class CooldownAction(
    id: String,
    name: String?,
    description: String = "",
    isReturnsActionList: Boolean = false,
    cost: Cost = Cost(),
    requirementsMetFn: (ActionInput) -> ShowAction = { ShowAction.YES },
    onHoverFn: (ActionInput) -> Unit = {},
    onSelectFn: (ActionInput) -> ActionResult,
    onSelectAIFn: (ActionInput) -> ActionResult = { AIOnSelectNotFound() },
    inputDisplayNameFn: ((String) -> String)? = null,
    private val turnCooldown: Int,
    private val factionCooldown: Boolean
) : Action(
    id,
    name,
    description,
    isReturnsActionList,
    cost,
    requirementsMetFn,
    onHoverFn,
    onSelectFn,
    onSelectAIFn,
    inputDisplayNameFn
) {

    override fun requirementsMet(input: ActionInput): ShowAction {
        val cooldownComponent = getCooldownComponent(input)
        if (cooldownComponent.cooldowns.containsKey(id)) return ShowAction.GRAYED
        return super.requirementsMet(input)
    }

    override fun onSelect(input: ActionInput, ai: Boolean): ActionResult? {
        val cooldownComponent = getCooldownComponent(input)
        cooldownComponent.cooldowns[id] = turnCooldown
        return super.onSelect(input, ai)
    }

    private fun getCooldownComponent(input: ActionInput): PossibleActionsComponent {
        val cooldownComponent: PossibleActionsComponent
        if (factionCooldown) {
            val faction = UUID.fromString(input.input)
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

    override fun fillCustomContent(table: Table, skin: Skin, input: String) {
        if (cooldown == null) {
            cooldown = Label("", skin)
        }
        val cooldownComponent = getCooldownComponent(
            ActionInput(GameState.global(), GameState.global().currentEntity, input)
        )
        var cooldownVal = cooldownComponent.cooldowns[id]
        cooldownVal = if (cooldownVal == null) turnCooldown else (turnCooldown - cooldownVal - 1)
        cooldown!!.setText("$cooldownVal/$turnCooldown")
        table.add(cooldown).fill().row()
        super.fillCustomContent(table, skin, input)
    }

    override fun register(): CooldownAction {
        return Registries.ActionManager.register(this) as CooldownAction
    }
}
