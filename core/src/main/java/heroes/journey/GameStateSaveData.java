package heroes.journey;

import heroes.journey.entities.actions.history.History;
import heroes.journey.tilemap.TileMapSaveData;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
public class GameStateSaveData {
    private TileMapSaveData map;
    private History history;
    private int turn;
    private UUID currentEntity;
    private List<UUID> entitiesInActionOrder;

    public GameStateSaveData(TileMapSaveData map, History history, int turn, UUID currentEntity, List<UUID> entitiesInActionOrder) {
        this.map = map;
        this.history = history;
        this.turn = turn;
        this.currentEntity = currentEntity;
        this.entitiesInActionOrder = entitiesInActionOrder;
    }
}
