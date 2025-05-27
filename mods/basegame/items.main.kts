import heroes.journey.entities.items.ItemSubType
import heroes.journey.entities.items.ItemType
import heroes.journey.mods.GameMod
import heroes.journey.mods.ModContext

val mod = object : GameMod("Base Game") {
  override fun onLoad(context: ModContext) {
    context.log("Hello from mod!")

    context.registries.itemSubTypeManager.register(ItemSubType("Raw Material", ItemType.Misc))
    context.registries.itemSubTypeManager.register(ItemSubType("Refined Material", ItemType.Misc))
    context.registries.itemSubTypeManager.register(ItemSubType("Sword", ItemType.Weapon))
    context.registries.itemSubTypeManager.register(ItemSubType("Chest Armor", ItemType.Armor))
    context.registries.itemSubTypeManager.register(ItemSubType("Potion", ItemType.Consumable))
  }
}

mod
