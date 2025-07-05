package heroes.journey.entities.tagging

import heroes.journey.entities.tagging.Stat

fun attributes(init: AttributesBuilder.() -> Unit): Attributes {
    val builder = AttributesBuilder()
    builder.init()
    return builder.build()
}

class AttributesBuilder {
    private val attributes = Attributes()

    fun build(): Attributes = attributes

    fun attr(tagName: String, value: Int) {
        val tag = Stat.getById(tagName)
        attributes.add(tag, value)
    }

    fun set(tag: Stat, value: Int) {
        attributes.add(tag, value)
    }
}
