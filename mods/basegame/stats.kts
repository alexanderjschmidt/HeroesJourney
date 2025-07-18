import heroes.journey.modlib.Ids
import heroes.journey.modlib.attributes.group
import heroes.journey.modlib.attributes.stat

// --- GROUPS ---
group(Ids.GROUP_BODY).register()
group(Ids.GROUP_MIND).register()
group(Ids.GROUP_MAGIC).register()
group(Ids.GROUP_CHARISMA).register()
group(Ids.GROUP_BASESTATS).register()
group(Ids.GROUP_RENOWN).register()

// --- BASE STATS ---
stat(
    Ids.STAT_BODY,
    groups = listOf(group(Ids.GROUP_BASESTATS), group(Ids.GROUP_BODY))
).register()
stat(
    Ids.STAT_MIND,
    groups = listOf(group(Ids.GROUP_BASESTATS), group(Ids.GROUP_MIND))
).register()
stat(
    Ids.STAT_MAGIC,
    groups = listOf(group(Ids.GROUP_BASESTATS), group(Ids.GROUP_MAGIC))
).register()
stat(
    Ids.STAT_CHARISMA,
    groups = listOf(group(Ids.GROUP_BASESTATS), group(Ids.GROUP_CHARISMA))
).register()

// --- RENOWN STATS ---
stat(
    Ids.STAT_VALOR,
    min = 0,
    groups = listOf(group(Ids.GROUP_RENOWN))
).register()
stat(
    Ids.STAT_INSIGHT,
    min = 0,
    groups = listOf(group(Ids.GROUP_RENOWN))
).register()
stat(
    Ids.STAT_ARCANUM,
    min = 0,
    groups = listOf(group(Ids.GROUP_RENOWN))
).register()
stat(
    Ids.STAT_INFLUENCE,
    min = 0,
    groups = listOf(group(Ids.GROUP_RENOWN))
).register()

