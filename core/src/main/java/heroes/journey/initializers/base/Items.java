package heroes.journey.initializers.base;

import heroes.journey.components.utils.Utils;
import heroes.journey.entities.items.ConsumableItem;
import heroes.journey.entities.items.Item;
import heroes.journey.entities.items.ItemSubType;
import heroes.journey.entities.items.ItemType;
import heroes.journey.initializers.InitializerInterface;

public class Items implements InitializerInterface {

    // Needed for Switch cases of subtype
    public static final String RAW_MATERIAL = "Raw Material", REFINED_MATERIAL = "Refined Material", SWORD = "Sword", CHEST_ARMOR = "Chest Armor", POTION = "Potion";
    public static final ItemSubType rawMaterial, refinedMaterial, sword, chestArmor, potion;
    public static final Item wood, ironOre, ironIngot, ironSword, chestPlate;
    public static final ConsumableItem healthPotion;

    static {
        // SubTypes
        rawMaterial = new ItemSubType.Builder().name(RAW_MATERIAL).parentType(ItemType.Misc).build();
        refinedMaterial = new ItemSubType.Builder().name(REFINED_MATERIAL).parentType(ItemType.Misc).build();
        sword = new ItemSubType.Builder().name(SWORD).parentType(ItemType.Weapon).build();
        chestArmor = new ItemSubType.Builder().name(CHEST_ARMOR).parentType(ItemType.Armor).build();
        potion = new ItemSubType.Builder().name(POTION).parentType(ItemType.Consumable).build();

        // Items
        wood = new Item.Builder().name("Wood").type(rawMaterial).weight(1).value(1).build();
        ironOre = new Item.Builder().name("Iron Ore").type(rawMaterial).weight(1).value(1).build();
        ironIngot = new Item.Builder().name("Iron Ingot").type(refinedMaterial).weight(1).value(1).build();
        ironSword = new Item.Builder().name("Iron Sword").type(sword).weight(1).value(3).build();
        chestPlate = new Item.Builder().name("Chest Plate").type(chestArmor).weight(5).value(1).build();
        healthPotion = new ConsumableItem.Builder().name("Health Potion")
            .type(potion)
            .weight(1)
            .value(1)
            .onConsume((gs, e) -> Utils.addItem(e, ironIngot, 1))
            .build();
    }

}
