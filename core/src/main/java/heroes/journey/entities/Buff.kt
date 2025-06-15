package heroes.journey.entities

import heroes.journey.registries.Registrable
import heroes.journey.registries.Registries
import lombok.Getter

@Getter
class Buff(id: String, name: String?, private val turnsBuffLasts: Int, private val timesBuffCanBeUsed: Int) :
  Registrable(id, name) {
  override fun register(): Buff {
    return Registries.BuffManager.register(this)
  }
}
