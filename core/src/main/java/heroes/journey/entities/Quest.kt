package heroes.journey.entities

import heroes.journey.components.character.PlayerComponent
import heroes.journey.entities.actions.ActionInput
import heroes.journey.registries.Registrable
import heroes.journey.registries.Registries
import java.util.*
import java.util.function.Consumer
import java.util.function.Predicate

class Quest(
    id: String,
    name: String?,
    private val onComplete: Consumer<ActionInput>,
    private val isComplete: Predicate<ActionInput>,
    private val fameReward: Int
) : Registrable(id, name) {
    fun onComplete(input: ActionInput) {
        val playerComponent = PlayerComponent.get(
            input.gameState.getWorld(),
            input.entityId
        )
        playerComponent?.fame(playerComponent.fame() + fameReward)
        onComplete.accept(input)
    }

    fun isComplete(input: ActionInput): Boolean {
        return isComplete.test(input)
    }

    override fun register(): Quest {
        return Registries.QuestManager.register(this)
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o !is Quest) return false
        return id == o.id
    }

    override fun hashCode(): Int {
        return Objects.hash(id)
    }
}
