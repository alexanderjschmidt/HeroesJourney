package heroes.journey.registries

import heroes.journey.utils.Lang

abstract class Registrable(val id: String) {
    open fun getName(): String {
        return Lang.get("${id}_name")
    }
    open fun getDescription(): String {
        return Lang.get("${id}_description")
    }
    override fun toString(): String {
        return id
    }
    abstract fun register(): Registrable
}
