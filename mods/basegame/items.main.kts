import heroes.journey.entities.items.Item
import heroes.journey.mods.GameMod
import heroes.journey.mods.ModContext

val mod = object : GameMod("Base Game") {
  override fun onLoad(context: ModContext) {
    context.log("Hello from mod!")
    val sword: Item = Item()
    context.registerItem("flaming_sword", "Flaming Sword")
  }
}

mod
