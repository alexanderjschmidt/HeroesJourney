import heroes.journey.entities.challenge
import heroes.journey.entities.tagging.Stat
import heroes.journey.modlib.Ids.*

// Demon Challenges - included by basegame mod

challenge("banish_antlered_rascal") {
    render = ANTLERED_RASCAL
    approaches(Stat.ILLUSION, Stat.LOGIC, Stat.PERSUASION)
    reward { attr("valor", 2) }
}.register()
challenge("subdue_clawed_abomination") {
    render = CLAWED_ABOMINATION
    approaches(Stat.CHARM, Stat.CONCENTRATION, Stat.ENCHANTING)
    reward { attr("valor", 2) }
}.register()
challenge("trick_crimson_imp") {
    render = CRIMSON_IMP
    approaches(Stat.CUNNING, Stat.ILLUSION, Stat.TECHNIQUE)
    reward { attr("arcanum", 2) }
}.register()
challenge("capture_depraved_blackguard") {
    render = DEPRAVED_BLACKGUARD
    approaches(Stat.CHARM, Stat.MIGHT, Stat.SKILL)
    reward { attr("valor", 2) }
}.register()
challenge("redeem_fledgling_demon") {
    render = FLEDGLING_DEMON
    approaches(Stat.BEWITCHING, Stat.LOGIC, Stat.MESMERISM)
    reward { attr("influence", 2) }
}.register()
challenge("destroy_floating_eye") {
    render = FLOATING_EYE
    approaches(Stat.CONCENTRATION, Stat.ILLUSION, Stat.SORCERY)
    reward { attr("arcanum", 2) }
}.register()
challenge("stop_foul_gouger") {
    render = FOUL_GOUGER
    approaches(Stat.CHARM, Stat.EMPOWERMENT, Stat.PERSUASION)
    reward { attr("valor", 2) }
}.register()
challenge("outwit_grinning_gremlin") {
    render = GRINNING_GREMLIN
    approaches(Stat.CUNNING, Stat.LOGIC, Stat.TECHNIQUE)
    reward { attr("insight", 2) }
}.register()
challenge("catch_nefarious_scamp") {
    render = NEFARIOUS_SCAMP
    approaches(Stat.CUNNING, Stat.ILLUSION, Stat.TECHNIQUE)
    reward { attr("insight", 2) }
}.register()
challenge("duel_pit_balor") {
    render = PIT_BALOR
    approaches(Stat.BRAVADO, Stat.CHARM, Stat.MIGHT)
    reward { attr("valor", 3) }
}.register()
challenge("banish_pointed_demonspawn") {
    render = POINTED_DEMONSPAWN
    approaches(Stat.ENCHANTING, Stat.PERSUASION, Stat.SORCERY)
    reward { attr("arcanum", 2) }
}.register()
challenge("discipline_rascally_demonling") {
    render = RASCALLY_DEMONLING
    approaches(Stat.CHARM, Stat.LOGIC, Stat.MESMERISM)
    reward { attr("influence", 2) }
}.register()
challenge("hunt_skewering_stalker") {
    render = SKEWERING_STALKER
    approaches(Stat.ILLUSION, Stat.SKILL, Stat.TECHNIQUE)
    reward { attr("valor", 2) }
}.register()
challenge("reform_tainted_scoundrel") {
    render = TAINTED_SCOUNDREL
    approaches(Stat.BRAVADO, Stat.CUNNING, Stat.LOGIC)
    reward { attr("influence", 2) }
}.register()
challenge("shatter_warp_skull") {
    render = WARP_SKULL
    approaches(Stat.ILLUSION, Stat.SORCERY, Stat.BEWITCHING)
    reward { attr("arcanum", 2) }
}.register()
