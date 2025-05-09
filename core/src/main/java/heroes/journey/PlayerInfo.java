package heroes.journey;

import java.util.UUID;

import heroes.journey.tilemap.Fog;
import heroes.journey.tilemap.FogUtils;
import lombok.Getter;

@Getter
public class PlayerInfo {

    private static PlayerInfo playerInfo;

    private UUID playersHero;
    private Fog[][] fog;
    private final UUID uuid;

    private PlayerInfo() {
        uuid = UUID.randomUUID();
    }

    public static PlayerInfo get() {
        if (playerInfo == null)
            playerInfo = new PlayerInfo();
        return playerInfo;
    }

    public static void updateFog() {
        get().fog = FogUtils.getFog(GameState.global(), get().playersHero);
    }

    public static boolean isPlayer(UUID entityId) {
        return get().playersHero == entityId;
    }

    public static boolean isCurrentlyPlaying() {
        return isPlayer(GameState.global().getCurrentEntity());
    }

    public void setPlayerId(UUID playerId) {
        this.playersHero = playerId;
    }
}
