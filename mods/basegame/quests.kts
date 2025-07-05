import heroes.journey.entities.Buff
import heroes.journey.entities.Quest
import heroes.journey.entities.actions.ActionInput
import heroes.journey.entities.challenge
import heroes.journey.initializers.base.Ids.*
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

    challenge("fight_monsters") {
        name = "Fight Monsters"
        description = "Kill some monsters terrorizing the locals."
        render = BRAWNY_OGRE
        approaches(Stats.MIGHT, Stats.EMPOWERMENT, Stats.SKILL, Stats.BRAVADO)
        reward {
            attr("valor", 2)
        }
    }.register()

    challenge("exterminate_vermin") {
        name = "Exterminate Vermin"
        description = "Get rid of tunneling moles."
        render = TUNNELING_MOLE
        approaches(Stats.LOGIC, Stats.CUNNING, Stats.TECHNIQUE, Stats.RUNE_CRAFT)
        reward {
            attr("insight", 2)
        }
    }.register()

    challenge("cure_curse") {
        name = "Cure Curse"
        description = "Remove a curse from a local townsfolk."
        render = BOUND_CADAVER
        approaches(Stats.WIZARDRY, Stats.SORCERY, Stats.ILLUSION, Stats.ENCHANTING)
        reward {
            attr("arcanum", 2)
        }
    }.register()

    challenge("resolve_dispute") {
        name = "Resolve Dispute"
        description = "Resolve a dispute between disgruntled townsfolk."
        render = OVERWORKED_VILLAGER
        approaches(Stats.CHARM, Stats.INTIMIDATION, Stats.PERSUASION, Stats.SEDUCTION)
        reward {
            attr("influence", 2)
        }
    }.register()

    Buff("rested", "Rested", 1, 0).register();
}
