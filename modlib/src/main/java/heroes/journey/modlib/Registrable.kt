package heroes.journey.modlib

abstract class Registrable(val id: String) {
    companion object {
        lateinit var lang: ILang
    }

    init {
        require(id.isNotBlank()) { "Renderable id must not be blank or empty" }
    }

    open fun getName(): String {
        return lang.get("${id}_name")
    }

    open fun getDescription(): String {
        return lang.get("${id}_description")
    }

    override fun toString(): String {
        return id
    }

    abstract fun register(): Registrable
}
