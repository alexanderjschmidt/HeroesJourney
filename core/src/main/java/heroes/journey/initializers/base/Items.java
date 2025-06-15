package heroes.journey.initializers.base;

import heroes.journey.entities.items.ConsumableItem;
import heroes.journey.entities.items.Item;
import heroes.journey.initializers.InitializerInterface;

public class Items implements InitializerInterface {

    // Needed for Switch cases of subtype
    public static final String SWORD = "sword", CHEST_ARMOR = "chest_armor";
    public static Item ironOre, ironIngot, ironSword, chestPlate;
    public static ConsumableItem healthPotion;

    @Override
    public void init() {
    }

}
