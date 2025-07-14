import heroes.journey.entities.challenge
import heroes.journey.entities.tagging.Stat
import heroes.journey.modlib.Ids.*

// Holy Challenges - included by basegame mod
challenge("aid_resolute_angel") {
    render = RESOLUTE_ANGEL
    approaches(Stat.CHIVALRY, Stat.EMPOWERMENT, Stat.ILLUSION)
    reward { attr("valor", 2) }
}.register()
challenge("bless_blessed_gladiator") {
    render = BLESSED_GLADIATOR
    approaches(Stat.CONCENTRATION, Stat.ENCHANTING, Stat.PERSUASION)
    reward { attr("valor", 2) }
}.register()
challenge("train_bold_man_at_arms") {
    render = BOLD_MAN_AT_ARMS
    approaches(Stat.CHARM, Stat.CHIVALRY, Stat.TECHNIQUE)
    reward { attr("valor", 2) }
}.register()
challenge("commune_with_divine_planetar") {
    render = DIVINE_PLANETAR
    approaches(Stat.CHARM, Stat.CONCENTRATION, Stat.SORCERY)
    reward { attr("arcanum", 3) }
}.register()
challenge("guide_devout_acolyte") {
    render = DEVOUT_ACOLYTE
    approaches(Stat.CONCENTRATION, Stat.ILLUSION, Stat.PERSUASION)
    reward { attr("influence", 2) }
}.register()
challenge("heal_favored_cleric") {
    render = FAVORED_CLERIC
    approaches(Stat.EMPOWERMENT, Stat.ENCHANTING, Stat.LOGIC)
    reward { attr("arcanum", 2) }
}.register()
challenge("befriend_floating_cherub") {
    render = FLOATING_CHERUB
    approaches(Stat.BEWITCHING, Stat.CHARM, Stat.PERSUASION)
    reward { attr("influence", 2) }
}.register()
challenge("guide_gentle_shepard") {
    render = GENTLE_SHEPARD
    approaches(Stat.CHARM, Stat.LOGIC, Stat.MESMERISM)
    reward { attr("influence", 2) }
}.register()
challenge("join_holy_crusader") {
    render = HOLY_CRUSADER
    approaches(Stat.CHARM, Stat.CHIVALRY, Stat.MIGHT)
    reward { attr("valor", 2) }
}.register()
challenge("learn_from_jovial_friar") {
    render = JOVIAL_FRIAR
    approaches(Stat.CHARM, Stat.LOGIC, Stat.PERSUASION)
    reward { attr("insight", 2) }
}.register()
challenge("commune_with_righteous_deva") {
    render = RIGHTEOUS_DEVA
    approaches(Stat.CHARM, Stat.LOGIC, Stat.SORCERY)
    reward { attr("arcanum", 3) }
}.register()
challenge("train_with_sword_archon") {
    render = SWORD_ARCHON
    approaches(Stat.LOGIC, Stat.SKILL, Stat.TECHNIQUE)
    reward { attr("valor", 2) }
}.register()
challenge("honor_veteran_swordsman") {
    render = VETERAN_SWORDSMAN
    approaches(Stat.CHARM, Stat.CHIVALRY, Stat.MIGHT)
    reward { attr("valor", 2) }
}.register()
challenge("guide_zealous_priest") {
    render = ZEALOUS_PRIEST
    approaches(Stat.BRAVADO, Stat.MESMERISM, Stat.PERSUASION)
    reward { attr("influence", 2) }
}.register()
challenge("inspire_determined_soldier") {
    render = DETERMINED_SOLDIER
    approaches(Stat.BRAVADO, Stat.LOGIC, Stat.MESMERISM)
    reward { attr("valor", 2) }
}.register()
