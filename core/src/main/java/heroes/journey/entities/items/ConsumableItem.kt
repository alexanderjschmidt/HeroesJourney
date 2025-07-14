package heroes.journey.entities.items

import heroes.journey.entities.actions.ActionInput
import heroes.journey.entities.tagging.Attributes
import heroes.journey.registries.Registries.ItemManager
import java.util.function.Consumer

class ConsumableItem(
    id: String,
    subType: ItemSubType,
    weight: Int,
    attributes: Attributes = Attributes(),
    protected val onConsume: Consumer<ActionInput>
) : Item(id, subType, weight, attributes) {

    fun consume(input: ActionInput) {
        onConsume.accept(input)
    }

    override fun register(): ConsumableItem = this.apply { ItemManager.register(this) }
}
