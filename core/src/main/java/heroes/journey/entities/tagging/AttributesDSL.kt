package heroes.journey.entities.tagging

import heroes.journey.registries.Tags

fun attributes(init: AttributesBuilder.() -> Unit): Attributes {
    val builder = AttributesBuilder()
    builder.init()
    return builder.build()
}

class AttributesBuilder {
    private val attributes = Attributes()

    fun build(): Attributes = attributes

    fun attr(tagName: String, value: Int) {
        val tag = Tags.getTag(tagName)
        attributes.add(tag, value)
    }

    fun set(tag: Tag, value: Int) {
        attributes.add(tag, value)
    }
}
