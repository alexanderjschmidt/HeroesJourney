package heroes.journey;

import java.util.List;
import java.util.UUID;

import heroes.journey.entities.actions.history.History;
import heroes.journey.tilemap.TileMapSaveData;
import lombok.Getter;

@Getter
public class GameStateSaveData {

    private final int width, height;
    private final TileMapSaveData map;
    private final History history;
    private final int turn;
    private final UUID currentEntity;
    private final List<UUID> entitiesInActionOrder;
    private final PlayerInfo playerInfo;

    public GameStateSaveData(
        int width,
        int height,
        TileMapSaveData map,
        History history,
        int turn,
        UUID currentEntity,
        List<UUID> entitiesInActionOrder,
        PlayerInfo playerInfo) {
        this.width = width;
        this.height = height;
        this.map = map;
        this.history = history;
        this.turn = turn;
        this.currentEntity = currentEntity;
        this.entitiesInActionOrder = entitiesInActionOrder;
        this.playerInfo = playerInfo;
    }
}
