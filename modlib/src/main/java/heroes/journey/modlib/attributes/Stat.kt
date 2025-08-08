package heroes.journey.modlib.attributes

import heroes.journey.modlib.registries.Registrable
import heroes.journey.modlib.registries.Registries

/**
 * A Stat, used for stat definitions and data storage.
 * This is a simple data container with no complex functions.
 */
class Stat(
    id: String,
    val formula: (Attributes) -> Int?,
    val defaultValue: Int?
) : Registrable(id) {
    
    private val relatedStats = mutableMapOf<Relation, Stat>() // For ONE relations
    private val relatedStatsMany = mutableMapOf<Relation, MutableList<Stat>>() // For MANY relations
    
    override fun register(): Stat {
        Registries.StatManager.register(this)
        return this
    }
    
    fun getRelations(): List<Relation> {
        val relations = mutableListOf<Relation>()
        relations.addAll(relatedStats.keys)
        relations.addAll(relatedStatsMany.keys)
        return relations
    }
    
    fun getRelation(relation: Relation): Stat? {
        return relatedStats[relation]
    }
    
    fun getRelation(attributes: Attributes, relation: Relation): Int? {
        if (relation.isOne()) {
            val relatedStat = relatedStats[relation]
            if (relatedStat != null) {
                return attributes[relatedStat]
            }
        } else if (relation.isMany()) {
            throw RuntimeException("This relationship ($relation) has many stats associated to it.")
        }
        
        // Return the default value for this relation type
        return relation.getDefaultValue()
    }
    
    fun getRelatedStats(relation: Relation): List<Stat> {
        return relatedStatsMany[relation] ?: emptyList()
    }
    
    fun addRelatedStat(relation: Relation, stat: Stat) {
        if (relation.isOne()) {
            relatedStats[relation] = stat
        } else if (relation.isMany()) {
            relatedStatsMany.getOrPut(relation) { mutableListOf() }.add(stat)
        }
    }
}

/**
 * Builder for defining a stat in a natural DSL style.
 */
class StatBuilder {
    var id: String = ""
    var parent: String? = null
    var min: Int? = null
    var max: Int? = null
    var minFormula: ((Attributes) -> Int?)? = null
    var maxFormula: ((Attributes) -> Int?)? = null
    var cap: Int? = null
    var formula: ((Attributes) -> Int?)? = null
    var defaultValue: Int? = null
    
    fun build(): Stat {
        // Validate mutually exclusive properties
        if (min != null && minFormula != null) {
            throw IllegalArgumentException("Cannot specify both 'min' and 'minFormula' for stat '$id'. Use only one.")
        }
        if (max != null && maxFormula != null) {
            throw IllegalArgumentException("Cannot specify both 'max' and 'maxFormula' for stat '$id'. Use only one.")
        }
        
        // Validate cap requires max or maxFormula
        if (cap != null && max == null && maxFormula == null) {
            throw IllegalArgumentException("Cannot specify 'cap' for stat '$id' without either 'max' or 'maxFormula'. Cap requires a max stat to exist.")
        }
        
        val coreFormula: (Attributes) -> Int? = formula ?: { attrs ->
            try {
                attrs.getDirect(id)
            } catch (_: Exception) {
                null
            }
        }
        
        // Create the main stat
        val mainStat = Stat(id, coreFormula, defaultValue)
        
        // Set up parent/child relationship if specified
        if (parent != null) {
            val parentStat = Registries.StatManager[parent!!] as Stat
            mainStat.addRelatedStat(Relation.PARENT, parentStat)
            parentStat.addRelatedStat(Relation.CHILD, mainStat)
        }
        
        // Automatically create related stats if specified
        if (min != null) {
            val minStat = createRelatedStat("${id}_min", min!!)
            mainStat.addRelatedStat(Relation.MIN, minStat)
            minStat.addRelatedStat(Relation.BASE, mainStat)
        }
        if (max != null) {
            val maxStat = createRelatedStat("${id}_max", max!!)
            mainStat.addRelatedStat(Relation.MAX, maxStat)
            maxStat.addRelatedStat(Relation.BASE, mainStat)
        }
        if (minFormula != null) {
            val minStat = createRelatedStatWithFormula("${id}_min", minFormula!!)
            mainStat.addRelatedStat(Relation.MIN, minStat)
            minStat.addRelatedStat(Relation.BASE, mainStat)
        }
        if (maxFormula != null) {
            val maxStat = createRelatedStatWithFormula("${id}_max", maxFormula!!)
            mainStat.addRelatedStat(Relation.MAX, maxStat)
            maxStat.addRelatedStat(Relation.BASE, mainStat)
        }
        
        // Auto-generate multiplier stat for the main stat
        val multStat = createRelatedStat("${id}_mult", 1)
        mainStat.addRelatedStat(Relation.MULTIPLIER, multStat)
        multStat.addRelatedStat(Relation.MULTIPLICAND, mainStat)
        
        // If we have a max stat, also create a multiplier for it
        val maxStat = mainStat.getRelation(Relation.MAX)
        if (maxStat != null) {
            val maxMultStat = createRelatedStat("${id}_max_mult", 1)
            maxStat.addRelatedStat(Relation.MULTIPLIER, maxMultStat)
            maxMultStat.addRelatedStat(Relation.MULTIPLICAND, maxStat)
        }
        
        // If we have a cap, create a max_max stat and link it to the max stat
        if (cap != null) {
            val maxMaxStat = createRelatedStat("${id}_max_max", cap!!)
            val maxStat = mainStat.getRelation(Relation.MAX)
            if (maxStat != null) {
                maxStat.addRelatedStat(Relation.MAX, maxMaxStat)
                maxMaxStat.addRelatedStat(Relation.BASE, maxStat)
            }
        }
        
        return mainStat
    }
    
    private fun createRelatedStat(relatedStatId: String, defaultValue: Int): Stat {
        // Create the related stat
        val relatedStat = Stat(
            relatedStatId,
            { attrs -> attrs.getDirect(relatedStatId) ?: defaultValue },
            defaultValue
        )
        
        // Register the related stat
        Registries.StatManager.register(relatedStat)
        
        return relatedStat
    }
    
    private fun createRelatedStatWithFormula(relatedStatId: String, formula: (Attributes) -> Int?): Stat {
        // Create the related stat with formula
        val relatedStat = Stat(
            relatedStatId,
            formula,
            null // No default value when using formula
        )
        
        // Register the related stat
        Registries.StatManager.register(relatedStat)
        
        return relatedStat
    }
}

/**
 * DSL entrypoint for defining a stat.
 *
 * Example usage:
 * ```kotlin
 * stat {
 *     id = Ids.STAT_BODY
 *     parent = Ids.GROUP_BASE_STATS
 *     defaultValue = 1
 *     min = 1
 *     max = 10
 * }
 * ```
 */
fun stat(init: StatBuilder.() -> Unit): Stat {
    val builder = StatBuilder()
    builder.init()
    return builder.build()
}

/**
 * DSL function to establish a relationship between two existing stats.
 * @param statId the ID of the stat that will be related
 * @param relation the type of relationship
 * @param targetStatId the ID of the target stat
 */
fun relate(statId: String, relation: Relation, targetStatId: String) {
    val stat = Registries.StatManager[statId] as Stat
    val targetStat = Registries.StatManager[targetStatId] as Stat
    targetStat.addRelatedStat(relation, stat)
}
