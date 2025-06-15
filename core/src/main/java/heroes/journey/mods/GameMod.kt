package heroes.journey.mods

abstract class GameMod(val name: String) {

    init {
        log("Found")
    }

    fun load() {
        onLoad()
        log("Loaded")
    }

    fun log(message: String) {
        println("[MOD: $name] $message")
    }

    abstract fun onLoad()

    override fun toString(): String {
        return name
    }
}
