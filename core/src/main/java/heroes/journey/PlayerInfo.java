package heroes.journey;

import heroes.journey.tilemap.Fog;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
public class PlayerInfo {

    private static PlayerInfo playerInfo;

    private final List<UUID> playableEntities;
    private Fog[][] fog;
    private final UUID uuid;

    private PlayerInfo() {
        playableEntities = new ArrayList<>();
        uuid = UUID.randomUUID();
    }

    public static PlayerInfo get() {
        if (playerInfo == null)
            playerInfo = new PlayerInfo();
        return playerInfo;
    }

    public static void setFog(Fog[][] fog) {
        get().fog = fog;
    }
}
