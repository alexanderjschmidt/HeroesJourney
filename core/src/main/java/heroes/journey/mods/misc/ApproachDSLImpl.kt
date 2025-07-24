package heroes.journey.mods.misc

import heroes.journey.entities.Approach
import heroes.journey.entities.tagging.Stat
import heroes.journey.modlib.attributes.IAttributes
import heroes.journey.modlib.misc.ApproachBuilder
import heroes.journey.modlib.misc.ApproachDSL
import heroes.journey.modlib.misc.IApproach
import heroes.journey.mods.Registries

class ApproachBuilderImpl : ApproachBuilder {
    override var id: String = ""
    override var cost: IAttributes? = null

    private val statIds = mutableListOf<String>()
    private val requiresAllIds = mutableListOf<String>()
    private val requiresAnyIds = mutableListOf<String>()
    private val forbiddenIds = mutableListOf<String>()

    override fun stat(vararg statIdsIn: String) {
        for (stat in statIdsIn) {
            statIds.add(stat)
        }
    }

    override fun requiresAll(vararg tags: String) {
        for (stat in tags) {
            requiresAllIds.add(stat)
        }
    }

    override fun requiresAny(vararg tags: String) {
        for (stat in tags) {
            requiresAnyIds.add(stat)
        }
    }

    override fun forbids(vararg tags: String) {
        for (stat in tags) {
            forbiddenIds.add(stat)
        }
    }

    fun build(): Approach {
        val stats = statIds.map { statId ->
            Registries.StatManager[statId] as Stat
        }
        val requiresAll = requiresAllIds.map { statId ->
            Registries.StatManager[statId] as Stat
        }
        val requiresAny = requiresAnyIds.map { statId ->
            Registries.StatManager[statId] as Stat
        }
        val forbidden = forbiddenIds.map { statId ->
            Registries.StatManager[statId] as Stat
        }
        return Approach(id, stats, cost, requiresAll, requiresAny, forbidden)
    }
}

class ApproachDSLImpl : ApproachDSL {
    override fun approach(init: ApproachBuilder.() -> Unit): IApproach {
        val builder = ApproachBuilderImpl()
        builder.init()
        return builder.build()
    }
}
