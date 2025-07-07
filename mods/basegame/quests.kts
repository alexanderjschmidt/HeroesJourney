import heroes.journey.entities.Buff
import heroes.journey.entities.Quest
import heroes.journey.entities.actions.ActionInput
import heroes.journey.utils.gamestate.Utils
import heroes.journey.registries.Registries

// Quests and Buffs - included by basegame mod

Quest(
    "delve_dungeon", "Delve a dungeon",
    { input: ActionInput? ->
        Utils.addItem(
            input,
            Registries.ItemManager.get("iron_sword"), 1
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
