import heroes.journey.entities.challenge
import heroes.journey.modlib.Ids.*

// Demon Challenges - included by basegame mod

challenge("banish_antlered_rascal") {
    render = ANTLERED_RASCAL
    approaches("illusion", "logic", "persuasion")
    reward { attr("valor", 2) }
}.register()
challenge("subdue_clawed_abomination") {
    render = CLAWED_ABOMINATION
    approaches("charm", "concentration", "enchanting")
    reward { attr("valor", 2) }
}.register()
challenge("trick_crimson_imp") {
    render = CRIMSON_IMP
    approaches("cunning", "illusion", "technique")
    reward { attr("arcanum", 2) }
}.register()
challenge("capture_depraved_blackguard") {
    render = DEPRAVED_BLACKGUARD
    approaches("charm", "might", "skill")
    reward { attr("valor", 2) }
}.register()
challenge("redeem_fledgling_demon") {
    render = FLEDGLING_DEMON
    approaches("bewitching", "logic", "mesmerism")
    reward { attr("influence", 2) }
}.register()
challenge("destroy_floating_eye") {
    render = FLOATING_EYE
    approaches("concentration", "illusion", "sorcery")
    reward { attr("arcanum", 2) }
}.register()
challenge("stop_foul_gouger") {
    render = FOUL_GOUGER
    approaches("charm", "empowerment", "persuasion")
    reward { attr("valor", 2) }
}.register()
challenge("outwit_grinning_gremlin") {
    render = GRINNING_GREMLIN
    approaches("cunning", "logic", "technique")
    reward { attr("insight", 2) }
}.register()
challenge("catch_nefarious_scamp") {
    render = NEFARIOUS_SCAMP
    approaches("cunning", "illusion", "technique")
    reward { attr("insight", 2) }
}.register()
challenge("duel_pit_balor") {
    render = PIT_BALOR
    approaches("bravado", "charm", "might")
    reward { attr("valor", 3) }
}.register()
challenge("banish_pointed_demonspawn") {
    render = POINTED_DEMONSPAWN
    approaches("enchanting", "persuasion", "sorcery")
    reward { attr("arcanum", 2) }
}.register()
challenge("discipline_rascally_demonling") {
    render = RASCALLY_DEMONLING
    approaches("charm", "logic", "mesmerism")
    reward { attr("influence", 2) }
}.register()
challenge("hunt_skewering_stalker") {
    render = SKEWERING_STALKER
    approaches("illusion", "skill", "technique")
    reward { attr("valor", 2) }
}.register()
challenge("reform_tainted_scoundrel") {
    render = TAINTED_SCOUNDREL
    approaches("bravado", "cunning", "logic")
    reward { attr("influence", 2) }
}.register()
challenge("shatter_warp_skull") {
    render = WARP_SKULL
    approaches("illusion", "sorcery", "bewitching")
    reward { attr("arcanum", 2) }
}.register()
