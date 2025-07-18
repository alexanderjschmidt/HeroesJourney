import heroes.journey.modlib.Ids
import heroes.journey.modlib.attributes.attributes
import heroes.journey.modlib.items.ItemType
import heroes.journey.modlib.items.item
import heroes.journey.modlib.items.itemSubType

// Items - included by basegame mod

// Item Sub Types
val raw_material = itemSubType(Ids.ITEMSUBTYPE_RAW_MATERIAL, ItemType.Misc).register()
val refined_material = itemSubType(Ids.ITEMSUBTYPE_REFINED_MATERIAL, ItemType.Misc).register()
val sword = itemSubType(Ids.ITEMSUBTYPE_SWORD, ItemType.Weapon).register()
val chest_armor = itemSubType(Ids.ITEMSUBTYPE_CHEST_ARMOR, ItemType.Armor).register()
val potion = itemSubType(Ids.ITEMSUBTYPE_POTION, ItemType.Consumable).register()

// Items
item(
    id = Ids.ITEM_WOOD,
    subType = raw_material, weight = 1
).register()

item(
    id = Ids.ITEM_IRON_ORE,
    subType = raw_material, weight = 1
).register()

val ironIngot = item(
    id = Ids.ITEM_IRON_INGOT,
    subType = refined_material, weight = 1
).register()

item(
    id = Ids.ITEM_IRON_SWORD,
    subType = sword, weight = 1,
    attributes = attributes(Ids.STAT_BODY to 3)
).register()

item(
    id = Ids.ITEM_CHEST_PLATE,
    subType = chest_armor, weight = 5,
    attributes = attributes(Ids.STAT_BODY to 3)
).register()

/*
TODO this probably needs its own DSL
ConsumableItem(
    Ids.ITEM_HEALTH_POTION,
    potion, 1
) { input: ActionInput ->
    Utils.addItem(
        input,
        ironIngot,
        1
    )
}.register()
*/
