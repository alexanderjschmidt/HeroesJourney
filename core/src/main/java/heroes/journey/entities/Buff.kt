package heroes.journey.entities

import heroes.journey.registries.Registrable
import heroes.journey.registries.Registries
import lombok.Getter

@Getter
class Buff(id: String, name: String?, val turnsBuffLasts: Int, val timesBuffCanBeUsed: Int) :
    Registrable(id, name) {
    override fun register(): Buff {
        return Registries.BuffManager.register(this)
    }
}
