package heroes.journey.modlib.registries

import heroes.journey.modlib.Lang

abstract class Registrable(override val id: String) : IRegistrable {

    init {
        require(id.isNotBlank()) { "Renderable id must not be blank or empty" }
    }

    override fun getName(): String {
        return Lang.instance.get("${id}_name")
    }

    override fun getDescription(): String {
        return Lang.instance.get("${id}_description")
    }

    override fun toString(): String {
        return id
    }
}

interface IRegistrable {
    val id: String
    fun getName(): String
    fun getDescription(): String
    fun register(): IRegistrable
}
