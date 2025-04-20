package heroes.journey.components;

import java.util.Map;

import com.artemis.PooledComponent;
import com.artemis.World;

import heroes.journey.entities.Loyalty;
import lombok.Getter;

@Getter
public class LoyaltyComponent extends PooledComponent {

    private Map<Integer,Loyalty> loyaltyMap;

    public LoyaltyComponent putLoyalty(Integer entityId, Loyalty loyalty) {
        loyaltyMap.put(entityId, loyalty);
        return this;
    }

    public static LoyaltyComponent get(World world, int entityId) {
        return world.getMapper(LoyaltyComponent.class).get(entityId);
    }

    @Override
    protected void reset() {
        loyaltyMap.clear();
    }
}
