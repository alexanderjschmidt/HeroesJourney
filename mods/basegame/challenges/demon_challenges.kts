import heroes.journey.entities.challenge
import heroes.journey.entities.tagging.Stat
import heroes.journey.initializers.base.Ids.*

// Demon Challenges - included by basegame mod

challenge("banish_antlered_rascal") {
    name = "Banish Antlered Rascal"
    description =
        "A mischievous demon with antlers is warping reality and deceiving villagers. Outsmart and banish it using illusion, logic, or persuasion."
    render = ANTLERED_RASCAL
    approaches(Stat.ILLUSION, Stat.LOGIC, Stat.PERSUASION)
    reward { attr("valor", 2) }
}.register()
challenge("subdue_clawed_abomination") {
    name = "Subdue Clawed Abomination"
    description =
        "A monstrous demon with razor claws is terrorizing the countryside. Use charm, magical enchantment, or focused concentration to subdue it."
    render = CLAWED_ABOMINATION
    approaches(Stat.CHARM, Stat.CONCENTRATION, Stat.ENCHANTING)
    reward { attr("valor", 2) }
}.register()
challenge("trick_crimson_imp") {
    name = "Trick Crimson Imp"
    description =
        "A cunning imp is causing magical mischief. Outsmart it with cleverness, illusion, or tactical technique."
    render = CRIMSON_IMP
    approaches(Stat.CUNNING, Stat.ILLUSION, Stat.TECHNIQUE)
    reward { attr("arcanum", 2) }
}.register()
challenge("capture_depraved_blackguard") {
    name = "Capture Depraved Blackguard"
    description =
        "A fallen demon knight is preying on travelers. Use charm, might, or skill to bring him to justice."
    render = DEPRAVED_BLACKGUARD
    approaches(Stat.CHARM, Stat.MIGHT, Stat.SKILL)
    reward { attr("valor", 2) }
}.register()
challenge("redeem_fledgling_demon") {
    name = "Redeem Fledgling Demon"
    description =
        "A young demon is torn between good and evil. Use bewitching magic, logic, or mesmerism to guide it toward redemption."
    render = FLEDGLING_DEMON
    approaches(Stat.BEWITCHING, Stat.LOGIC, Stat.MESMERISM)
    reward { attr("influence", 2) }
}.register()
challenge("destroy_floating_eye") {
    name = "Destroy Floating Eye"
    description =
        "A floating demon eye spies on and curses villagers. Use concentration, illusion, or sorcery to destroy it."
    render = FLOATING_EYE
    approaches(Stat.CONCENTRATION, Stat.ILLUSION, Stat.SORCERY)
    reward { attr("arcanum", 2) }
}.register()
challenge("stop_foul_gouger") {
    name = "Stop Foul Gouger"
    description =
        "A demon gouges out eyes and spreads terror. Use charm, empowerment, or persuasion to stop it."
    render = FOUL_GOUGER
    approaches(Stat.CHARM, Stat.EMPOWERMENT, Stat.PERSUASION)
    reward { attr("valor", 2) }
}.register()
challenge("outwit_grinning_gremlin") {
    name = "Outwit Grinning Gremlin"
    description =
        "A gremlin is sabotaging machinery with tricks. Outsmart it with cunning, logic, or technique."
    render = GRINNING_GREMLIN
    approaches(Stat.CUNNING, Stat.LOGIC, Stat.TECHNIQUE)
    reward { attr("insight", 2) }
}.register()
challenge("catch_nefarious_scamp") {
    name = "Catch Nefarious Scamp"
    description =
        "A demonic thief is stealing magical artifacts. Use cunning, illusion, or technique to catch it."
    render = NEFARIOUS_SCAMP
    approaches(Stat.CUNNING, Stat.ILLUSION, Stat.TECHNIQUE)
    reward { attr("insight", 2) }
}.register()
challenge("duel_pit_balor") {
    name = "Duel Pit Balor"
    description = "A mighty demon lord challenges you to a duel. Use bravado, charm, or might to win."
    render = PIT_BALOR
    approaches(Stat.BRAVADO, Stat.CHARM, Stat.MIGHT)
    reward { attr("valor", 3) }
}.register()
challenge("banish_pointed_demonspawn") {
    name = "Banish Pointed Demonspawn"
    description =
        "A demon with deadly spikes threatens the land. Use enchanting magic, persuasion, or sorcery to banish it."
    render = POINTED_DEMONSPAWN
    approaches(Stat.ENCHANTING, Stat.PERSUASION, Stat.SORCERY)
    reward { attr("arcanum", 2) }
}.register()
challenge("discipline_rascally_demonling") {
    name = "Discipline Rascally Demonling"
    description =
        "A mischievous demonling is causing trouble. Use charm, logic, or mesmerism to teach it a lesson."
    render = RASCALLY_DEMONLING
    approaches(Stat.CHARM, Stat.LOGIC, Stat.MESMERISM)
    reward { attr("influence", 2) }
}.register()
challenge("hunt_skewering_stalker") {
    name = "Hunt Skewering Stalker"
    description =
        "A demon impales its victims on spikes. Use illusion, skill, or technique to hunt it down."
    render = SKEWERING_STALKER
    approaches(Stat.ILLUSION, Stat.SKILL, Stat.TECHNIQUE)
    reward { attr("valor", 2) }
}.register()
challenge("reform_tainted_scoundrel") {
    name = "Reform Tainted Scoundrel"
    description = "A corrupted demon seeks redemption. Use bravado, cunning, or logic to help it change."
    render = TAINTED_SCOUNDREL
    approaches(Stat.BRAVADO, Stat.CUNNING, Stat.LOGIC)
    reward { attr("influence", 2) }
}.register()
challenge("shatter_warp_skull") {
    name = "Shatter Warp Skull"
    description =
        "A demonic skull warps reality and minds. Use illusion, sorcery, or bewitching to shatter it."
    render = WARP_SKULL
    approaches(Stat.ILLUSION, Stat.SORCERY, Stat.BEWITCHING)
    reward { attr("arcanum", 2) }
}.register()
