package heroes.journey.registries

import lombok.Getter

@Getter
abstract class Registrable(val id: String, private val name: String? = null) {

    override fun toString(): String {
        return name ?: id
    }

    abstract fun register(): Registrable

}
