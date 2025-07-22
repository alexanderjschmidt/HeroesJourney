package heroes.journey.mods.attributes

import heroes.journey.entities.tagging.Group
import heroes.journey.entities.tagging.Stat
import heroes.journey.modlib.attributes.IAttributes
import heroes.journey.modlib.attributes.IStat
import heroes.journey.modlib.attributes.StatBuilder
import heroes.journey.modlib.attributes.StatDSL
import heroes.journey.mods.Registries

class StatBuilderImpl : StatBuilder {
    override var id: String = ""
    override var min: Int = 1
    override var max: Int = 10
    override var formula: ((IAttributes) -> Int?)? = null
    override var defaultValue: Int? = null
    private val groupIds = mutableListOf<String>()
    override fun group(id: String) {
        groupIds.add(id)
    }

    fun build(): Stat {
        val coreGroups = groupIds.map { Registries.GroupManager[it] as Group }
        val coreFormula: (IAttributes) -> Int? = formula ?: { attrs -> try { attrs.getDirect(id) } catch (_: Exception) { null } }
        return Stat(id, min, max, coreFormula, coreGroups, defaultValue)
    }
}

class StatDSLImpl : StatDSL {
    override fun stat(init: StatBuilder.() -> Unit): IStat {
        val builder = StatBuilderImpl()
        builder.init()
        return builder.build()
    }
}
