import heroes.journey.entities.actions.ActionInput
import heroes.journey.entities.items.ConsumableItem
import heroes.journey.entities.items.Item
import heroes.journey.entities.items.ItemSubType
import heroes.journey.entities.items.ItemType
import heroes.journey.entities.tagging.Attributes
import heroes.journey.modlib.Ids
import heroes.journey.utils.gamestate.Utils

// Items - included by basegame mod

// Item Sub Types
val raw_material = ItemSubType("raw_material", ItemType.Misc).register()
val refined_material =
    ItemSubType("refined_material", ItemType.Misc).register()
val sword = ItemSubType("sword", ItemType.Weapon).register()
val chest_armor = ItemSubType("chest_armor", ItemType.Armor).register()
val potion = ItemSubType("potion", ItemType.Consumable).register()

// Items
Item(
    id = "wood",
    subType = raw_material, weight = 1
).register()

Item(
    "iron_ore",
    raw_material, 1
).register()

val ironIngot = Item(
    "iron_ingot",
    refined_material, 1
).register()

Item(
    "iron_sword",
    sword, 1,
    Attributes().add(Ids.STAT_BODY, 3)
).register()

Item(
    "chest_plate",
    chest_armor, 5,
    Attributes().add(Ids.STAT_BODY, 3)
).register()

ConsumableItem(
    "health_potion",
    potion, 1
) { input: ActionInput ->
    Utils.addItem(
        input,
        ironIngot,
        1
    )
}.register()
