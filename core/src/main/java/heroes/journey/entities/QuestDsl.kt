package heroes.journey.entities

import heroes.journey.entities.tagging.Attributes
import heroes.journey.entities.tagging.Stat

// Helper functions for quest creation
fun questCost(vararg pairs: Pair<Stat, Int>): Attributes {
    return Attributes().apply {
        pairs.forEach { (stat, amount) -> add(stat, amount) }
    }
}

fun questRewards(vararg pairs: Pair<Stat, Int>): Attributes {
    return Attributes().apply {
        pairs.forEach { (stat, amount) -> add(stat, amount) }
    }
} 