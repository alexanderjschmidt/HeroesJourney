import heroes.journey.modlib.Ids
import heroes.journey.modlib.attributes.group
import heroes.journey.modlib.attributes.stat
import heroes.journey.modlib.attributes.attributes

// --- GROUPS ---
group { id = Ids.GROUP_BODY }.register()
group { id = Ids.GROUP_MIND }.register()
group { id = Ids.GROUP_MAGIC }.register()
group { id = Ids.GROUP_CHARISMA }.register()
group { id = Ids.GROUP_BASESTATS }.register()
group { id = Ids.GROUP_RENOWN }.register()

// --- BASE STATS ---
stat {
    id = Ids.STAT_BODY
    group(Ids.GROUP_BASESTATS)
    group(Ids.GROUP_BODY)
}.register()
stat {
    id = Ids.STAT_MIND
    group(Ids.GROUP_BASESTATS)
    group(Ids.GROUP_MIND)
}.register()
stat {
    id = Ids.STAT_MAGIC
    group(Ids.GROUP_BASESTATS)
    group(Ids.GROUP_MAGIC)
}.register()
stat {
    id = Ids.STAT_CHARISMA
    group(Ids.GROUP_BASESTATS)
    group(Ids.GROUP_CHARISMA)
}.register()

// --- RENOWN STATS ---
stat {
    id = Ids.STAT_VALOR
    min = 0
    group(Ids.GROUP_RENOWN)
}.register()
stat {
    id = Ids.STAT_INSIGHT
    min = 0
    group(Ids.GROUP_RENOWN)
}.register()
stat {
    id = Ids.STAT_ARCANUM
    min = 0
    group(Ids.GROUP_RENOWN)
}.register()
stat {
    id = Ids.STAT_INFLUENCE
    min = 0
    group(Ids.GROUP_RENOWN)
}.register()

