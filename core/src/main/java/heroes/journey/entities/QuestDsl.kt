package heroes.journey.entities

import heroes.journey.entities.tagging.Attributes
import heroes.journey.mods.Registries.StatManager

// Helper functions for quest creation
fun questCost(vararg pairs: Pair<String, Int>): Attributes {
    return Attributes().apply {
        pairs.forEach { (statId, amount) -> add(StatManager[statId]!!, amount) }
    }
}

fun questRewards(vararg pairs: Pair<String, Int>): Attributes {
    return Attributes().apply {
        pairs.forEach { (statId, amount) -> add(StatManager[statId]!!, amount) }
    }
}
