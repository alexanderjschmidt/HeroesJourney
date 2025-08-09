package heroes.journey.ui.infoproviders

import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Table
import heroes.journey.GameState
import heroes.journey.entities.actions.Action
import heroes.journey.entities.actions.ActionContext
import heroes.journey.entities.actions.ActionUtils

class ActionInfoProvider(private val action: Action) : RegistrableInfoProvider(action) {

    private var cooldown: Label? = null
    private var costLabel: Label? = null

    override fun fillCustomContent(table: Table, skin: Skin, input: Map<String, String>) {
        // Use custom InfoProvider if available and it implements UIInfoProvider
        val actionContext = ActionContext(GameState.global(), null, false, input)
        val customProvider = action.customInfoProviderFn?.invoke(actionContext)
        if (customProvider is UIInfoProvider) {
            customProvider.fillCustomContent(table, skin, input)
            return
        }

        // Display cost if present
        val cost = action.cost
        if (cost != null) {
            if (costLabel == null) {
                costLabel = Label("", skin)
            }
            val costText = StringBuilder("Cost: ")
            var first = true
            for ((stat, amount) in cost) {
                if (!first) costText.append(", ")
                costText.append("${stat.getName()}: $amount")
                first = false
            }
            costLabel!!.setText(costText.toString())
            table.add(costLabel).fill().row()
        }

        // Default implementation for cooldown display
        if (action.turnCooldown != 0) {
            if (cooldown == null) {
                cooldown = Label("", skin)
            }
            val cooldownComponent = ActionUtils.getCooldownComponent(action, actionContext)
            var cooldownVal = cooldownComponent?.cooldowns?.get(action.id)
            cooldownVal = if (cooldownVal == null) action.turnCooldown else (action.turnCooldown - cooldownVal - 1)
            cooldown!!.setText("Cooldown: $cooldownVal/${action.turnCooldown}")
            table.add(cooldown).fill().row()
        }
    }
}
