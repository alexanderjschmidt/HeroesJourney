import heroes.journey.entities.actions.ActionInput
import heroes.journey.entities.items.ConsumableItem
import heroes.journey.entities.items.Item
import heroes.journey.entities.items.ItemSubType
import heroes.journey.entities.items.ItemType
import heroes.journey.entities.tagging.Attributes
import heroes.journey.entities.tagging.Stat
import heroes.journey.initializers.utils.Utils

// Items - included by basegame mod

// Item Sub Types
val raw_material = ItemSubType("raw_material", "Raw Material", ItemType.Misc).register()
val refined_material =
    ItemSubType("refined_material", "Refined Material", ItemType.Misc).register()
val sword = ItemSubType("sword", "Sword", ItemType.Weapon).register()
val chest_armor = ItemSubType("chest_armor", "Chest Armor", ItemType.Armor).register()
val potion = ItemSubType("potion", "Potion", ItemType.Consumable).register()

// Items
Item(
    id = "wood", name = "Wood",
    subType = raw_material, weight = 1
).register()

Item(
    "iron_ore", "Iron Ore",
    raw_material, 1
).register()

val ironIngot = Item(
    "iron_ingot", "Iron Ingot",
    refined_material, 1
).register()

Item(
    "iron_sword", "Iron Sword",
    sword, 1,
    Attributes().add(Stat.BODY, 3)
).register()

Item(
    "chest_plate", "Chest Plate",
    chest_armor, 5,
    Attributes().add(Stat.BODY, 3)
).register()

ConsumableItem(
    "health_potion", "Health Potion",
    potion, 1
) { input: ActionInput ->
    Utils.addItem(
        input,
        ironIngot,
        1
    )
}.register()
