import heroes.journey.entities.items.ItemSubType
import heroes.journey.entities.items.ItemType
import heroes.journey.mods.GameMod
import heroes.journey.mods.ModContext
import heroes.journey.registries.Registry

val mod = object : GameMod("Base Game") {
    override fun onLoad(context: ModContext) {
        var itemSubTypeManager: Registry<ItemSubType> = context.registries.itemSubTypeManager
        itemSubTypeManager.register(ItemSubType("Raw Material", ItemType.Misc))
        itemSubTypeManager.register(ItemSubType("Refined Material", ItemType.Misc))
        itemSubTypeManager.register(ItemSubType("Sword", ItemType.Weapon))
        itemSubTypeManager.register(ItemSubType("Chest Armor", ItemType.Armor))
        itemSubTypeManager.register(ItemSubType("Potion", ItemType.Consumable))
        context.log("Loaded item sub types")
    }
}

mod
