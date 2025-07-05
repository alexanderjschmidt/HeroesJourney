import heroes.journey.entities.Buff
import heroes.journey.entities.Quest
import heroes.journey.entities.actions.ActionInput
import heroes.journey.entities.challenge
import heroes.journey.initializers.base.Ids.*
import heroes.journey.initializers.base.actions.DelveAction
import heroes.journey.entities.tagging.Stat
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
        approaches(Stat.MIGHT, Stat.EMPOWERMENT, Stat.SKILL, Stat.BRAVADO)
        reward {
            attr("valor", 2)
        }
    }.register()

    challenge("exterminate_vermin") {
        name = "Exterminate Vermin"
        description = "Get rid of tunneling moles."
        render = TUNNELING_MOLE
        approaches(Stat.LOGIC, Stat.CUNNING, Stat.TECHNIQUE, Stat.RUNE_CRAFT)
        reward {
            attr("insight", 2)
        }
    }.register()

    challenge("cure_curse") {
        name = "Cure Curse"
        description = "Remove a curse from a local townsfolk."
        render = BOUND_CADAVER
        approaches(Stat.WIZARDRY, Stat.SORCERY, Stat.ILLUSION, Stat.ENCHANTING)
        reward {
            attr("arcanum", 2)
        }
    }.register()

    challenge("resolve_dispute") {
        name = "Resolve Dispute"
        description = "Resolve a dispute between disgruntled townsfolk."
        render = OVERWORKED_VILLAGER
        approaches(Stat.CHARM, Stat.INTIMIDATION, Stat.PERSUASION, Stat.SEDUCTION)
        reward {
            attr("influence", 2)
        }
    }.register()

    Buff("rested", "Rested", 1, 0).register();
}
