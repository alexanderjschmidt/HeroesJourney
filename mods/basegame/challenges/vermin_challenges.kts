import heroes.journey.entities.challenge
import heroes.journey.entities.tagging.Stat
import heroes.journey.modlib.Ids.*

// Vermin Challenges - included by basegame mod
challenge("defeat_acid_ant") {
    render = ACID_ANT
    approaches(Stat.CUNNING, Stat.SKILL, Stat.TECHNIQUE)
    reward { attr("valor", 2) }
}.register()
challenge("defeat_bloated_bedbug") {
    render = BLOATED_BEDBUG
    approaches(Stat.BRAVADO, Stat.MIGHT, Stat.SKILL)
    reward { attr("valor", 2) }
}.register()
challenge("defeat_dung_beetle") {
    render = DUNG_BEETLE
    approaches(Stat.MIGHT, Stat.SKILL, Stat.TECHNIQUE)
    reward { attr("valor", 2) }
}.register()
challenge("defeat_engorged_tick") {
    render = ENGORGED_TICK
    approaches(Stat.BRAVADO, Stat.ENCHANTING, Stat.MIGHT)
    reward { attr("valor", 2) }
}.register()
challenge("defeat_famished_tick") {
    render = FAMISHED_TICK
    approaches(Stat.CUNNING, Stat.SKILL, Stat.TECHNIQUE)
    reward { attr("valor", 2) }
}.register()
challenge("defeat_foraging_maggot") {
    render = FORAGING_MAGGOT
    approaches(Stat.BRAVADO, Stat.MIGHT, Stat.SKILL)
    reward { attr("valor", 2) }
}.register()
challenge("defeat_plague_bat") {
    render = PLAGUE_BAT
    approaches(Stat.BEWITCHING, Stat.ILLUSION, Stat.PERSUASION)
    reward { attr("arcanum", 2) }
}.register()
challenge("defeat_infected_mouse") {
    render = INFECTED_MOUSE
    approaches(Stat.CUNNING, Stat.SKILL, Stat.TECHNIQUE)
    reward { attr("valor", 2) }
}.register()
challenge("defeat_lava_ant") {
    render = LAVA_ANT
    approaches(Stat.BEWITCHING, Stat.EMPOWERMENT, Stat.LOGIC)
    reward { attr("arcanum", 2) }
}.register()
challenge("defeat_mawing_beaver") {
    render = MAWING_BEAVER
    approaches(Stat.MIGHT, Stat.SKILL, Stat.TECHNIQUE)
    reward { attr("valor", 2) }
}.register()
challenge("defeat_rhino_beetle") {
    render = RHINO_BEETLE
    approaches(Stat.BRAVADO, Stat.ENCHANTING, Stat.MIGHT)
    reward { attr("valor", 2) }
}.register()
challenge("defeat_soldier_ant") {
    render = SOLDIER_ANT
    approaches(Stat.BRAVADO, Stat.MIGHT, Stat.SKILL)
    reward { attr("valor", 2) }
}.register()
challenge("defeat_tainted_cockroach") {
    render = TAINTED_COCKROACH
    approaches(Stat.BRAVADO, Stat.MIGHT, Stat.SKILL)
    reward { attr("valor", 2) }
}.register()
challenge("defeat_swooping_bat") {
    render = SWOOPING_BAT
    approaches(Stat.CUNNING, Stat.SKILL, Stat.TECHNIQUE)
    reward { attr("valor", 2) }
}.register()
challenge("defeat_tunneling_mole") {
    render = TUNNELING_MOLE
    approaches(Stat.CUNNING, Stat.SKILL, Stat.TECHNIQUE)
    reward { attr("valor", 2) }
}.register()
