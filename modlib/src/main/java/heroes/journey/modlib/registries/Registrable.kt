package heroes.journey.modlib.registries

import heroes.journey.modlib.Lang
import heroes.journey.modlib.actions.IActionContext

abstract class Registrable(override val id: String) : IRegistrable {

    init {
        require(id.isNotBlank()) { "Registrable id must not be blank or empty" }
    }

    override fun getName(): String {
        return Lang.instance.name(id)
    }

    override fun getDescription(): String {
        return Lang.instance.description(id)
    }

    // InfoProvider methods - all registrables can now be used as InfoProviders
    override fun getTitle(input: IActionContext): String {
        return getName()
    }

    override fun getDescription(input: Map<String, String>): String {
        return getDescription()
    }

    override fun toString(): String {
        return id
    }
}

interface IRegistrable : InfoProvider {
    val id: String
    fun getName(): String
    fun getDescription(): String
    fun register(): IRegistrable
}

interface InfoProvider {
    fun getTitle(input: IActionContext): String
    fun getDescription(input: Map<String, String>): String
}
