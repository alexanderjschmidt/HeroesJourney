package heroes.journey;

import heroes.journey.entities.actions.history.History;
import heroes.journey.tilemap.TileMapSaveData;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
public class GameStateSaveData {

    private int width, height;
    private TileMapSaveData map;
    private History history;
    private int turn;
    private UUID currentEntity;
    private List<UUID> entitiesInActionOrder;
    private PlayerInfo playerInfo;

    public GameStateSaveData(int width, int height, TileMapSaveData map, History history, int turn, UUID currentEntity, List<UUID> entitiesInActionOrder, PlayerInfo playerInfo) {
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
