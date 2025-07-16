package heroes.journey.mods

import heroes.journey.entities.tagging.Group
import heroes.journey.entities.tagging.Stat
import heroes.journey.modlib.IAttributes
import heroes.journey.modlib.IGroup
import heroes.journey.modlib.IStat
import heroes.journey.modlib.StatDSL

class StatDSLImpl : StatDSL {
    override fun stat(
        id: String,
        min: Int,
        max: Int,
        groups: List<IGroup>,
        formula: ((IAttributes) -> Int)?
    ): IStat {
        val coreGroups = groups.map { it as Group }
        val coreFormula: (IAttributes) -> Int = if (formula != null) {
            formula
        } else {
            { attrs -> attrs.getDirect(id) }
        }
        return Stat(id, min, max, coreFormula, coreGroups)
    }
}
