package heroes.journey.modlib.misc

import heroes.journey.modlib.attributes.IAttributes
import heroes.journey.modlib.attributes.IStat
import heroes.journey.modlib.registries.Registries
import heroes.journey.modlib.registries.Registrable

/**
Fibonacci * 10 with some rounding
1,  2,  3,  4,  5,   6,   7,   8,   9,   10,   11?
10, 20, 30, 50, 75, 125, 200, 350, 500, 1000, 1500
 */
val powerLevels = listOf(10, 20, 30, 50, 75, 125, 200, 350, 500, 1000, 1500)

/**
 * A Challenge, used for challenge definitions and data storage.
 * This is a simple data container with no complex functions.
 * UI rendering is handled by ChallengeInfoProvider in the core module.
 */
class Challenge(
    id: String,
    val render: String,
    val stats: List<IStat>,
    val powerTier: Int,
    val rewards: IAttributes
) : Registrable(id) {
    
    override fun register(): Challenge {
        Registries.ChallengeManager.register(this)
        return this
    }
}

/**
 * Builder for defining a challenge in a natural DSL style.
 */
class ChallengeBuilder {
    var id: String = ""
    var render: String = ""
    var powerTier: Int = 1
    var rewards: IAttributes? = null
    private val _stats = mutableListOf<String>()
    
    fun tag(vararg statIdsIn: String) {
        _stats.addAll(statIdsIn)
    }
    
    fun builtStats(): List<IStat> {
        return _stats.map { statId ->
            Registries.StatManager[statId] 
                ?: throw IllegalArgumentException("Stat not found: $statId")
        }
    }
    
    fun builtRewards(): IAttributes = rewards ?: heroes.journey.modlib.attributes.attributes { }
}

/**
 * DSL entrypoint for defining a challenge.
 *
 * Example usage:
 * ```kotlin
 * challenge {
 *     id = Ids.MY_CHALLENGE
 *     render = Ids.PLAYER_SPRITE
 *     tag(Ids.STAT_PHYSICAL)
 *     tag(Ids.STAT_SENTIENT)
 *     powerTier = 3 // Tier 3 challenge (30 power)
 *     rewards = attributes {
 *         stat(Ids.STAT_GOLD, 50)
 *         stat(Ids.STAT_XP, 100)
 *     }
 * }
 * ```
 */
fun challenge(init: ChallengeBuilder.() -> Unit): Challenge {
    val builder = ChallengeBuilder()
    builder.init()
    return Challenge(builder.id, builder.render, builder.builtStats(), builder.powerTier, builder.builtRewards())
}
