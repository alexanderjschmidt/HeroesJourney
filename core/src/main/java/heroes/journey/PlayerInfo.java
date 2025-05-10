package heroes.journey;

import heroes.journey.tilemap.Fog;
import heroes.journey.tilemap.FogUtils;
import lombok.Getter;

import java.util.UUID;

@Getter
public class PlayerInfo {

    private static PlayerInfo playerInfo;
    private final UUID uuid;
    private UUID playersHero;
    private transient Fog[][] fog;

    public PlayerInfo(UUID uuid, UUID playersHero) {
        this.uuid = uuid;
        this.playersHero = playersHero;
    }

    public PlayerInfo(UUID uuid) {
        this(uuid, null);
    }

    public PlayerInfo() {
        this(UUID.randomUUID(), null);
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
