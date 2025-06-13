import heroes.journey.entities.items.ItemSubType
import heroes.journey.entities.items.ItemType
import heroes.journey.mods.GameMod
import heroes.journey.mods.ModContext
import heroes.journey.registries.Registry

val mod = object : GameMod("Base Game") {
    override fun onLoad(context: ModContext) {
        var itemSubTypeManager: Registry<ItemSubType> = context.registries.itemSubTypeManager
        itemSubTypeManager.register(ItemSubType("raw_material", "Raw Material", ItemType.Misc))
        itemSubTypeManager.register(ItemSubType("refined_material", "Refined Material", ItemType.Misc))
        itemSubTypeManager.register(ItemSubType("sword", "Sword", ItemType.Weapon))
        itemSubTypeManager.register(ItemSubType("chest_armor", "Chest Armor", ItemType.Armor))
        itemSubTypeManager.register(ItemSubType("potion", "Potion", ItemType.Consumable))
        context.log("Loaded item sub types")
    }
}

mod
