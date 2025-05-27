package heroes.journey.initializers.base;

import static heroes.journey.registries.Registries.ItemSubTypeManager;

import heroes.journey.entities.items.ConsumableItem;
import heroes.journey.entities.items.Item;
import heroes.journey.entities.tagging.Attributes;
import heroes.journey.initializers.InitializerInterface;
import heroes.journey.initializers.base.tags.DamageTypes;
import heroes.journey.initializers.base.tags.DefenseTypes;
import heroes.journey.initializers.utils.Utils;

public class Items implements InitializerInterface {

    // Needed for Switch cases of subtype
    public static final String RAW_MATERIAL = "Raw Material", REFINED_MATERIAL = "Refined Material", SWORD = "Sword", CHEST_ARMOR = "Chest Armor", POTION = "Potion";
    public static Item wood, ironOre, ironIngot, ironSword, chestPlate;
    public static ConsumableItem healthPotion;

    @Override
    public void init() {
        // Items
        wood = Item.builder()
            .name("Wood")
            .subType(ItemSubTypeManager.get(RAW_MATERIAL))
            .weight(1)
            .value(1)
            .build()
            .register();
        ironOre = Item.builder()
            .name("Iron Ore")
            .subType(ItemSubTypeManager.get(RAW_MATERIAL))
            .weight(1)
            .value(1)
            .build()
            .register();
        ironIngot = Item.builder()
            .name("Iron Ingot")
            .subType(ItemSubTypeManager.get(REFINED_MATERIAL))
            .weight(1)
            .value(1)
            .build()
            .register();
        ironSword = Item.builder()
            .name("Iron Sword")
            .subType(ItemSubTypeManager.get(SWORD))
            .attributes(new Attributes().add(DamageTypes.PHYSICAL, 3))
            .weight(1)
            .value(3)
            .build()
            .register();
        chestPlate = Item.builder()
            .name("Chest Plate")
            .subType(ItemSubTypeManager.get(CHEST_ARMOR))
            .attributes(new Attributes().add(DefenseTypes.PHYSICAL_DEF, 3))
            .weight(5)
            .value(1)
            .build()
            .register();
        healthPotion = ConsumableItem.builder()
            .name("Health Potion")
            .subType(ItemSubTypeManager.get(POTION))
            .weight(1)
            .value(1)
            .onConsume((input) -> Utils.addItem(input, ironIngot, 1))
            .build()
            .register();
    }

}
