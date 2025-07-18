import heroes.journey.modlib.Ids
import heroes.journey.modlib.attributes.attributes
import heroes.journey.modlib.misc.challenge

// Humanoid II Challenges - included by basegame mod
challenge {
    id = Ids.CHALLENGE_MENTOR_ADVENTUROUS_ADOLESCENT
    render = Ids.ADVENTUROUS_ADOLESCENT
    approach(Ids.STAT_CHARM)
    approach(Ids.STAT_MESMERISM)
    approach(Ids.STAT_PERSUASION)
    reward { stat("influence", 2) }
}.register()
challenge {
    id = Ids.CHALLENGE_MENTOR_BOISTEROUS_YOUTH
    render = Ids.BOISTEROUS_YOUTH
    approach(Ids.STAT_CHARM)
    approach(Ids.STAT_MESMERISM)
    approach(Ids.STAT_PERSUASION)
    reward { stat("influence", 2) }
}.register()
challenge {
    id = Ids.CHALLENGE_LEARN_ELF_BLADEDANCER
    render = Ids.ELF_BLADEDANCER
    approach(Ids.STAT_ENCHANTING)
    approach(Ids.STAT_LOGIC)
    approach(Ids.STAT_SKILL)
    reward { stat("valor", 2) }
}.register()
challenge {
    id = Ids.CHALLENGE_STUDY_ELF_ENCHANTER
    render = Ids.ELF_ENCHANTER
    approach(Ids.STAT_CHARM)
    approach(Ids.STAT_CONCENTRATION)
    approach(Ids.STAT_SORCERY)
    reward { stat("arcanum", 2) }
}.register()
challenge {
    id = Ids.CHALLENGE_NEGOTIATE_ELF_LORD
    render = Ids.ELF_LORD
    approach(Ids.STAT_CHARM)
    approach(Ids.STAT_MESMERISM)
    approach(Ids.STAT_PERSUASION)
    reward { stat("influence", 3) }
}.register()
challenge {
    id = Ids.CHALLENGE_COMPETE_ELF_SHARPSHOOTER
    render = Ids.ELF_SHARPSHOOTER
    approach(Ids.STAT_LOGIC)
    approach(Ids.STAT_SKILL)
    approach(Ids.STAT_TECHNIQUE)
    reward { stat("valor", 2) }
}.register()
challenge {
    id = Ids.CHALLENGE_TRAVEL_ELF_WAYFARER
    render = Ids.ELF_WAYFARER
    approach(Ids.STAT_CHARM)
    approach(Ids.STAT_CUNNING)
    approach(Ids.STAT_SKILL)
    reward { stat("insight", 2) }
}.register()
challenge {
    id = Ids.CHALLENGE_PLAY_JOYFUL_KID
    render = Ids.JOYFUL_KID
    approach(Ids.STAT_BEWITCHING)
    approach(Ids.STAT_CHARM)
    approach(Ids.STAT_MESMERISM)
    reward { stat("influence", 2) }
}.register()
challenge {
    id = Ids.CHALLENGE_LEARN_MERFOLK_AQUAMANCER
    render = Ids.MERFOLK_AQUAMANCER
    approach(Ids.STAT_CONCENTRATION)
    approach(Ids.STAT_LOGIC)
    approach(Ids.STAT_SORCERY)
    reward { stat("arcanum", 2) }
}.register()
challenge {
    id = Ids.CHALLENGE_SPAR_MERFOLK_IMPALER
    render = Ids.MERFOLK_IMPALER
    approach(Ids.STAT_CHARM)
    approach(Ids.STAT_MIGHT)
    approach(Ids.STAT_SKILL)
    reward { stat("valor", 2) }
}.register()
challenge {
    id = Ids.CHALLENGE_COMPETE_MERFOLK_JAVELINEER
    render = Ids.MERFOLK_JAVELINEER
    approach(Ids.STAT_LOGIC)
    approach(Ids.STAT_SKILL)
    approach(Ids.STAT_TECHNIQUE)
    reward { stat("valor", 2) }
}.register()
challenge {
    id = Ids.CHALLENGE_STUDY_MERFOLK_MYSTIC
    render = Ids.MERFOLK_MYSTIC
    approach(Ids.STAT_CHARM)
    approach(Ids.STAT_CONCENTRATION)
    approach(Ids.STAT_SORCERY)
    reward { stat("arcanum", 2) }
}.register()
challenge {
    id = Ids.CHALLENGE_SCOUT_MERFOLK_SCOUT
    render = Ids.MERFOLK_SCOUT
    approach(Ids.STAT_CUNNING)
    approach(Ids.STAT_LOGIC)
    approach(Ids.STAT_SKILL)
    reward { stat("insight", 2) }
}.register()
challenge {
    id = Ids.CHALLENGE_HELP_OVERWORKED_VILLAGER
    render = Ids.OVERWORKED_VILLAGER
    approach(Ids.STAT_CHARM)
    approach(Ids.STAT_CUNNING)
    approach(Ids.STAT_LOGIC)
    reward { stat("influence", 2) }
}.register()
challenge {
    id = Ids.CHALLENGE_PLAY_PLAYFUL_CHILD
    render = Ids.PLAYFUL_CHILD
    approach(Ids.STAT_BEWITCHING)
    approach(Ids.STAT_CHARM)
    approach(Ids.STAT_MESMERISM)
    reward { stat("influence", 2) }
}.register()
