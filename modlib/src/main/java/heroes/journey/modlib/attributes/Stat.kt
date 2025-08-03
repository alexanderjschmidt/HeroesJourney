package heroes.journey.modlib.attributes

import heroes.journey.modlib.registries.IRegistrable
import heroes.journey.modlib.registries.Registries

/**
 * Interface for stats that can be registered and used in the game.
 * Mods should only use this interface, not implementation classes.
 */
interface IStat : IRegistrable {
    val formula: (IAttributes) -> Int?
    val defaultValue: Int?

    fun getRelation(relation: Relation): IStat?
    fun getRelation(attributes: IAttributes, relation: Relation): Int?
    fun getRelatedStats(relation: Relation): List<IStat>
    fun addRelatedStat(relation: Relation, stat: IStat)

    override fun register(): IStat
}

/**
 * Builder interface for creating stats using a DSL.
 */
interface StatBuilder {
    var id: String
    var parent: String?
    var min: Int?
    var max: Int?
    var minFormula: ((IAttributes) -> Int?)?
    var maxFormula: ((IAttributes) -> Int?)?
    var cap: Int?
    var formula: ((IAttributes) -> Int?)?
    var defaultValue: Int?
}

/**
 * Interface for the stat DSL implementation.
 */
interface StatDSL {
    fun stat(init: StatBuilder.() -> Unit): IStat
}

/**
 * Singleton provider for the StatDSL implementation.
 * The core game must set this before any mods are loaded.
 */
object StatDSLProvider {
    lateinit var instance: StatDSL
}

/**
 * DSL entrypoint for defining a new stat using a builder lambda.
 */
fun stat(init: StatBuilder.() -> Unit): IStat = StatDSLProvider.instance.stat(init)

/**
 * DSL function to establish a relationship between two existing stats.
 * @param statId the ID of the stat that will be related
 * @param relation the type of relationship
 * @param targetStatId the ID of the target stat
 */
fun relate(statId: String, relation: Relation, targetStatId: String) {
    val stat = Registries.StatManager[statId] as IStat
    val targetStat = Registries.StatManager[targetStatId] as IStat
    targetStat.addRelatedStat(relation, stat)
}
