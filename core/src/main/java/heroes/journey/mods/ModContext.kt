package heroes.journey.mods

import heroes.journey.GameState
import heroes.journey.registries.Registries

public class ModContext(
    val modName: String, val gameState: GameState, val registries: Registries
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
