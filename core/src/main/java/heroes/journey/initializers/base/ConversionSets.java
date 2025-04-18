package heroes.journey.initializers.base;

import heroes.journey.entities.tagging.ConversionSet;

public class ConversionSets {

    public static final ConversionSet DEFENSE_TO_DAMAGE = new ConversionSet();

    static {
        DEFENSE_TO_DAMAGE.add(DefenseTypes.PHYSICAL_DEF, DamageTypes.PHYSICAL)
            .add(DefenseTypes.MAGICAL, DamageTypes.MAGICAL);
    }

}
