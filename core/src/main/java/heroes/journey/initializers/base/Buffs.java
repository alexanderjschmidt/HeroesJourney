package heroes.journey.initializers.base;

import heroes.journey.entities.buffs.Buff;
import heroes.journey.initializers.InitializerInterface;

public class Buffs implements InitializerInterface {

    public static Buff rested;

    static {
        rested = Buff.builder().name("Rested").turnsBuffLasts(1).build().register();
    }

}
