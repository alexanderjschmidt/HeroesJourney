import heroes.journey.entities.challenge
import heroes.journey.entities.tagging.Stat
import heroes.journey.modlib.Ids.*

// Holy Challenges - included by basegame mod
challenge("aid_resolute_angel") {
    name = "Aid Resolute Angel"
    description =
        "A celestial angel is fighting dark forces. Use chivalry, empowerment, or illusion to aid it."
    render = RESOLUTE_ANGEL
    approaches(Stat.CHIVALRY, Stat.EMPOWERMENT, Stat.ILLUSION)
    reward { attr("valor", 2) }
}.register()
challenge("bless_blessed_gladiator") {
    name = "Bless Blessed Gladiator"
    description =
        "A holy warrior seeks divine blessing. Use concentration, enchanting, or persuasion to bless it."
    render = BLESSED_GLADIATOR
    approaches(Stat.CONCENTRATION, Stat.ENCHANTING, Stat.PERSUASION)
    reward { attr("valor", 2) }
}.register()
challenge("train_bold_man_at_arms") {
    name = "Train Bold Man at Arms"
    description = "A brave soldier needs combat training. Use charm, chivalry, or technique to train it."
    render = BOLD_MAN_AT_ARMS
    approaches(Stat.CHARM, Stat.CHIVALRY, Stat.TECHNIQUE)
    reward { attr("valor", 2) }
}.register()
challenge("commune_with_divine_planetar") {
    name = "Commune with Divine Planetar"
    description =
        "A powerful celestial being offers wisdom. Use charm, concentration, or sorcery to commune."
    render = DIVINE_PLANETAR
    approaches(Stat.CHARM, Stat.CONCENTRATION, Stat.SORCERY)
    reward { attr("arcanum", 3) }
}.register()
challenge("guide_devout_acolyte") {
    name = "Guide Devout Acolyte"
    description =
        "A young priest seeks spiritual guidance. Use concentration, illusion, or persuasion to guide it."
    render = DEVOUT_ACOLYTE
    approaches(Stat.CONCENTRATION, Stat.ILLUSION, Stat.PERSUASION)
    reward { attr("influence", 2) }
}.register()
challenge("heal_favored_cleric") {
    name = "Heal Favored Cleric"
    description =
        "A holy cleric is wounded and needs healing. Use empowerment, enchanting, or logic to heal it."
    render = FAVORED_CLERIC
    approaches(Stat.EMPOWERMENT, Stat.ENCHANTING, Stat.LOGIC)
    reward { attr("arcanum", 2) }
}.register()
challenge("befriend_floating_cherub") {
    name = "Befriend Floating Cherub"
    description =
        "A small angelic being is curious about you. Use bewitching magic, charm, or persuasion to befriend it."
    render = FLOATING_CHERUB
    approaches(Stat.BEWITCHING, Stat.CHARM, Stat.PERSUASION)
    reward { attr("influence", 2) }
}.register()
challenge("guide_gentle_shepard") {
    name = "Guide Gentle Shepard"
    description = "A holy shepherd guides lost souls. Use charm, logic, or mesmerism to guide it."
    render = GENTLE_SHEPARD
    approaches(Stat.CHARM, Stat.LOGIC, Stat.MESMERISM)
    reward { attr("influence", 2) }
}.register()
challenge("join_holy_crusader") {
    name = "Join Holy Crusader"
    description =
        "A holy warrior invites you to join a sacred quest. Use charm, chivalry, or might to join it."
    render = HOLY_CRUSADER
    approaches(Stat.CHARM, Stat.CHIVALRY, Stat.MIGHT)
    reward { attr("valor", 2) }
}.register()
challenge("learn_from_jovial_friar") {
    name = "Learn from Jovial Friar"
    description =
        "A cheerful monk offers spiritual teachings. Use charm, logic, or persuasion to learn from it."
    render = JOVIAL_FRIAR
    approaches(Stat.CHARM, Stat.LOGIC, Stat.PERSUASION)
    reward { attr("insight", 2) }
}.register()
challenge("commune_with_righteous_deva") {
    name = "Commune with Righteous Deva"
    description = "A divine being offers celestial wisdom. Use charm, logic, or sorcery to commune."
    render = RIGHTEOUS_DEVA
    approaches(Stat.CHARM, Stat.LOGIC, Stat.SORCERY)
    reward { attr("arcanum", 3) }
}.register()
challenge("train_with_sword_archon") {
    name = "Train with Sword Archon"
    description = "A celestial warrior offers combat training. Use logic, skill, or technique to train."
    render = SWORD_ARCHON
    approaches(Stat.LOGIC, Stat.SKILL, Stat.TECHNIQUE)
    reward { attr("valor", 2) }
}.register()
challenge("honor_veteran_swordsman") {
    name = "Honor Veteran Swordsman"
    description =
        "A battle-hardened holy warrior seeks recognition. Use charm, chivalry, or might to honor it."
    render = VETERAN_SWORDSMAN
    approaches(Stat.CHARM, Stat.CHIVALRY, Stat.MIGHT)
    reward { attr("valor", 2) }
}.register()
challenge("guide_zealous_priest") {
    name = "Guide Zealous Priest"
    description =
        "A passionate priest seeks spiritual direction. Use bravado, mesmerism, or persuasion to guide it."
    render = ZEALOUS_PRIEST
    approaches(Stat.BRAVADO, Stat.MESMERISM, Stat.PERSUASION)
    reward { attr("influence", 2) }
}.register()
challenge("inspire_determined_soldier") {
    name = "Inspire Determined Soldier"
    description =
        "A holy soldier needs motivation for battle. Use bravado, logic, or mesmerism to inspire it."
    render = DETERMINED_SOLDIER
    approaches(Stat.BRAVADO, Stat.LOGIC, Stat.MESMERISM)
    reward { attr("valor", 2) }
}.register()
