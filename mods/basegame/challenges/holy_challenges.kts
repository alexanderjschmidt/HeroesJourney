import heroes.journey.entities.challenge
import heroes.journey.modlib.Ids.*

// Holy Challenges - included by basegame mod
challenge("aid_resolute_angel") {
    render = RESOLUTE_ANGEL
    approaches("chivalry", "empowerment", "illusion")
    reward { attr("valor", 2) }
}.register()
challenge("bless_blessed_gladiator") {
    render = BLESSED_GLADIATOR
    approaches("concentration", "enchanting", "persuasion")
    reward { attr("valor", 2) }
}.register()
challenge("train_bold_man_at_arms") {
    render = BOLD_MAN_AT_ARMS
    approaches("charm", "chivalry", "technique")
    reward { attr("valor", 2) }
}.register()
challenge("commune_with_divine_planetar") {
    render = DIVINE_PLANETAR
    approaches("charm", "concentration", "sorcery")
    reward { attr("arcanum", 3) }
}.register()
challenge("guide_devout_acolyte") {
    render = DEVOUT_ACOLYTE
    approaches("concentration", "illusion", "persuasion")
    reward { attr("influence", 2) }
}.register()
challenge("heal_favored_cleric") {
    render = FAVORED_CLERIC
    approaches("empowerment", "enchanting", "logic")
    reward { attr("arcanum", 2) }
}.register()
challenge("befriend_floating_cherub") {
    render = FLOATING_CHERUB
    approaches("bewitching", "charm", "persuasion")
    reward { attr("influence", 2) }
}.register()
challenge("guide_gentle_shepard") {
    render = GENTLE_SHEPARD
    approaches("charm", "logic", "mesmerism")
    reward { attr("influence", 2) }
}.register()
challenge("join_holy_crusader") {
    render = HOLY_CRUSADER
    approaches("charm", "chivalry", "might")
    reward { attr("valor", 2) }
}.register()
challenge("learn_from_jovial_friar") {
    render = JOVIAL_FRIAR
    approaches("charm", "logic", "persuasion")
    reward { attr("insight", 2) }
}.register()
challenge("commune_with_righteous_deva") {
    render = RIGHTEOUS_DEVA
    approaches("charm", "logic", "sorcery")
    reward { attr("arcanum", 3) }
}.register()
challenge("train_with_sword_archon") {
    render = SWORD_ARCHON
    approaches("logic", "skill", "technique")
    reward { attr("valor", 2) }
}.register()
challenge("honor_veteran_swordsman") {
    render = VETERAN_SWORDSMAN
    approaches("charm", "chivalry", "might")
    reward { attr("valor", 2) }
}.register()
challenge("guide_zealous_priest") {
    render = ZEALOUS_PRIEST
    approaches("bravado", "mesmerism", "persuasion")
    reward { attr("influence", 2) }
}.register()
challenge("inspire_determined_soldier") {
    render = DETERMINED_SOLDIER
    approaches("bravado", "logic", "mesmerism")
    reward { attr("valor", 2) }
}.register()
