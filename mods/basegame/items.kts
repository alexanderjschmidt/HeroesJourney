import heroes.journey.modlib.Ids
import heroes.journey.modlib.items.ItemType
import heroes.journey.modlib.items.item
import heroes.journey.modlib.items.itemSubType

// Items - included by basegame mod

// Item Sub Types
itemSubType {
    id = Ids.ITEMSUBTYPE_RAW_MATERIAL
    type = ItemType.Misc
}.register()
itemSubType {
    id = Ids.ITEMSUBTYPE_REFINED_MATERIAL
    type = ItemType.Misc
}.register()
itemSubType {
    id = Ids.ITEMSUBTYPE_SWORD
    type = ItemType.Weapon
}.register()
itemSubType {
    id = Ids.ITEMSUBTYPE_CHEST_ARMOR
    type = ItemType.Armor
}.register()
itemSubType {
    id = Ids.ITEMSUBTYPE_POTION
    type = ItemType.Consumable
}.register()

// Items
item {
    id = Ids.ITEM_WOOD
    subTypeId = Ids.ITEMSUBTYPE_RAW_MATERIAL
    weight = 1
}.register()

item {
    id = Ids.ITEM_IRON_ORE
    subTypeId = Ids.ITEMSUBTYPE_RAW_MATERIAL
    weight = 1
}.register()

val ironIngot = item {
    id = Ids.ITEM_IRON_INGOT
    subTypeId = Ids.ITEMSUBTYPE_REFINED_MATERIAL
    weight = 1
}.register()

item {
    id = Ids.ITEM_IRON_SWORD
    subTypeId = Ids.ITEMSUBTYPE_SWORD
    weight = 1
    attribute(Ids.STAT_BODY, 3)
}.register()

item {
    id = Ids.ITEM_CHEST_PLATE
    subTypeId = Ids.ITEMSUBTYPE_CHEST_ARMOR
    weight = 5
    attribute(Ids.STAT_BODY, 3)
}.register()

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
