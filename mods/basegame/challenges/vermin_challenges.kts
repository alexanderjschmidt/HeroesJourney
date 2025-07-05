import heroes.journey.entities.challenge
import heroes.journey.entities.tagging.Stat
import heroes.journey.initializers.base.Ids.*
import heroes.journey.mods.gameMod

gameMod("Vermin Challenges", 0) {
    challenge("defeat_acid_ant") {
        name = "Defeat Acid Ant"
        description = "An acid-spitting ant is burning crops. Use cunning, skill, or technique to defeat it."
        render = ACID_ANT
        approaches(Stat.CUNNING, Stat.SKILL, Stat.TECHNIQUE)
        reward { attr("valor", 2) }
    }.register()
    challenge("defeat_bloated_bedbug") {
        name = "Defeat Bloated Bedbug"
        description = "A bloated bedbug is infesting the inn. Use bravado, might, or skill to defeat it."
        render = BLOATED_BEDBUG
        approaches(Stat.BRAVADO, Stat.MIGHT, Stat.SKILL)
        reward { attr("valor", 2) }
    }.register()
    challenge("defeat_dung_beetle") {
        name = "Defeat Dung Beetle"
        description =
            "A giant dung beetle is rolling dung into town. Use might, skill, or technique to defeat it."
        render = DUNG_BEETLE
        approaches(Stat.MIGHT, Stat.SKILL, Stat.TECHNIQUE)
        reward { attr("valor", 2) }
    }.register()
    challenge("defeat_engorged_tick") {
        name = "Defeat Engorged Tick"
        description =
            "An engorged tick is draining livestock. Use bravado, enchanting, or might to defeat it."
        render = ENGORGED_TICK
        approaches(Stat.BRAVADO, Stat.ENCHANTING, Stat.MIGHT)
        reward { attr("valor", 2) }
    }.register()
    challenge("defeat_famished_tick") {
        name = "Defeat Famished Tick"
        description = "A hungry tick is hunting for blood. Use cunning, skill, or technique to defeat it."
        render = FAMISHED_TICK
        approaches(Stat.CUNNING, Stat.SKILL, Stat.TECHNIQUE)
        reward { attr("valor", 2) }
    }.register()
    challenge("defeat_foraging_maggot") {
        name = "Defeat Foraging Maggot"
        description = "A maggot is eating stored food. Use bravado, might, or skill to defeat it."
        render = FORAGING_MAGGOT
        approaches(Stat.BRAVADO, Stat.MIGHT, Stat.SKILL)
        reward { attr("valor", 2) }
    }.register()
    challenge("defeat_plague_bat") {
        name = "Defeat Plague Bat"
        description = "A plague bat is spreading disease. Use bewitching magic, illusion, or persuasion to defeat it."
        render = PLAGUE_BAT
        approaches(Stat.BEWITCHING, Stat.ILLUSION, Stat.PERSUASION)
        reward { attr("arcanum", 2) }
    }.register()
    challenge("defeat_infected_mouse") {
        name = "Defeat Infected Mouse"
        description = "An infected mouse is spreading disease. Use cunning, skill, or technique to defeat it."
        render = INFECTED_MOUSE
        approaches(Stat.CUNNING, Stat.SKILL, Stat.TECHNIQUE)
        reward { attr("valor", 2) }
    }.register()
    challenge("defeat_lava_ant") {
        name = "Defeat Lava Ant"
        description =
            "A lava ant is burning the forest. Use bewitching magic, empowerment, or logic to defeat it."
        render = LAVA_ANT
        approaches(Stat.BEWITCHING, Stat.EMPOWERMENT, Stat.LOGIC)
        reward { attr("arcanum", 2) }
    }.register()
    challenge("defeat_mawing_beaver") {
        name = "Defeat Mawing Beaver"
        description = "A beaver is destroying the dam. Use might, skill, or technique to defeat it."
        render = MAWING_BEAVER
        approaches(Stat.MIGHT, Stat.SKILL, Stat.TECHNIQUE)
        reward { attr("valor", 2) }
    }.register()
    challenge("defeat_rhino_beetle") {
        name = "Defeat Rhino Beetle"
        description =
            "A rhino beetle is charging through town. Use bravado, enchanting, or might to defeat it."
        render = RHINO_BEETLE
        approaches(Stat.BRAVADO, Stat.ENCHANTING, Stat.MIGHT)
        reward { attr("valor", 2) }
    }.register()
    challenge("defeat_soldier_ant") {
        name = "Defeat Soldier Ant"
        description = "A soldier ant is attacking travelers. Use bravado, might, or skill to defeat it."
        render = SOLDIER_ANT
        approaches(Stat.BRAVADO, Stat.MIGHT, Stat.SKILL)
        reward { attr("valor", 2) }
    }.register()
    challenge("defeat_tainted_cockroach") {
        name = "Defeat Tainted Cockroach"
        description = "A tainted cockroach is spreading poison. Use bravado, might, or skill to defeat it."
        render = TAINTED_COCKROACH
        approaches(Stat.BRAVADO, Stat.MIGHT, Stat.SKILL)
        reward { attr("valor", 2) }
    }.register()
    challenge("defeat_swooping_bat") {
        name = "Defeat Swooping Bat"
        description = "A swooping bat is attacking travelers. Use cunning, skill, or technique to defeat it."
        render = SWOOPING_BAT
        approaches(Stat.CUNNING, Stat.SKILL, Stat.TECHNIQUE)
        reward { attr("valor", 2) }
    }.register()
    challenge("defeat_tunneling_mole") {
        name = "Defeat Tunneling Mole"
        description =
            "A tunneling mole is undermining buildings. Use cunning, skill, or technique to defeat it."
        render = TUNNELING_MOLE
        approaches(Stat.CUNNING, Stat.SKILL, Stat.TECHNIQUE)
        reward { attr("valor", 2) }
    }.register()
}
