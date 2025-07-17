package heroes.journey.entities.tagging

import heroes.journey.modlib.IAttributes
import heroes.journey.modlib.IGroup
import heroes.journey.registries.Registries
import heroes.journey.registries.Registries.StatManager

fun attributes(init: AttributesBuilder.() -> Unit): Attributes {
    val builder = AttributesBuilder()
    builder.init()
    return builder.build()
}

class AttributesBuilder {
    private val attributes = Attributes()

    fun build(): Attributes = attributes

    fun attr(tagName: String, value: Int) {
        val tag = StatManager[tagName]
        attributes.add(tag!!, value)
    }

    fun set(tag: Stat, value: Int) {
        attributes.add(tag, value)
    }
}

// --- GROUP DSL ---

class GroupBuilder(val id: String) {
    fun build(): Group = Group(id)
}

fun group(id: String): Group {
    val builder = GroupBuilder(id)
    return builder.build()
}

// --- STAT DSL ---

class StatBuilder(val id: String) {
    var min: Int = 1
    var max: Int = 10
    var groups: MutableList<IGroup> = mutableListOf()
    var formula: (IAttributes) -> Int = { it.getDirect(id) }

    fun group(groupId: String) {
        Registries.GroupManager.get(groupId)?.let { groups.add(it) }
    }

    fun build(): Stat = Stat(id, min, max, formula, groups)
}

fun stat(id: String, init: StatBuilder.() -> Unit): Stat {
    val builder = StatBuilder(id)
    builder.init()
    return builder.build()
}
