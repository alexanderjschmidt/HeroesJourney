import heroes.journey.modding.api.GameContext
import heroes.journey.modding.api.GameMod
import heroes.journey.modding.api.definitions.ItemSubType
import heroes.journey.modding.api.definitions.ItemType
import heroes.journey.modding.api.registration.Registry

val mod = object : GameMod("Base Game") {
    override fun onLoad(context: GameContext) {
        // Register ItemSubTypes
        val itemSubTypeManager: Registry<ItemSubType> = context.registries.itemSubTypeManager
        itemSubTypeManager.register(ItemSubType("Raw Material", ItemType.Misc))
        itemSubTypeManager.register(ItemSubType("Refined Material", ItemType.Misc))
        itemSubTypeManager.register(ItemSubType("Sword", ItemType.Weapon))
        itemSubTypeManager.register(ItemSubType("Chest Armor", ItemType.Armor))
        itemSubTypeManager.register(ItemSubType("Potion", ItemType.Consumable))
        log("Subtypes loaded")
    }
}

mod
