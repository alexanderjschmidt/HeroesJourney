package heroes.journey.mods

class GameMod(val name: String, private val onLoadBlock: GameMod.() -> Unit) {

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
}

fun gameMod(name: String, onLoad: GameMod.() -> Unit): GameMod {
    return GameMod(name, onLoad)
}
