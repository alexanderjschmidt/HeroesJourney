package heroes.journey.modding.api

abstract class GameMod(private val modName: String) {

    init {
        log("Found")
    }

    fun load(context: GameContext) {
        onLoad(context)
        log("Loaded")
    }

    abstract fun onLoad(context: GameContext)

    fun log(message: String) {
        println("[MOD: $modName] $message")
    }

    override fun toString(): String {
        return modName
    }
}
