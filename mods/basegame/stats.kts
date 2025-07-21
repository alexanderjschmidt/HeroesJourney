import heroes.journey.modlib.Ids
import heroes.journey.modlib.attributes.group
import heroes.journey.modlib.attributes.stat

// --- GROUPS ---
group { id = Ids.GROUP_BODY }.register()
group { id = Ids.GROUP_MIND }.register()
group { id = Ids.GROUP_MAGIC }.register()
group { id = Ids.GROUP_CHARISMA }.register()
group { id = Ids.GROUP_RENOWN }.register()
group { id = Ids.GROUP_MAX }.register()
group { id = Ids.GROUP_MIN }.register()

// --- BASE STATS ---
stat {
    id = Ids.STAT_BODY
    group(Ids.GROUP_BODY)
}.register()
stat {
    id = Ids.STAT_MIND
    group(Ids.GROUP_MIND)
}.register()
stat {
    id = Ids.STAT_MAGIC
    group(Ids.GROUP_MAGIC)
}.register()
stat {
    id = Ids.STAT_CHARISMA
    group(Ids.GROUP_CHARISMA)
}.register()

// --- RENOWN STATS ---
stat {
    id = Ids.STAT_VALOR
    min = 0
    group(Ids.GROUP_RENOWN)
    group(Ids.GROUP_BODY)
}.register()
stat {
    id = Ids.STAT_INSIGHT
    min = 0
    group(Ids.GROUP_RENOWN)
    group(Ids.GROUP_MIND)
}.register()
stat {
    id = Ids.STAT_ARCANUM
    min = 0
    group(Ids.GROUP_RENOWN)
    group(Ids.GROUP_MAGIC)
}.register()
stat {
    id = Ids.STAT_INFLUENCE
    min = 0
    group(Ids.GROUP_RENOWN)
    group(Ids.GROUP_CHARISMA)
}.register()

// --- CALCULATED STATS ---
stat {
    id = Ids.STAT_SPEED
    formula = { it.getDirect(Ids.STAT_BODY) }
}.register()
stat {
    id = Ids.STAT_VISION
    formula = { it.getDirect(Ids.STAT_BODY) + 3 }
}.register()
stat {
    id = Ids.STAT_CARRY_CAPACITY
    max = 100
    formula = { it.getDirect(Ids.STAT_BODY) * 10 }
}.register()