// --- CONFLUENCE STATS (complex) ---
// BODY
stat {
    id = Ids.STAT_MIGHT
    group(Ids.GROUP_BODY)
    formula = { it ->
        (it.getDirect(Ids.STAT_BODY) + it.getDirect(Ids.STAT_BODY) + it.getDirect(Ids.STAT_BODY)) / 3 + it.getDirect(
            Ids.STAT_MIGHT
        )
    }
}.register()
stat {
    id = Ids.STAT_SKILL
    group(Ids.GROUP_BODY)
    group(Ids.GROUP_MIND)
    formula = { it ->
        (it.getDirect(Ids.STAT_BODY) + it.getDirect(Ids.STAT_BODY) + it.getDirect(Ids.STAT_MIND)) / 3 + it.getDirect(
            Ids.STAT_SKILL
        )
    }
}.register()
stat {
    id = Ids.STAT_EMPOWERMENT
    group(Ids.GROUP_BODY)
    group(Ids.GROUP_MAGIC)
    formula = { it ->
        (it.getDirect(Ids.STAT_BODY) + it.getDirect(Ids.STAT_BODY) + it.getDirect(Ids.STAT_MAGIC)) / 3 + it.getDirect(
            Ids.STAT_EMPOWERMENT
        )
    }
}.register()
stat {
    id = Ids.STAT_CHIVALRY
    group(Ids.GROUP_BODY)
    group(Ids.GROUP_CHARISMA)
    formula = { it ->
        (it.getDirect(Ids.STAT_BODY) + it.getDirect(Ids.STAT_BODY) + it.getDirect(Ids.STAT_CHARISMA)) / 3 + it.getDirect(
            Ids.STAT_CHIVALRY
        )
    }
}.register()
// MIND
stat {
    id = Ids.STAT_TECHNIQUE
    group(Ids.GROUP_MIND)
    group(Ids.GROUP_BODY)
    formula = { it ->
        (it.getDirect(Ids.STAT_MIND) + it.getDirect(Ids.STAT_MIND) + it.getDirect(Ids.STAT_BODY)) / 3 + it.getDirect(
            Ids.STAT_TECHNIQUE
        )
    }
}.register()
stat {
    id = Ids.STAT_LOGIC
    group(Ids.GROUP_MIND)
    formula = { it ->
        (it.getDirect(Ids.STAT_MIND) + it.getDirect(Ids.STAT_MIND) + it.getDirect(Ids.STAT_MIND)) / 3 + it.getDirect(
            Ids.STAT_LOGIC
        )
    }
}.register()
stat {
    id = Ids.STAT_CONCENTRATION
    group(Ids.GROUP_MIND)
    group(Ids.GROUP_MAGIC)
    formula = { it ->
        (it.getDirect(Ids.STAT_MIND) + it.getDirect(Ids.STAT_MIND) + it.getDirect(Ids.STAT_MAGIC)) / 3 + it.getDirect(
            Ids.STAT_CONCENTRATION
        )
    }
}.register()
stat {
    id = Ids.STAT_CUNNING
    group(Ids.GROUP_MIND)
    group(Ids.GROUP_CHARISMA)
    formula = { it ->
        (it.getDirect(Ids.STAT_MIND) + it.getDirect(Ids.STAT_MIND) + it.getDirect(Ids.STAT_CHARISMA)) / 3 + it.getDirect(
            Ids.STAT_CUNNING
        )
    }
}.register()
// MAGIC
stat {
    id = Ids.STAT_ENCHANTING
    group(Ids.GROUP_MAGIC)
    group(Ids.GROUP_BODY)
    formula = { it ->
        (it.getDirect(Ids.STAT_MAGIC) + it.getDirect(Ids.STAT_MAGIC) + it.getDirect(Ids.STAT_BODY)) / 3 + it.getDirect(
            Ids.STAT_ENCHANTING
        )
    }
}.register()
stat {
    id = Ids.STAT_ILLUSION
    group(Ids.GROUP_MAGIC)
    group(Ids.GROUP_MIND)
    formula = { it ->
        (it.getDirect(Ids.STAT_MAGIC) + it.getDirect(Ids.STAT_MAGIC) + it.getDirect(Ids.STAT_MIND)) / 3 + it.getDirect(
            Ids.STAT_ILLUSION
        )
    }
}.register()
stat {
    id = Ids.STAT_SORCERY
    group(Ids.GROUP_MAGIC)
    formula = { it ->
        (it.getDirect(Ids.STAT_MAGIC) + it.getDirect(Ids.STAT_MAGIC) + it.getDirect(Ids.STAT_MAGIC)) / 3 + it.getDirect(
            Ids.STAT_SORCERY
        )
    }
}.register()
stat {
    id = Ids.STAT_BEWITCHING
    group(Ids.GROUP_MAGIC)
    group(Ids.GROUP_CHARISMA)
    formula = { it ->
        (it.getDirect(Ids.STAT_MAGIC) + it.getDirect(Ids.STAT_MAGIC) + it.getDirect(Ids.STAT_CHARISMA)) / 3 + it.getDirect(
            Ids.STAT_BEWITCHING
        )
    }
}.register()
// CHARISMA
stat {
    id = Ids.STAT_BRAVADO
    group(Ids.GROUP_CHARISMA)
    group(Ids.GROUP_BODY)
    formula = { it ->
        (it.getDirect(Ids.STAT_CHARISMA) + it.getDirect(Ids.STAT_CHARISMA) + it.getDirect(Ids.STAT_BODY)) / 3 + it.getDirect(
            Ids.STAT_BRAVADO
        )
    }
}.register()
stat {
    id = Ids.STAT_PERSUASION
    group(Ids.GROUP_CHARISMA)
    group(Ids.GROUP_MIND)
    formula = { it ->
        (it.getDirect(Ids.STAT_CHARISMA) + it.getDirect(Ids.STAT_CHARISMA) + it.getDirect(Ids.STAT_MIND)) / 3 + it.getDirect(
            Ids.STAT_PERSUASION
        )
    }
}.register()
stat {
    id = Ids.STAT_MESMERISM
    group(Ids.GROUP_CHARISMA)
    group(Ids.GROUP_MAGIC)
    formula = { it ->
        (it.getDirect(Ids.STAT_CHARISMA) + it.getDirect(Ids.STAT_CHARISMA) + it.getDirect(Ids.STAT_MAGIC)) / 3 + it.getDirect(
            Ids.STAT_MESMERISM
        )
    }
}.register()
stat {
    id = Ids.STAT_CHARM
    group(Ids.GROUP_CHARISMA)
    formula = { it ->
        (it.getDirect(Ids.STAT_CHARISMA) + it.getDirect(Ids.STAT_CHARISMA) + it.getDirect(Ids.STAT_CHARISMA)) / 3 + it.getDirect(
            Ids.STAT_CHARM
        )
    }
}.register()

// --- CALCULATED STATS ---
stat {
    id = Ids.STAT_SPEED
    group(Ids.GROUP_BODY)
    formula = { it.getDirect(Ids.STAT_BODY) }
}.register()
stat {
    id = Ids.STAT_VISION
    group(Ids.GROUP_BODY)
    formula = { it.getDirect(Ids.STAT_BODY) + 3 }
}.register()
stat {
    id = Ids.STAT_CARRY_CAPACITY
    max = 100
    group(Ids.GROUP_BODY)
    formula = { it.getDirect(Ids.STAT_BODY) * 10 }
}.register()
