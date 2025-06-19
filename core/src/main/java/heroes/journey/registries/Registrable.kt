package heroes.journey.registries

abstract class Registrable(val id: String, protected var nameInternal: String? = null) {

    open fun getName(): String {
        return nameInternal ?: id
    }

    override fun toString(): String {
        return id
    }

    abstract fun register(): Registrable

}
