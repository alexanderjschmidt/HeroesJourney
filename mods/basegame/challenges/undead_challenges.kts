import heroes.journey.entities.challenge
import heroes.journey.entities.tagging.Stat
import heroes.journey.initializers.Ids.*

// Undead Challenges - included by basegame mod
challenge("lay_bound_cadaver") {
    name = "Lay Bound Cadaver to Rest"
    description =
        "A bound cadaver is haunting the graveyard. Use concentration, logic, or sorcery to lay it to rest."
    render = BOUND_CADAVER
    approaches(Stat.CONCENTRATION, Stat.LOGIC, Stat.SORCERY)
    reward { attr("arcanum", 2) }
}.register()
challenge("defeat_brittle_archer") {
    name = "Defeat Brittle Archer"
    description =
        "A skeletal archer is shooting travelers. Use cunning, skill, or technique to defeat it."
    render = BRITTLE_ARCHER
    approaches(Stat.CUNNING, Stat.SKILL, Stat.TECHNIQUE)
    reward { attr("valor", 2) }
}.register()
challenge("defeat_carcass_feeder") {
    name = "Defeat Carcass Feeder"
    description = "A ghoul is feeding on corpses. Use bravado, enchanting, or might to defeat it."
    render = CARCASS_FEEDER
    approaches(Stat.BRAVADO, Stat.ENCHANTING, Stat.MIGHT)
    reward { attr("valor", 2) }
}.register()
challenge("destroy_decrepit_bones") {
    name = "Destroy Decrepit Bones"
    description =
        "Animated bones are attacking the village. Use bravado, might, or skill to destroy them."
    render = DECREPIT_BONES
    approaches(Stat.BRAVADO, Stat.MIGHT, Stat.SKILL)
    reward { attr("valor", 2) }
}.register()
challenge("defeat_dismembered_crawler") {
    name = "Defeat Dismembered Crawler"
    description = "A crawling undead is spreading disease. Use cunning, skill, or technique to defeat it."
    render = DISMEMBERED_CRAWLER
    approaches(Stat.CUNNING, Stat.SKILL, Stat.TECHNIQUE)
    reward { attr("valor", 2) }
}.register()
challenge("defeat_ghastly_eye") {
    name = "Defeat Ghastly Eye"
    description =
        "A ghastly eye is cursing the living. Use bewitching magic, illusion, or persuasion to defeat it."
    render = GHASTLY_EYE
    approaches(Stat.BEWITCHING, Stat.ILLUSION, Stat.PERSUASION)
    reward { attr("arcanum", 2) }
}.register()
challenge("defeat_grave_revenant") {
    name = "Defeat Grave Revenant"
    description = "A revenant seeks vengeance. Use concentration, logic, or sorcery to defeat it."
    render = GRAVE_REVENANT
    approaches(Stat.CONCENTRATION, Stat.LOGIC, Stat.SORCERY)
    reward { attr("arcanum", 2) }
}.register()
challenge("defeat_mutilated_stumbler") {
    name = "Defeat Mutilated Stumbler"
    description = "A mutilated zombie is spreading plague. Use bravado, might, or skill to defeat it."
    render = MUTILATED_STUMBLER
    approaches(Stat.BRAVADO, Stat.MIGHT, Stat.SKILL)
    reward { attr("valor", 2) }
}.register()
challenge("defeat_sand_ghoul") {
    name = "Defeat Sand Ghoul"
    description = "A ghoul is hunting in the desert. Use bravado, might, or skill to defeat it."
    render = SAND_GHOUL
    approaches(Stat.BRAVADO, Stat.MIGHT, Stat.SKILL)
    reward { attr("valor", 2) }
}.register()
challenge("defeat_skittering_hand") {
    name = "Defeat Skittering Hand"
    description = "A severed hand is crawling around. Use cunning, skill, or technique to defeat it."
    render = SKITTERING_HAND
    approaches(Stat.CUNNING, Stat.SKILL, Stat.TECHNIQUE)
    reward { attr("valor", 2) }
}.register()
challenge("defeat_unraveling_crawler") {
    name = "Defeat Unraveling Crawler"
    description = "A decaying undead is falling apart. Use cunning, skill, or technique to defeat it."
    render = UNRAVELING_CRAWLER
    approaches(Stat.CUNNING, Stat.SKILL, Stat.TECHNIQUE)
    reward { attr("valor", 2) }
}.register()
challenge("defeat_vampire_bat") {
    name = "Defeat Vampire Bat"
    description =
        "A vampire bat is draining blood. Use bewitching magic, illusion, or persuasion to defeat it."
    render = VAMPIRE_BAT
    approaches(Stat.BEWITCHING, Stat.ILLUSION, Stat.PERSUASION)
    reward { attr("arcanum", 2) }
}.register()
challenge("defeat_giant_royal_scarab") {
    name = "Defeat Giant Royal Scarab"
    description =
        "A giant scarab is guarding ancient treasures. Use bravado, might, or skill to defeat it."
    render = GIANT_ROYAL_SCARAB
    approaches(Stat.BRAVADO, Stat.MIGHT, Stat.SKILL)
    reward { attr("valor", 2) }
}.register()
challenge("defeat_royal_scarab") {
    name = "Defeat Royal Scarab"
    description =
        "A royal scarab is protecting sacred artifacts. Use cunning, skill, or technique to defeat it."
    render = ROYAL_SCARAB
    approaches(Stat.CUNNING, Stat.SKILL, Stat.TECHNIQUE)
    reward { attr("valor", 2) }
}.register()
challenge("defeat_toxic_hound") {
    name = "Defeat Toxic Hound"
    description = "A toxic hound is poisoning the water. Use bravado, enchanting, or might to defeat it."
    render = TOXIC_HOUND
    approaches(Stat.BRAVADO, Stat.ENCHANTING, Stat.MIGHT)
    reward { attr("valor", 2) }
}.register()
