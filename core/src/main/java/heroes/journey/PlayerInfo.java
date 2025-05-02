package heroes.journey;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

@Getter
public class PlayerInfo {

    private static PlayerInfo playerInfo;

    private final List<Integer> playableEntities;

    private PlayerInfo() {
        playableEntities = new ArrayList<>();
    }

    public static PlayerInfo get() {
        if (playerInfo == null)
            playerInfo = new PlayerInfo();
        return playerInfo;
    }
}
