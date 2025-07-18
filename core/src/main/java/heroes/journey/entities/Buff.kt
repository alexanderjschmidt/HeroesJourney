package heroes.journey.entities

import heroes.journey.entities.tagging.Attributes
import heroes.journey.modlib.misc.IBuff
import heroes.journey.modlib.registries.Registrable
import heroes.journey.mods.Registries
import lombok.Getter

@Getter
class Buff(
    id: String,
    override val turnsBuffLasts: Int,
    override val attributes: Attributes
) :
    Registrable(id), IBuff {
    override fun register(): Buff {
        return Registries.BuffManager.register(this)
    }
}
