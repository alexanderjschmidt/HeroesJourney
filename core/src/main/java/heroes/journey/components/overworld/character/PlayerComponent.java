package heroes.journey.components.overworld.character;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;

import lombok.Getter;
import lombok.Setter;

public class PlayerComponent implements Component {

    private final String playerId;
    @Getter @Setter private int fame = 0;

    public PlayerComponent(String playerId) {
        this.playerId = playerId;
    }

    public String getPlayerId() {
        return playerId;
    }

    private static final ComponentMapper<PlayerComponent> mapper = ComponentMapper.getFor(
        PlayerComponent.class);

    public static PlayerComponent get(Entity entity) {
        return mapper.get(entity);
    }
}
