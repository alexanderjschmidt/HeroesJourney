import heroes.journey.entities.Buff
import heroes.journey.entities.Quest
import heroes.journey.entities.actions.ActionInput
import heroes.journey.initializers.utils.Utils
import heroes.journey.mods.gameMod
import heroes.journey.registries.Registries

gameMod("Base Game Quests and Buffs", 1) {
    Quest(
        "delve_dungeon", "Delve a dungeon",
        { input: ActionInput? ->
            Utils.addItem(
                input,
                Registries.ItemManager["iron_sword"], 1
            )
        },
        { input: ActionInput ->
            Utils.justCompletedAction(
                input.gameState, input.entityId,
                "delve"
            )
        }, 10
    ).register()

    Buff("rested", "Rested", 1, 0).register();
}
