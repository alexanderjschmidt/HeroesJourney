import heroes.journey.entities.challenge
import heroes.journey.entities.tagging.Stat
import heroes.journey.initializers.Ids.*

// Humanoid II Challenges - included by basegame mod
challenge("mentor_adventurous_adolescent") {
    name = "Mentor Adventurous Adolescent"
    description = "A young adventurer seeks guidance. Use charm, mesmerism, or persuasion to mentor it."
    render = ADVENTUROUS_ADOLESCENT
    approaches(Stat.CHARM, Stat.MESMERISM, Stat.PERSUASION)
    reward { attr("influence", 2) }
}.register()
challenge("mentor_boisterous_youth") {
    name = "Mentor Boisterous Youth"
    description =
        "An energetic young person needs direction. Use charm, mesmerism, or persuasion to mentor it."
    render = BOISTEROUS_YOUTH
    approaches(Stat.CHARM, Stat.MESMERISM, Stat.PERSUASION)
    reward { attr("influence", 2) }
}.register()
challenge("learn_elf_bladedancer") {
    name = "Learn from Elf Bladedancer"
    description =
        "An elf offers to teach graceful combat. Use enchanting magic, logic, or skill to learn."
    render = ELF_BLADEDANCER
    approaches(Stat.ENCHANTING, Stat.LOGIC, Stat.SKILL)
    reward { attr("valor", 2) }
}.register()
challenge("study_elf_enchanter") {
    name = "Study with Elf Enchanter"
    description =
        "An elf offers to teach magical enchantment. Use charm, concentration, or sorcery to study."
    render = ELF_ENCHANTER
    approaches(Stat.CHARM, Stat.CONCENTRATION, Stat.SORCERY)
    reward { attr("arcanum", 2) }
}.register()
challenge("negotiate_elf_lord") {
    name = "Negotiate with Elf Lord"
    description = "An elf lord offers diplomatic talks. Use charm, mesmerism, or persuasion to negotiate."
    render = ELF_LORD
    approaches(Stat.CHARM, Stat.MESMERISM, Stat.PERSUASION)
    reward { attr("influence", 3) }
}.register()
challenge("compete_elf_sharpshooter") {
    name = "Compete with Elf Sharpshooter"
    description =
        "An elf challenges you to an archery contest. Use logic, skill, or technique to compete."
    render = ELF_SHARPSHOOTER
    approaches(Stat.LOGIC, Stat.SKILL, Stat.TECHNIQUE)
    reward { attr("valor", 2) }
}.register()
challenge("travel_elf_wayfarer") {
    name = "Travel with Elf Wayfarer"
    description =
        "An elf offers to guide you through dangerous lands. Use charm, cunning, or skill to travel."
    render = ELF_WAYFARER
    approaches(Stat.CHARM, Stat.CUNNING, Stat.SKILL)
    reward { attr("insight", 2) }
}.register()
challenge("play_joyful_kid") {
    name = "Play with Joyful Kid"
    description = "A happy child wants to play games. Use bewitching magic, charm, or mesmerism to play."
    render = JOYFUL_KID
    approaches(Stat.BEWITCHING, Stat.CHARM, Stat.MESMERISM)
    reward { attr("influence", 2) }
}.register()
challenge("learn_merfolk_aquamancer") {
    name = "Learn from Merfolk Aquamancer"
    description = "A merfolk offers to teach water magic. Use concentration, logic, or sorcery to learn."
    render = MERFOLK_AQUAMANCER
    approaches(Stat.CONCENTRATION, Stat.LOGIC, Stat.SORCERY)
    reward { attr("arcanum", 2) }
}.register()
challenge("spar_merfolk_impaler") {
    name = "Spar with Merfolk Impaler"
    description = "A merfolk offers underwater combat training. Use charm, might, or skill to spar."
    render = MERFOLK_IMPALER
    approaches(Stat.CHARM, Stat.MIGHT, Stat.SKILL)
    reward { attr("valor", 2) }
}.register()
challenge("compete_merfolk_javelineer") {
    name = "Compete with Merfolk Javelineer"
    description =
        "A merfolk challenges you to a javelin contest. Use logic, skill, or technique to compete."
    render = MERFOLK_JAVELINEER
    approaches(Stat.LOGIC, Stat.SKILL, Stat.TECHNIQUE)
    reward { attr("valor", 2) }
}.register()
challenge("study_merfolk_mystic") {
    name = "Study with Merfolk Mystic"
    description =
        "A merfolk offers to teach mystical arts. Use charm, concentration, or sorcery to study."
    render = MERFOLK_MYSTIC
    approaches(Stat.CHARM, Stat.CONCENTRATION, Stat.SORCERY)
    reward { attr("arcanum", 2) }
}.register()
challenge("scout_merfolk_scout") {
    name = "Scout with Merfolk Scout"
    description = "A merfolk offers underwater scouting lessons. Use cunning, logic, or skill to scout."
    render = MERFOLK_SCOUT
    approaches(Stat.CUNNING, Stat.LOGIC, Stat.SKILL)
    reward { attr("insight", 2) }
}.register()
challenge("help_overworked_villager") {
    name = "Help Overworked Villager"
    description = "A villager is overwhelmed with work. Use charm, cunning, or logic to help it."
    render = OVERWORKED_VILLAGER
    approaches(Stat.CHARM, Stat.CUNNING, Stat.LOGIC)
    reward { attr("influence", 2) }
}.register()
challenge("play_playful_child") {
    name = "Play with Playful Child"
    description = "A playful child wants to have fun. Use bewitching magic, charm, or mesmerism to play."
    render = PLAYFUL_CHILD
    approaches(Stat.BEWITCHING, Stat.CHARM, Stat.MESMERISM)
    reward { attr("influence", 2) }
}.register()
