package heroes.journey.initializers.base;

import heroes.journey.entities.items.ConsumableItem;
import heroes.journey.entities.items.Item;
import heroes.journey.entities.tagging.Attributes;
import heroes.journey.initializers.InitializerInterface;
import heroes.journey.initializers.base.tags.DamageTypes;
import heroes.journey.initializers.base.tags.DefenseTypes;
import heroes.journey.initializers.utils.Utils;

import static heroes.journey.registries.Registries.ItemSubTypeManager;

public class Items implements InitializerInterface {

    // Needed for Switch cases of subtype
    public static final String RAW_MATERIAL = "Raw Material", REFINED_MATERIAL = "Refined Material", SWORD = "Sword", CHEST_ARMOR = "Chest Armor", POTION = "Potion";
    public static Item wood, ironOre, ironIngot, ironSword, chestPlate;
    public static ConsumableItem healthPotion;

    @Override
    public void init() {
        // Items
        wood = new Item("Wood", ItemSubTypeManager.get(RAW_MATERIAL), 1, 1, new Attributes()).register();

        ironOre = new Item("Iron Ore", ItemSubTypeManager.get(RAW_MATERIAL), 1, 1, new Attributes()).register();

        ironIngot = new Item("Iron Ingot", ItemSubTypeManager.get(REFINED_MATERIAL), 1, 1, new Attributes()).register();

        ironSword = new Item("Iron Sword", ItemSubTypeManager.get(SWORD), 1, 3, new Attributes().add(DamageTypes.PHYSICAL, 3)).register();
        
        chestPlate = new Item("Chest Plate", ItemSubTypeManager.get(CHEST_ARMOR), 5, 1, new Attributes().add(DefenseTypes.PHYSICAL_DEF, 3)).register();

        healthPotion = new ConsumableItem("Health Potion", ItemSubTypeManager.get(POTION), 1, 1, new Attributes(),
            input -> Utils.addItem(input, ironIngot, 1)).register();
    }

}
