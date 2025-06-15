package heroes.journey.entities.actions

import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Table
import heroes.journey.GameState
import heroes.journey.components.PossibleActionsComponent
import heroes.journey.entities.actions.inputs.ActionInput
import heroes.journey.entities.actions.results.ActionResult
import heroes.journey.initializers.utils.Utils
import heroes.journey.registries.Registries
import lombok.Getter

abstract class CooldownAction(
    id: String,
    name: String?,
    description: String,
    returnsActionList: Boolean,
    cost: Cost?,
    @field:Getter private val turnCooldown: Int,
    private val factionCooldown: Boolean
) :
    Action(id, name, description, returnsActionList, cost) {
    constructor(id: String, name: String?, turnCooldown: Int) : this(
        id,
        name,
        "",
        false,
        null,
        turnCooldown,
        false
    )

    override fun requirementsMet(input: ActionInput): ShowAction? {
        val cooldownComponent = getCooldownComponent(input)
        if (cooldownComponent.cooldowns.containsKey(id)) return ShowAction.GRAYED
        return super.internalRequirementsMet(input)
    }

    override fun onSelect(input: ActionInput?, ai: Boolean): ActionResult? {
        if (input == null) return null
        val cooldownComponent = getCooldownComponent(input)
        cooldownComponent.cooldowns[id] = turnCooldown
        return super.onSelect(input, ai)
    }

    private fun getCooldownComponent(input: ActionInput): PossibleActionsComponent {
        val cooldownComponent: PossibleActionsComponent
        if (factionCooldown) {
            val faction = Utils.getLocation(input)
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

    override fun fillCustomContent(table: Table, skin: Skin) {
        if (cooldown == null) {
            cooldown = Label("", skin)
        }
        val cooldownComponent = getCooldownComponent(
            ActionInput(GameState.global(), GameState.global().currentEntity)
        )
        var cooldownVal = cooldownComponent.cooldowns[id]
        cooldownVal = if (cooldownVal == null) turnCooldown else (turnCooldown - cooldownVal - 1)
        cooldown!!.setText("$cooldownVal/$turnCooldown")
        table.add(cooldown).fill().row()
        super.fillCustomContent(table, skin)
    }

    override fun register(): CooldownAction {
        return Registries.ActionManager.register(this) as CooldownAction
    }
}
