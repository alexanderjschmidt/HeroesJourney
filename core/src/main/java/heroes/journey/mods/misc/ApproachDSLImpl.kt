package heroes.journey.mods.misc

import heroes.journey.entities.Approach
import heroes.journey.entities.tagging.Stat
import heroes.journey.modlib.attributes.IStat
import heroes.journey.modlib.misc.IApproach
import heroes.journey.modlib.misc.ApproachBuilder
import heroes.journey.modlib.misc.ApproachDSL
import heroes.journey.mods.Registries

class ApproachBuilderImpl : ApproachBuilder {
    override var id: String = ""
    private val statIds = mutableListOf<String>()
    
    override fun stat(statId: String) {
        statIds.add(statId)
    }
    
    fun build(): Approach {
        val stats = statIds.map { statId ->
            Registries.StatManager[statId] as Stat
        }
        return Approach(id, stats)
    }
}

class ApproachDSLImpl : ApproachDSL {
    override fun approach(init: ApproachBuilder.() -> Unit): IApproach {
        val builder = ApproachBuilderImpl()
        builder.init()
        return builder.build()
    }
} 