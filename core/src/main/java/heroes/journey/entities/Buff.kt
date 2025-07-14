package heroes.journey.entities

import heroes.journey.entities.tagging.Attributes
import heroes.journey.registries.Registrable
import heroes.journey.registries.Registries
import lombok.Getter

@Getter
class Buff(
    id: String,
    val turnsBuffLasts: Int,
    val attributes: Attributes
) :
    Registrable(id) {
    override fun register(): Buff {
        return Registries.BuffManager.register(this)
    }
}
