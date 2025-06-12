package heroes.journey.modding.api.gamestate

import java.util.*

interface GameState {
    val width: Int
    val height: Int
    val turn: Int
    val currentEntity: UUID
    val entitiesInActionOrder: List<UUID>
}
