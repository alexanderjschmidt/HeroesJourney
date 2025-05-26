package heroes.journey.initializers.base.tags;

import heroes.journey.entities.tagging.ConversionSet;
import heroes.journey.initializers.InitializerInterface;

public class ConversionSets implements InitializerInterface {

    public static final ConversionSet DEFENSE_TO_DAMAGE = new ConversionSet();
    
    @Override
    public void init() {
        DEFENSE_TO_DAMAGE.add(DefenseTypes.PHYSICAL_DEF, DamageTypes.PHYSICAL)
            .add(DefenseTypes.MAGICAL, DamageTypes.MAGICAL);
    }

}
