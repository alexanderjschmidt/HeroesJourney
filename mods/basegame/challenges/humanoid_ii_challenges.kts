import heroes.journey.entities.challenge
import heroes.journey.entities.tagging.Stat
import heroes.journey.modlib.Ids.*

// Humanoid II Challenges - included by basegame mod
challenge("mentor_adventurous_adolescent") {
    render = ADVENTUROUS_ADOLESCENT
    approaches(Stat.CHARM, Stat.MESMERISM, Stat.PERSUASION)
    reward { attr("influence", 2) }
}.register()
challenge("mentor_boisterous_youth") {
    render = BOISTEROUS_YOUTH
    approaches(Stat.CHARM, Stat.MESMERISM, Stat.PERSUASION)
    reward { attr("influence", 2) }
}.register()
challenge("learn_elf_bladedancer") {
    render = ELF_BLADEDANCER
    approaches(Stat.ENCHANTING, Stat.LOGIC, Stat.SKILL)
    reward { attr("valor", 2) }
}.register()
challenge("study_elf_enchanter") {
    render = ELF_ENCHANTER
    approaches(Stat.CHARM, Stat.CONCENTRATION, Stat.SORCERY)
    reward { attr("arcanum", 2) }
}.register()
challenge("negotiate_elf_lord") {
    render = ELF_LORD
    approaches(Stat.CHARM, Stat.MESMERISM, Stat.PERSUASION)
    reward { attr("influence", 3) }
}.register()
challenge("compete_elf_sharpshooter") {
    render = ELF_SHARPSHOOTER
    approaches(Stat.LOGIC, Stat.SKILL, Stat.TECHNIQUE)
    reward { attr("valor", 2) }
}.register()
challenge("travel_elf_wayfarer") {
    render = ELF_WAYFARER
    approaches(Stat.CHARM, Stat.CUNNING, Stat.SKILL)
    reward { attr("insight", 2) }
}.register()
challenge("play_joyful_kid") {
    render = JOYFUL_KID
    approaches(Stat.BEWITCHING, Stat.CHARM, Stat.MESMERISM)
    reward { attr("influence", 2) }
}.register()
challenge("learn_merfolk_aquamancer") {
    render = MERFOLK_AQUAMANCER
    approaches(Stat.CONCENTRATION, Stat.LOGIC, Stat.SORCERY)
    reward { attr("arcanum", 2) }
}.register()
challenge("spar_merfolk_impaler") {
    render = MERFOLK_IMPALER
    approaches(Stat.CHARM, Stat.MIGHT, Stat.SKILL)
    reward { attr("valor", 2) }
}.register()
challenge("compete_merfolk_javelineer") {
    render = MERFOLK_JAVELINEER
    approaches(Stat.LOGIC, Stat.SKILL, Stat.TECHNIQUE)
    reward { attr("valor", 2) }
}.register()
challenge("study_merfolk_mystic") {
    render = MERFOLK_MYSTIC
    approaches(Stat.CHARM, Stat.CONCENTRATION, Stat.SORCERY)
    reward { attr("arcanum", 2) }
}.register()
challenge("scout_merfolk_scout") {
    render = MERFOLK_SCOUT
    approaches(Stat.CUNNING, Stat.LOGIC, Stat.SKILL)
    reward { attr("insight", 2) }
}.register()
challenge("help_overworked_villager") {
    render = OVERWORKED_VILLAGER
    approaches(Stat.CHARM, Stat.CUNNING, Stat.LOGIC)
    reward { attr("influence", 2) }
}.register()
challenge("play_playful_child") {
    render = PLAYFUL_CHILD
    approaches(Stat.BEWITCHING, Stat.CHARM, Stat.MESMERISM)
    reward { attr("influence", 2) }
}.register()
