package heroes.journey.initializers.base;

import heroes.journey.entities.items.ConsumableItem;
import heroes.journey.entities.items.Item;
import heroes.journey.entities.items.ItemSubType;
import heroes.journey.entities.items.ItemType;
import heroes.journey.entities.tagging.Attributes;
import heroes.journey.initializers.InitializerInterface;
import heroes.journey.initializers.base.tags.DamageTypes;
import heroes.journey.initializers.base.tags.DefenseTypes;
import heroes.journey.initializers.utils.Utils;

public class Items implements InitializerInterface {

    // Needed for Switch cases of subtype
    public static final String RAW_MATERIAL = "Raw Material", REFINED_MATERIAL = "Refined Material", SWORD = "Sword", CHEST_ARMOR = "Chest Armor", POTION = "Potion";
    public static final ItemSubType rawMaterial, refinedMaterial, sword, chestArmor, potion;
    public static final Item wood, ironOre, ironIngot, ironSword, chestPlate;
    public static final ConsumableItem healthPotion;

    static {
        // SubTypes
        rawMaterial = ItemSubType.builder().name(RAW_MATERIAL).parentType(ItemType.Misc).build();
        refinedMaterial = ItemSubType.builder().name(REFINED_MATERIAL).parentType(ItemType.Misc).build();
        sword = ItemSubType.builder().name(SWORD).parentType(ItemType.Weapon).build();
        chestArmor = ItemSubType.builder().name(CHEST_ARMOR).parentType(ItemType.Armor).build();
        potion = ItemSubType.builder().name(POTION).parentType(ItemType.Consumable).build();

        // Items
        wood = Item.builder().name("Wood").subType(rawMaterial).weight(1).value(1).build().register();
        ironOre = Item.builder().name("Iron Ore").subType(rawMaterial).weight(1).value(1).build().register();
        ironIngot = Item.builder()
            .name("Iron Ingot")
            .subType(refinedMaterial)
            .weight(1)
            .value(1)
            .build()
            .register();
        ironSword = Item.builder()
            .name("Iron Sword")
            .subType(sword)
            .attributes(new Attributes().add(DamageTypes.PHYSICAL, 3))
            .weight(1)
            .value(3)
            .build()
            .register();
        chestPlate = Item.builder()
            .name("Chest Plate")
            .subType(chestArmor)
            .attributes(new Attributes().add(DefenseTypes.PHYSICAL_DEF, 3))
            .weight(5)
            .value(1)
            .build()
            .register();
        healthPotion = ConsumableItem.builder()
            .name("Health Potion")
            .subType(potion)
            .weight(1)
            .value(1)
            .onConsume((input) -> Utils.addItem(input, ironIngot, 1))
            .build()
            .register();
    }

}
