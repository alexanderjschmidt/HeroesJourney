import heroes.journey.entities.actions.inputs.ActionInput
import heroes.journey.entities.items.ConsumableItem
import heroes.journey.entities.items.Item
import heroes.journey.entities.items.ItemSubType
import heroes.journey.entities.items.ItemType
import heroes.journey.entities.tagging.Attributes
import heroes.journey.initializers.base.Items
import heroes.journey.initializers.base.tags.DamageTypes
import heroes.journey.initializers.base.tags.DefenseTypes
import heroes.journey.initializers.utils.Utils
import heroes.journey.mods.GameMod

val mod = object : GameMod("Base Game") {
    override fun onLoad(context: ModContext) {
        // Item Sub Types
        val raw_material = ItemSubType("raw_material", "Raw Material", ItemType.Misc).register()
        val refined_material =
            ItemSubType("refined_material", "Refined Material", ItemType.Misc).register()
        val sword = ItemSubType("sword", "Sword", ItemType.Weapon).register()
        val chest_armor = ItemSubType("chest_armor", "Chest Armor", ItemType.Armor).register()
        val potion = ItemSubType("potion", "Potion", ItemType.Consumable).register()
        context.log("Loaded item sub types")

        // Items
        Item(
            id = "wood", name = "Wood",
            subType = raw_material, weight = 1
        ).register()

        Items.ironOre = Item(
            "iron_ore", "Iron Ore",
            raw_material, 1
        ).register()

        Items.ironIngot = Item(
            "iron_ingot", "Iron Ingot",
            refined_material, 1
        ).register()

        Items.ironSword = Item(
            "iron_sword", "Iron Sword",
            sword, 1,
            Attributes().add(DamageTypes.PHYSICAL, 3)
        ).register()

        Items.chestPlate = Item(
            "chest_plate", "Chest Plate",
            chest_armor, 5,
            Attributes().add(DefenseTypes.PHYSICAL_DEF, 3)
        ).register()

        Items.healthPotion = ConsumableItem(
            "health_potion", "Health Potion",
            potion, 1
        ) { input: ActionInput ->
            Utils.addItem(
                input,
                Items.ironIngot,
                1
            )
        }.register()
        context.log("Loaded items")
    }
}

mod
