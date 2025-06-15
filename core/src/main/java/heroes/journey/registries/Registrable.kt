package heroes.journey.registries

import lombok.Getter

@Getter
abstract class Registrable(val id: String, private val name: String? = null) {

    open fun getName(): String {
        return name ?: id
    }

    override fun toString(): String {
        return id
    }

    abstract fun register(): Registrable

}
