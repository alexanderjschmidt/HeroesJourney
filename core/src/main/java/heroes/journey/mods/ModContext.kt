package heroes.journey.mods

import heroes.journey.GameState

public class ModContext(
    val modName: String, val gameState: GameState
) {
    fun log(message: String) {
        println("[MOD: $modName] $message")
    }

    // Example API access points:
    fun registerItem(id: String, data: Any) {
        println("Registering item: $id")
        // Add to game item registry
    }
}
