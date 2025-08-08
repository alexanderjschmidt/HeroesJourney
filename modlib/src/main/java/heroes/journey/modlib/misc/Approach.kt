package heroes.journey.modlib.misc

import heroes.journey.modlib.attributes.IAttributes
import heroes.journey.modlib.attributes.Stat
import heroes.journey.modlib.registries.Registrable
import heroes.journey.modlib.registries.Registries

/**
 * An Approach, used for challenge approach definitions and data storage.
 * This is a simple data container with no complex functions.
 * UI rendering is handled by ApproachInfoProvider in the core module.
 */
class Approach(
    id: String,
    val stats: List<Stat>,
    val cost: IAttributes?,
    val requiredAllTags: List<Stat>,
    val requiredAnyTags: List<Stat>,
    val forbiddenTags: List<Stat>
) : Registrable(id) {

    override fun register(): Approach {
        Registries.ApproachManager.register(this)
        return this
    }
}

/**
 * Builder for defining an approach in a natural DSL style.
 */
class ApproachBuilder {
    var id: String = ""
    var cost: IAttributes? = null
    private val _stats = mutableListOf<String>()
    private val _requiredAllTags = mutableListOf<String>()
    private val _requiredAnyTags = mutableListOf<String>()
    private val _forbiddenTags = mutableListOf<String>()

    fun stat(vararg statIds: String) {
        _stats.addAll(statIds)
    }

    fun requiresAll(vararg tags: String) {
        _requiredAllTags.addAll(tags)
    }

    fun requiresAny(vararg tags: String) {
        _requiredAnyTags.addAll(tags)
    }

    fun forbids(vararg tags: String) {
        _forbiddenTags.addAll(tags)
    }

    fun builtStats(): List<Stat> {
        return _stats.map { statId ->
            heroes.journey.modlib.registries.Registries.StatManager[statId]
                ?: throw IllegalArgumentException("Stat not found: $statId")
        }
    }

    fun builtRequiredAllTags(): List<Stat> {
        return _requiredAllTags.map { tagId ->
            heroes.journey.modlib.registries.Registries.StatManager[tagId]
                ?: throw IllegalArgumentException("Stat not found: $tagId")
        }
    }

    fun builtRequiredAnyTags(): List<Stat> {
        return _requiredAnyTags.map { tagId ->
            heroes.journey.modlib.registries.Registries.StatManager[tagId]
                ?: throw IllegalArgumentException("Stat not found: $tagId")
        }
    }

    fun builtForbiddenTags(): List<Stat> {
        return _forbiddenTags.map { tagId ->
            heroes.journey.modlib.registries.Registries.StatManager[tagId]
                ?: throw IllegalArgumentException("Stat not found: $tagId")
        }
    }
}

/**
 * DSL entrypoint for defining an approach.
 *
 * Example usage:
 * ```kotlin
 * approach {
 *     id = Ids.APPROACH_MIGHT
 *     stat(Ids.STAT_BODY)
 *     stat(Ids.STAT_MIND)
 *     cost = attributes {
 *         stat(Ids.STAT_STAMINA, 2)
 *     }
 * }
 * ```
 */
fun approach(init: ApproachBuilder.() -> Unit): Approach {
    val builder = ApproachBuilder()
    builder.init()
    return Approach(
        builder.id,
        builder.builtStats(),
        builder.cost,
        builder.builtRequiredAllTags(),
        builder.builtRequiredAnyTags(),
        builder.builtForbiddenTags()
    )
}
