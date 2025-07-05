import heroes.journey.entities.challenge
import heroes.journey.entities.tagging.Stat
import heroes.journey.initializers.base.Ids.*
import heroes.journey.mods.gameMod

gameMod("Dragon Challenges", 0) {
    challenge("befriend_aqua_drake") {
        name = "Befriend Aqua Drake"
        description =
            "A graceful water dragon swims in the lake. Use bewitching magic, charm, or enchanting to befriend it."
        render = AQUA_DRAKE
        approaches(Stat.BEWITCHING, Stat.CHARM, Stat.ENCHANTING)
        reward { attr("influence", 2) }
    }.register()
    challenge("rescue_baby_brass_dragon") {
        name = "Rescue Baby Brass Dragon"
        description =
            "A young brass dragon is trapped in a cave. Use bravado, chivalry, or logic to rescue it."
        render = BABY_BRASS_DRAGON
        approaches(Stat.BRAVADO, Stat.CHIVALRY, Stat.LOGIC)
        reward { attr("valor", 2) }
    }.register()
    challenge("heal_baby_copper_dragon") {
        name = "Heal Baby Copper Dragon"
        description =
            "A sick copper dragon needs magical healing. Use concentration, illusion, or sorcery to cure it."
        render = BABY_COPPER_DRAGON
        approaches(Stat.CONCENTRATION, Stat.ILLUSION, Stat.SORCERY)
        reward { attr("arcanum", 2) }
    }.register()
    challenge("guide_baby_green_dragon") {
        name = "Guide Baby Green Dragon"
        description =
            "A young green dragon is lost in the forest. Use enchanting magic, mesmerism, or persuasion to guide it home."
        render = BABY_GREEN_DRAGON
        approaches(Stat.ENCHANTING, Stat.MESMERISM, Stat.PERSUASION)
        reward { attr("influence", 2) }
    }.register()
    challenge("rescue_baby_white_dragon") {
        name = "Rescue Baby White Dragon"
        description = "A white dragon cub is caught in an ice storm. Use charm, might, or skill to rescue it."
        render = BABY_WHITE_DRAGON
        approaches(Stat.CHARM, Stat.MIGHT, Stat.SKILL)
        reward { attr("valor", 2) }
    }.register()
    challenge("train_juvenile_bronze_dragon") {
        name = "Train Juvenile Bronze Dragon"
        description =
            "A young bronze dragon needs training in combat. Use cunning, logic, or technique to train it."
        render = JUVENILE_BRONZE_DRAGON
        approaches(Stat.CUNNING, Stat.LOGIC, Stat.TECHNIQUE)
        reward { attr("valor", 2) }
    }.register()
    challenge("challenge_mature_bronze_dragon") {
        name = "Challenge Mature Bronze Dragon"
        description =
            "A powerful bronze dragon tests your worthiness. Use charm, chivalry, or might to prove yourself."
        render = MATURE_BRONZE_DRAGON
        approaches(Stat.CHARM, Stat.CHIVALRY, Stat.MIGHT)
        reward { attr("valor", 3) }
    }.register()
    challenge("defeat_mud_wyvern") {
        name = "Defeat Mud Wyvern"
        description =
            "A wyvern lurks in the swamp, attacking travelers. Use illusion, skill, or technique to defeat it."
        render = MUD_WYVERN
        approaches(Stat.ILLUSION, Stat.SKILL, Stat.TECHNIQUE)
        reward { attr("valor", 2) }
    }.register()
    challenge("outsmart_poison_drake") {
        name = "Outsmart Poison Drake"
        description =
            "A venomous drake is poisoning the water supply. Use bewitching magic, cunning, or logic to outsmart it."
        render = POISON_DRAKE
        approaches(Stat.BEWITCHING, Stat.CUNNING, Stat.LOGIC)
        reward { attr("insight", 2) }
    }.register()
    challenge("befriend_pygmy_wyvern") {
        name = "Befriend Pygmy Wyvern"
        description =
            "A small wyvern is curious about you. Use charm, mesmerism, or persuasion to befriend it."
        render = PYGMY_WYVERN
        approaches(Stat.CHARM, Stat.MESMERISM, Stat.PERSUASION)
        reward { attr("influence", 2) }
    }.register()
    challenge("guide_viridian_drake") {
        name = "Guide Viridian Drake"
        description =
            "A green drake is lost in the jungle. Use bewitching magic, enchanting, or logic to guide it."
        render = VIRIDIAN_DRAKE
        approaches(Stat.BEWITCHING, Stat.ENCHANTING, Stat.LOGIC)
        reward { attr("influence", 2) }
    }.register()
    challenge("challenge_young_brass_dragon") {
        name = "Challenge Young Brass Dragon"
        description =
            "A young brass dragon challenges you to a test of strength. Use bravado, charm, or might to win."
        render = YOUNG_BRASS_DRAGON
        approaches(Stat.BRAVADO, Stat.CHARM, Stat.MIGHT)
        reward { attr("valor", 2) }
    }.register()
    challenge("defeat_young_red_dragon") {
        name = "Defeat Young Red Dragon"
        description =
            "A fierce red dragon is burning villages. Use bravado, charm, or empowerment to defeat it."
        render = YOUNG_RED_DRAGON
        approaches(Stat.BRAVADO, Stat.CHARM, Stat.EMPOWERMENT)
        reward { attr("valor", 3) }
    }.register()
    challenge("survive_adult_white_dragon") {
        name = "Survive Adult White Dragon"
        description = "A massive white dragon is hunting you. Use empowerment, logic, or might to survive."
        render = ADULT_WHITE_DRAGON
        approaches(Stat.EMPOWERMENT, Stat.LOGIC, Stat.MIGHT)
        reward { attr("valor", 3) }
    }.register()
    challenge("negotiate_adult_green_dragon") {
        name = "Negotiate with Adult Green Dragon"
        description =
            "A wise green dragon guards ancient knowledge. Use charm, mesmerism, or persuasion to negotiate."
        render = ADULT_GREEN_DRAGON
        approaches(Stat.CHARM, Stat.MESMERISM, Stat.PERSUASION)
        reward { attr("influence", 3) }
    }.register()
}
