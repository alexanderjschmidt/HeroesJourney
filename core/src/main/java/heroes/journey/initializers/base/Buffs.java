package heroes.journey.initializers.base;

import heroes.journey.entities.Buff;
import heroes.journey.initializers.InitializerInterface;

public class Buffs implements InitializerInterface {

    public static Buff rested;

    @Override
    public void init() {
        rested = new Buff("Rested", 1, 0);
    }

}
