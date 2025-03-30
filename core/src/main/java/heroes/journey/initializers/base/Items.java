package heroes.journey.initializers.base;

import heroes.journey.entities.items.Item;
import heroes.journey.entities.items.ItemSubType;
import heroes.journey.entities.items.ItemType;
import heroes.journey.initializers.InitializerInterface;

public class Items implements InitializerInterface {

    // Needed for Switch cases of subtype
    public static final String RAW_MATERIAL = "Raw Material", REFINED_MATERIAL = "Refined Material", SWORD = "Sword", CHEST_ARMOR = "Chest Armor", POTION = "Potion";
    public static final ItemSubType rawMaterial, refinedMaterial, sword, chestArmor, potion;
    public static final Item wood, ironOre, ironIngot, ironSword, chestPlate, healthPotion;

    static {
        rawMaterial = new ItemSubType(RAW_MATERIAL, ItemType.Misc);
        refinedMaterial = new ItemSubType(REFINED_MATERIAL, ItemType.Misc);
        sword = new ItemSubType(SWORD, ItemType.Weapon);
        chestArmor = new ItemSubType(CHEST_ARMOR, ItemType.Armor);
        potion = new ItemSubType(POTION, ItemType.Consumable);

        wood = new Item("Wood", rawMaterial, 1, 1);
        ironOre = new Item("Iron Ore", rawMaterial, 1, 1);
        ironIngot = new Item("Iron Ingot", refinedMaterial, 1, 1);
        ironSword = new Item("Sword", sword, 3, 1);
        chestPlate = new Item("Chest Plate", chestArmor, 5, 1);
        healthPotion = new Item("Health Potion", potion, 1, 1);
    }

}
