package heroes.journey.modlib

/**
 * Public interface for a Stat, used for character and challenge stats.
 * Mods should only use this interface, not implementation classes.
 */
interface IStat : IRegistrable {
    val min: Int
    val max: Int
    val groups: List<IGroup>
    val formula: (IAttributes) -> Int
    override fun register(): IStat
}

/**
 * Interface for the stat DSL implementation.
 */
interface StatDSL {
    fun stat(
        id: String,
        min: Int = 1,
        max: Int = 10,
        groups: List<IGroup> = emptyList(),
        formula: ((IAttributes) -> Int)? = null
    ): IStat
}

/**
 * Singleton provider for the StatDSL implementation.
 * The core game must set this before any mods are loaded.
 */
object StatDSLProvider {
    lateinit var instance: StatDSL
}

/**
 * DSL entrypoint for mods. Always delegates to the core implementation.
 */
fun stat(
    id: String,
    min: Int = 1,
    max: Int = 10,
    groups: List<IGroup> = emptyList(),
    formula: ((IAttributes) -> Int)? = null
): IStat = StatDSLProvider.instance.stat(id, min, max, groups, formula)
