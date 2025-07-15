import heroes.journey.entities.challenge
import heroes.journey.modlib.Ids.*

// Vermin Challenges - included by basegame mod
challenge("outsmart_giant_ant") {
    approaches("cunning", "skill", "technique")
    reward { attr("insight", 2) }
}.register()
challenge("crush_bedbug_swarm") {
    approaches("bravado", "might", "skill")
    reward { attr("valor", 2) }
}.register()
challenge("hunt_tunneling_mole") {
    approaches("might", "skill", "technique")
    reward { attr("valor", 2) }
}.register()
challenge("befriend_dung_beetle") {
    approaches("bravado", "enchanting", "might")
    reward { attr("influence", 2) }
}.register()
challenge("outwit_plague_bat") {
    approaches("cunning", "skill", "technique")
    reward { attr("insight", 2) }
}.register()