// --- CONFLUENCE STATS (complex) ---
// BODY
stat(
    Ids.STAT_MIGHT,
    groups = listOf(group(Ids.GROUP_BODY)),
    formula = { it ->
        (it.getDirect(Ids.STAT_BODY) + it.getDirect(Ids.STAT_BODY) + it.getDirect(Ids.STAT_BODY)) / 3 + it.getDirect(
            Ids.STAT_MIGHT
        )
    }
).register()
stat(
    Ids.STAT_SKILL,
    groups = listOf(group(Ids.GROUP_BODY), group(Ids.GROUP_MIND)),
    formula = { it ->
        (it.getDirect(Ids.STAT_BODY) + it.getDirect(Ids.STAT_BODY) + it.getDirect(Ids.STAT_MIND)) / 3 + it.getDirect(
            Ids.STAT_SKILL
        )
    }
).register()
stat(
    Ids.STAT_EMPOWERMENT,
    groups = listOf(group(Ids.GROUP_BODY), group(Ids.GROUP_MAGIC)),
    formula = { it ->
        (it.getDirect(Ids.STAT_BODY) + it.getDirect(Ids.STAT_BODY) + it.getDirect(Ids.STAT_MAGIC)) / 3 + it.getDirect(
            Ids.STAT_EMPOWERMENT
        )
    }
).register()
stat(
    Ids.STAT_CHIVALRY,
    groups = listOf(group(Ids.GROUP_BODY), group(Ids.GROUP_CHARISMA)),
    formula = { it ->
        (it.getDirect(Ids.STAT_BODY) + it.getDirect(Ids.STAT_BODY) + it.getDirect(Ids.STAT_CHARISMA)) / 3 + it.getDirect(
            Ids.STAT_CHIVALRY
        )
    }
).register()
// MIND
stat(
    Ids.STAT_TECHNIQUE,
    groups = listOf(group(Ids.GROUP_MIND), group(Ids.GROUP_BODY)),
    formula = { it ->
        (it.getDirect(Ids.STAT_MIND) + it.getDirect(Ids.STAT_MIND) + it.getDirect(Ids.STAT_BODY)) / 3 + it.getDirect(
            Ids.STAT_TECHNIQUE
        )
    }
).register()
stat(
    Ids.STAT_LOGIC,
    groups = listOf(group(Ids.GROUP_MIND)),
    formula = { it ->
        (it.getDirect(Ids.STAT_MIND) + it.getDirect(Ids.STAT_MIND) + it.getDirect(Ids.STAT_MIND)) / 3 + it.getDirect(
            Ids.STAT_LOGIC
        )
    }
).register()
stat(
    Ids.STAT_CONCENTRATION,
    groups = listOf(group(Ids.GROUP_MIND), group(Ids.GROUP_MAGIC)),
    formula = { it ->
        (it.getDirect(Ids.STAT_MIND) + it.getDirect(Ids.STAT_MIND) + it.getDirect(Ids.STAT_MAGIC)) / 3 + it.getDirect(
            Ids.STAT_CONCENTRATION
        )
    }
).register()
stat(
    Ids.STAT_CUNNING,
    groups = listOf(group(Ids.GROUP_MIND), group(Ids.GROUP_CHARISMA)),
    formula = { it ->
        (it.getDirect(Ids.STAT_MIND) + it.getDirect(Ids.STAT_MIND) + it.getDirect(Ids.STAT_CHARISMA)) / 3 + it.getDirect(
            Ids.STAT_CUNNING
        )
    }
).register()
// MAGIC
stat(
    Ids.STAT_ENCHANTING,
    groups = listOf(group(Ids.GROUP_MAGIC), group(Ids.GROUP_BODY)),
    formula = { it ->
        (it.getDirect(Ids.STAT_MAGIC) + it.getDirect(Ids.STAT_MAGIC) + it.getDirect(Ids.STAT_BODY)) / 3 + it.getDirect(
            Ids.STAT_ENCHANTING
        )
    }
).register()
stat(
    Ids.STAT_ILLUSION,
    groups = listOf(group(Ids.GROUP_MAGIC), group(Ids.GROUP_MIND)),
    formula = { it ->
        (it.getDirect(Ids.STAT_MAGIC) + it.getDirect(Ids.STAT_MAGIC) + it.getDirect(Ids.STAT_MIND)) / 3 + it.getDirect(
            Ids.STAT_ILLUSION
        )
    }
).register()
stat(
    Ids.STAT_SORCERY,
    groups = listOf(group(Ids.GROUP_MAGIC)),
    formula = { it ->
        (it.getDirect(Ids.STAT_MAGIC) + it.getDirect(Ids.STAT_MAGIC) + it.getDirect(Ids.STAT_MAGIC)) / 3 + it.getDirect(
            Ids.STAT_SORCERY
        )
    }
).register()
stat(
    Ids.STAT_BEWITCHING,
    groups = listOf(group(Ids.GROUP_MAGIC), group(Ids.GROUP_CHARISMA)),
    formula = { it ->
        (it.getDirect(Ids.STAT_MAGIC) + it.getDirect(Ids.STAT_MAGIC) + it.getDirect(Ids.STAT_CHARISMA)) / 3 + it.getDirect(
            Ids.STAT_BEWITCHING
        )
    }
).register()
// CHARISMA
stat(
    Ids.STAT_BRAVADO,
    groups = listOf(group(Ids.GROUP_CHARISMA), group(Ids.GROUP_BODY)),
    formula = { it ->
        (it.getDirect(Ids.STAT_CHARISMA) + it.getDirect(Ids.STAT_CHARISMA) + it.getDirect(Ids.STAT_BODY)) / 3 + it.getDirect(
            Ids.STAT_BRAVADO
        )
    }
).register()
stat(
    Ids.STAT_PERSUASION,
    groups = listOf(group(Ids.GROUP_CHARISMA), group(Ids.GROUP_MIND)),
    formula = { it ->
        (it.getDirect(Ids.STAT_CHARISMA) + it.getDirect(Ids.STAT_CHARISMA) + it.getDirect(Ids.STAT_MIND)) / 3 + it.getDirect(
            Ids.STAT_PERSUASION
        )
    }
).register()
stat(
    Ids.STAT_MESMERISM,
    groups = listOf(group(Ids.GROUP_CHARISMA), group(Ids.GROUP_MAGIC)),
    formula = { it ->
        (it.getDirect(Ids.STAT_CHARISMA) + it.getDirect(Ids.STAT_CHARISMA) + it.getDirect(Ids.STAT_MAGIC)) / 3 + it.getDirect(
            Ids.STAT_MESMERISM
        )
    }
).register()
stat(
    Ids.STAT_CHARM,
    groups = listOf(group(Ids.GROUP_CHARISMA)),
    formula = { it ->
        (it.getDirect(Ids.STAT_CHARISMA) + it.getDirect(Ids.STAT_CHARISMA) + it.getDirect(Ids.STAT_CHARISMA)) / 3 + it.getDirect(
            Ids.STAT_CHARM
        )
    }
).register()

// --- CALCULATED STATS ---
stat(
    Ids.STAT_SPEED,
    groups = listOf(group(Ids.GROUP_BODY)),
    formula = { it.getDirect(Ids.STAT_BODY) }
).register()
stat(
    Ids.STAT_VISION,
    groups = listOf(group(Ids.GROUP_BODY)),
    formula = { it.getDirect(Ids.STAT_BODY) + 3 }
).register()
stat(
    Ids.STAT_CARRY_CAPACITY,
    max = 100,
    groups = listOf(group(Ids.GROUP_BODY)),
    formula = { it.getDirect(Ids.STAT_BODY) * 10 }
).register()
