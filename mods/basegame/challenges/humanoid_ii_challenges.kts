import heroes.journey.entities.challenge
import heroes.journey.modlib.Ids
import heroes.journey.modlib.Ids.*

// Humanoid II Challenges - included by basegame mod
challenge("mentor_adventurous_adolescent") {
    render = ADVENTUROUS_ADOLESCENT
    approaches(Ids.STAT_CHARM, Ids.STAT_MESMERISM, Ids.STAT_PERSUASION)
    reward { attr("influence", 2) }
}.register()
challenge("mentor_boisterous_youth") {
    render = BOISTEROUS_YOUTH
    approaches(Ids.STAT_CHARM, Ids.STAT_MESMERISM, Ids.STAT_PERSUASION)
    reward { attr("influence", 2) }
}.register()
challenge("learn_elf_bladedancer") {
    render = ELF_BLADEDANCER
    approaches(Ids.STAT_ENCHANTING, Ids.STAT_LOGIC, Ids.STAT_SKILL)
    reward { attr("valor", 2) }
}.register()
challenge("study_elf_enchanter") {
    render = ELF_ENCHANTER
    approaches(Ids.STAT_CHARM, Ids.STAT_CONCENTRATION, Ids.STAT_SORCERY)
    reward { attr("arcanum", 2) }
}.register()
challenge("negotiate_elf_lord") {
    render = ELF_LORD
    approaches(Ids.STAT_CHARM, Ids.STAT_MESMERISM, Ids.STAT_PERSUASION)
    reward { attr("influence", 3) }
}.register()
challenge("compete_elf_sharpshooter") {
    render = ELF_SHARPSHOOTER
    approaches(Ids.STAT_LOGIC, Ids.STAT_SKILL, Ids.STAT_TECHNIQUE)
    reward { attr("valor", 2) }
}.register()
challenge("travel_elf_wayfarer") {
    render = ELF_WAYFARER
    approaches(Ids.STAT_CHARM, Ids.STAT_CUNNING, Ids.STAT_SKILL)
    reward { attr("insight", 2) }
}.register()
challenge("play_joyful_kid") {
    render = JOYFUL_KID
    approaches(Ids.STAT_BEWITCHING, Ids.STAT_CHARM, Ids.STAT_MESMERISM)
    reward { attr("influence", 2) }
}.register()
challenge("learn_merfolk_aquamancer") {
    render = MERFOLK_AQUAMANCER
    approaches(Ids.STAT_CONCENTRATION, Ids.STAT_LOGIC, Ids.STAT_SORCERY)
    reward { attr("arcanum", 2) }
}.register()
challenge("spar_merfolk_impaler") {
    render = MERFOLK_IMPALER
    approaches(Ids.STAT_CHARM, Ids.STAT_MIGHT, Ids.STAT_SKILL)
    reward { attr("valor", 2) }
}.register()
challenge("compete_merfolk_javelineer") {
    render = MERFOLK_JAVELINEER
    approaches(Ids.STAT_LOGIC, Ids.STAT_SKILL, Ids.STAT_TECHNIQUE)
    reward { attr("valor", 2) }
}.register()
challenge("study_merfolk_mystic") {
    render = MERFOLK_MYSTIC
    approaches(Ids.STAT_CHARM, Ids.STAT_CONCENTRATION, Ids.STAT_SORCERY)
    reward { attr("arcanum", 2) }
}.register()
challenge("scout_merfolk_scout") {
    render = MERFOLK_SCOUT
    approaches(Ids.STAT_CUNNING, Ids.STAT_LOGIC, Ids.STAT_SKILL)
    reward { attr("insight", 2) }
}.register()
challenge("help_overworked_villager") {
    render = OVERWORKED_VILLAGER
    approaches(Ids.STAT_CHARM, Ids.STAT_CUNNING, Ids.STAT_LOGIC)
    reward { attr("influence", 2) }
}.register()
challenge("play_playful_child") {
    render = PLAYFUL_CHILD
    approaches(Ids.STAT_BEWITCHING, Ids.STAT_CHARM, Ids.STAT_MESMERISM)
    reward { attr("influence", 2) }
}.register()
