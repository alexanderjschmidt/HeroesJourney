package heroes.journey.mods.attributes

import heroes.journey.entities.tagging.Stat
import heroes.journey.modlib.attributes.*
import heroes.journey.mods.Registries

class StatBuilderImpl : StatBuilder {
    override var id: String = ""
    override var parent: String? = null
    override var min: Int? = null
    override var max: Int? = null
    override var minFormula: ((IAttributes) -> Int?)? = null
    override var maxFormula: ((IAttributes) -> Int?)? = null
    override var cap: Int? = null
    override var formula: ((IAttributes) -> Int?)? = null
    override var defaultValue: Int? = null

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

        val coreFormula: (IAttributes) -> Int? = formula ?: { attrs ->
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
            val parentStat = Registries.StatManager[parent!!] as IStat
            mainStat.addRelatedStat(Relation.PARENT, parentStat)
        }

        // Automatically create related stats if specified
        if (min != null) {
            val minStat = createRelatedStat("${id}_min", min!!)
            mainStat.addRelatedStat(Relation.MIN, minStat)
        }
        if (max != null) {
            val maxStat = createRelatedStat("${id}_max", max!!)
            mainStat.addRelatedStat(Relation.MAX, maxStat)
        }
        if (minFormula != null) {
            val minStat = createRelatedStatWithFormula("${id}_min", minFormula!!)
            mainStat.addRelatedStat(Relation.MIN, minStat)
        }
        if (maxFormula != null) {
            val maxStat = createRelatedStatWithFormula("${id}_max", maxFormula!!)
            mainStat.addRelatedStat(Relation.MAX, maxStat)
        }

        // Auto-generate multiplier stat for the main stat
        val multStat = createRelatedStat("${id}_mult", 100) // Default 100% = no change
        mainStat.addRelatedStat(Relation.MULTIPLICAND, multStat)
        multStat.addRelatedStat(Relation.MULTIPLIER, mainStat)

        // If we have a max stat, also create a multiplier for it
        val maxStat = mainStat.getRelation(Relation.MAX)
        if (maxStat != null) {
            val maxMultStat = createRelatedStat("${id}_max_mult", 100) // Default 100% = no change
            maxStat.addRelatedStat(Relation.MULTIPLICAND, maxMultStat)
            maxMultStat.addRelatedStat(Relation.MULTIPLIER, maxStat)
        }

        // If we have a cap, create a max_max stat and link it to the max stat
        if (cap != null) {
            val maxMaxStat = createRelatedStat("${id}_max_max", cap!!)
            val maxStat = mainStat.getRelation(Relation.MAX)
            if (maxStat != null) {
                maxStat.addRelatedStat(Relation.MAX, maxMaxStat)
                maxMaxStat.addRelatedStat(Relation.MIN, maxStat)
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

    private fun createRelatedStatWithFormula(relatedStatId: String, formula: (IAttributes) -> Int?): Stat {
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

class StatDSLImpl : StatDSL {
    override fun stat(init: StatBuilder.() -> Unit): IStat {
        val builder = StatBuilderImpl()
        builder.init()
        return builder.build()
    }
}
