package heroes.journey.entities

import heroes.journey.modlib.attributes.Attributes

// Helper functions for quest creation
fun questCost(vararg pairs: Pair<String, Int>): Attributes {
    return Attributes().apply {
        pairs.forEach { (statId, amount) -> add(statId, amount) }
    }
}

fun questRewards(vararg pairs: Pair<String, Int>): Attributes {
    return Attributes().apply {
        pairs.forEach { (statId, amount) -> add(statId, amount) }
    }
}
