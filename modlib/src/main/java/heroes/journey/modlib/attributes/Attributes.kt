package heroes.journey.modlib.attributes

import heroes.journey.modlib.registries.Registries

/**
 * A stat container, used for character and challenge attributes.
 * This is a simple data container with stat calculation logic.
 */
class Attributes : HashMap<Stat, Int> {

    constructor() : super()

    constructor(map: Map<Stat, Int>) : super(map)

    fun get(statId: String): Int? {
        val stat = Registries.StatManager[statId]
        return if (stat != null) get(stat) else null
    }

    override fun get(stat: Stat): Int? {
        if (stat == null) return null

        // Get the base value from the stat's formula
        val baseValue = stat.formula(this)
        if (baseValue == null) {
            return null
        }

        // Apply parent stat value (additive)
        val parentStat = stat.getRelation(Relation.PARENT)
        if (parentStat != null) {
            val parentVal = get(parentStat)
            if (parentVal != null) {
                return baseValue + parentVal
            }
        }

        // Apply multiplier using the new MULTIPLIER relation
        val multiplierStat = stat.getRelation(Relation.MULTIPLIER)
        if (multiplierStat != null) {
            val multiplier = get(multiplierStat)
            if (multiplier != null) {
                return baseValue * multiplier
            }
        }

        return baseValue
    }

    fun getDirect(statId: String): Int? {
        val stat = Registries.StatManager[statId]
        return if (stat != null) super.get(stat) else null
    }

    fun put(statId: String, cascade: Boolean): Attributes {
        val stat = Registries.StatManager[statId]
        if (stat != null) {
            put(statId, stat.defaultValue, cascade)
        }
        return this
    }

    fun put(statId: String, value: Int?, cascade: Boolean): Attributes {
        val stat = Registries.StatManager[statId]
        if (value == null)
            return this
        if (stat != null) {
            super.put(stat, value)
            if (cascade) {
                for (relation in stat.getRelations()) {
                    if (relation.isOne()) {
                        val relatedStat = stat.getRelation(relation)
                        if (relatedStat != null && !this.containsKey(relatedStat)) {
                            put(relatedStat.id, relatedStat.defaultValue, cascade)
                        }
                    } else {
                        val relatedStats = stat.getRelatedStats(relation)
                        for (relatedStat in relatedStats) {
                            if (!this.containsKey(relatedStat)) {
                                put(relatedStat.id, relatedStat.defaultValue, cascade)
                            }
                        }
                    }
                }
            }
        }
        return this
    }

    fun put(statId: String, value: Int?, operation: Operation): Attributes {
        val stat = Registries.StatManager[statId]
        if (stat != null) {
            if (this.containsKey(stat)) {
                val currentValue = super.get(stat) ?: 0
                val newValue = operation.apply(currentValue, value ?: 0)
                val minValue = stat.getRelation(this, Relation.MIN)
                val maxValue = stat.getRelation(this, Relation.MAX)
                val clampedValue = if (minValue != null && maxValue != null) {
                    newValue.coerceIn(minValue, maxValue)
                } else {
                    newValue
                }
                super.put(stat, clampedValue)
            } else {
                super.put(stat, value!!)
            }
        }
        return this
    }

    fun add(statId: String?, value: Int): Attributes {
        return put(statId ?: "", value, Operation.ADD)
    }

    fun merge(attributesToMerge: Attributes?, operation: Operation): Attributes {
        if (attributesToMerge != null) {
            attributesToMerge.forEach { (stat, value) ->
                put(stat.id, value, operation)
            }
        }
        return this
    }
}

/**
 * Builder for defining attributes in a natural DSL style.
 */
class AttributesBuilder {
    private val attrMap = mutableMapOf<String, Int>()

    fun stat(id: String, value: Int) {
        attrMap[id] = value
    }

    fun build(): Map<String, Int> = attrMap
}

/**
 * DSL entrypoint for defining attributes.
 *
 * Example usage:
 * ```kotlin
 * attributes {
 *     stat(Ids.STAT_BODY, 3)
 *     stat(Ids.STAT_MIND, 2)
 * }
 * ```
 */
fun attributes(init: AttributesBuilder.() -> Unit): Attributes {
    val builder = AttributesBuilder()
    builder.init()
    val attrs = Attributes()
    for ((statId, value) in builder.build()) {
        attrs.put(statId, value, false)
    }
    return attrs
}
