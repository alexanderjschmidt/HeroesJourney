import heroes.journey.entities.challenge
import heroes.journey.modlib.Ids.*

// Magical Challenges - included by basegame mod
challenge("arcane_puzzle") {
    approaches("concentration", "logic", "sorcery")
    reward { attr("arcanum", 2) }
}.register()
challenge("enchant_item") {
    approaches("charm", "empowerment", "enchanting")
    reward { attr("arcanum", 2) }
}.register()
challenge("trick_magical_guardian") {
    approaches("cunning", "illusion", "logic")
    reward { attr("insight", 2) }
}.register()
challenge("empower_relic") {
    approaches("enchanting", "might", "persuasion")
    reward { attr("arcanum", 2) }
}.register()
challenge("banish_cursed_spirit") {
    approaches("charm", "concentration", "sorcery")
    reward { attr("arcanum", 2) }
}.register()
challenge("bewitch_familiar") {
    approaches("bewitching", "empowerment", "logic")
    reward { attr("influence", 2) }
}.register()
challenge("charm_magical_beast") {
    approaches("bewitching", "charm", "mesmerism")
    reward { attr("influence", 2) }
}.register()
challenge("outwit_magical_trap") {
    approaches("cunning", "illusion", "logic")
    reward { attr("insight", 2) }
}.register()
challenge("enchant_weapon") {
    approaches("empowerment", "enchanting", "persuasion")
    reward { attr("arcanum", 2) }
}.register()
challenge("solve_riddle") {
    approaches("enchanting", "logic", "might")
    reward { attr("insight", 2) }
}.register()
challenge("disarm_golem") {
    approaches("cunning", "skill", "technique")
    reward { attr("valor", 2) }
}.register()
challenge("bewitch_magical_foe") {
    approaches("bewitching", "charm", "mesmerism")
    reward { attr("influence", 2) }
}.register()
challenge("persuade_elemental") {
    approaches("concentration", "persuasion", "sorcery")
    reward { attr("influence", 2) }
}.register()
challenge("befriend_fairy") {
    approaches("charm", "enchanting", "mesmerism")
    reward { attr("influence", 2) }
}.register()
challenge("banish_demon") {
    approaches("bewitching", "logic", "sorcery")
    reward { attr("arcanum", 2) }
}.register()
