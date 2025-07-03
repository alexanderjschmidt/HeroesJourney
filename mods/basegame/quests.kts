import heroes.journey.entities.Buff
import heroes.journey.entities.Challenge
import heroes.journey.entities.Quest
import heroes.journey.entities.actions.ActionInput
import heroes.journey.entities.tagging.Attributes
import heroes.journey.initializers.base.actions.DelveAction
import heroes.journey.initializers.base.tags.Stats
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
                DelveAction.delve
            )
        }, 10
    ).register()

    Challenge(
        "fight_monsters",
        "Fight Monsters",
        "Kill some monsters terrorizing the locals.",
        Attributes().add(Stats.VALOR, 2)
    ).register()

    Challenge(
        "solve_mystery",
        "Solve Mystery",
        "Solve a mystery for some locals.",
        Attributes().add(Stats.INSIGHT, 2)
    ).register()

    Challenge(
        "cure_curse",
        "Cure Curse",
        "Remove a curse from a local townsfolk.",
        Attributes().add(Stats.ARCANUM, 2)
    ).register()

    Challenge(
        "resolve_dispute",
        "Resolve Dispute",
        "Resolve a dispute between disgruntled townsfolk.",
        Attributes().add(Stats.INFLUENCE, 2)
    ).register()

    Buff("rested", "Rested", 1, 0).register();
}
