package heroes.journey.mods

class GameMod(val name: String, private val onLoadBlock: GameMod.() -> Unit, val priority: Int) : Comparable<GameMod> {

    init {
        log("Found")
    }

    fun load() {
        onLoadBlock()
        log("Loaded")
    }

    fun log(message: String) {
        println("[MOD: $name] $message")
    }

    override fun toString(): String {
        return name
    }

    override fun compareTo(other: GameMod): Int {
        // Higher priority loads first, so descending order
        return other.priority.compareTo(this.priority)
    }
}

fun gameMod(name: String, priority: Int = 0, onLoad: GameMod.() -> Unit): GameMod {
    return GameMod(name, onLoad, priority)
}
