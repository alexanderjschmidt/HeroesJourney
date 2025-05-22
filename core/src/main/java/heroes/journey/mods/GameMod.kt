package heroes.journey.mods

abstract class GameMod(val name: String) {
    abstract fun onLoad(context: ModContext)

    override fun toString(): String {
        return name
    }
}
