import heroes.journey.modlib.Ids
import heroes.journey.modlib.IdsC
import heroes.journey.modlib.art.animationRenderable
import heroes.journey.modlib.art.stillRenderable
import heroes.journey.modlib.art.textureMap

// Textures - included by basegame mod

val Sprites = textureMap { id = "sprites"; location = "sprites.png"; width = 16; height = 16 }.register()
val OverworldTileset =
    textureMap { id = Ids.OVERWORLD_TILESET; location = "Overworld_Tileset.png"; width = 16; height = 16 }.register()

stillRenderable { id = Ids.PLAYER_SPRITE; textureMapId = Sprites.id; x = 1; y = 1 }.register()
stillRenderable { id = Ids.CAPITAL_SPRITE; textureMapId = OverworldTileset.id; x = 9; y = 14 }.register()
stillRenderable { id = Ids.TOWN_SPRITE; textureMapId = OverworldTileset.id; x = 7; y = 12 }.register()
stillRenderable { id = Ids.DUNGEON_SPRITE; textureMapId = OverworldTileset.id; x = 17; y = 4 }.register()

stillRenderable { id = Ids.LIGHT_FOG; textureMapId = Sprites.id; x = 0; y = 1 }.register()
stillRenderable { id = Ids.DENSE_FOG; textureMapId = Sprites.id; x = 0; y = 0 }.register()

stillRenderable { id = Ids.RED; textureMapId = "ui"; x = 2; y = 0 }.register()
stillRenderable { id = Ids.GREEN; textureMapId = "ui"; x = 3; y = 0 }.register()
stillRenderable { id = Ids.BLUE; textureMapId = "ui"; x = 2; y = 1 }.register()
stillRenderable { id = Ids.LIGHT_BLUE; textureMapId = "ui"; x = 4; y = 1 }.register()
stillRenderable { id = Ids.PURPLE; textureMapId = "ui"; x = 4; y = 0 }.register()
stillRenderable { id = Ids.YELLOW; textureMapId = "ui"; x = 3; y = 1 }.register()

animationRenderable {
    id = Ids.CURSOR; textureMapId = "ui"; frameDuration = 0.5f; frames = listOf(0 to 0, 0 to 0, 0 to 1)
}.register()
animationRenderable {
    id = Ids.MAP_POINTER; textureMapId = "ui"; frameDuration = 0.5f; frames = listOf(3 to 3, 3 to 3, 3 to 4)
}.register()

// Demon Animations
deepDiveAnimation(IdsC.RENDER_ANTLERED_RASCAL, "Demon", "Antlered Rascal")
deepDiveAnimation(IdsC.RENDER_CLAWED_ABOMINATION, "Demon", "Clawed Abomination")
deepDiveAnimation(IdsC.RENDER_CRIMSON_IMP, "Demon", "Crimson Imp")
deepDiveAnimation(IdsC.RENDER_DEPRAVED_BLACKGUARD, "Demon", "Depraved Blackguard")
deepDiveAnimation(IdsC.RENDER_FLEDGLING_DEMON, "Demon", "Fledgling Demon")
deepDiveAnimation(IdsC.RENDER_FLOATING_EYE, "Demon", "Floating Eye")
deepDiveAnimation(IdsC.RENDER_FOUL_GOUGER, "Demon", "Foul Gouger")
deepDiveAnimation(IdsC.RENDER_GRINNING_GREMLIN, "Demon", "Grinning Gremlin")
deepDiveAnimation(IdsC.RENDER_NEFARIOUS_SCAMP, "Demon", "Nefarious Scamp")
deepDiveAnimation(IdsC.RENDER_PIT_BALOR, "Demon", "Pit Balor")
deepDiveAnimation(IdsC.RENDER_POINTED_DEMONSPAWN, "Demon", "Pointed Demonspawn")
deepDiveAnimation(IdsC.RENDER_RASCALLY_DEMONLING, "Demon", "Rascally Demonling")
deepDiveAnimation(IdsC.RENDER_SKEWERING_STALKER, "Demon", "Skewering Stalker")
deepDiveAnimation(IdsC.RENDER_TAINTED_SCOUNDREL, "Demon", "Tainted Scoundrel")
deepDiveAnimation(IdsC.RENDER_WARP_SKULL, "Demon", "Warp Skull")

// Dragon Animations
deepDiveAnimation(IdsC.RENDER_ADULT_GREEN_DRAGON, "Dragon", "Adult Green Dragon")
deepDiveAnimation(IdsC.RENDER_ADULT_WHITE_DRAGON, "Dragon", "Adult White Dragon")
deepDiveAnimation(IdsC.RENDER_AQUA_DRAKE, "Dragon", "Aqua Drake")
deepDiveAnimation(IdsC.RENDER_BABY_BRASS_DRAGON, "Dragon", "Baby Brass Dragon")
deepDiveAnimation(IdsC.RENDER_BABY_COPPER_DRAGON, "Dragon", "Baby Copper Dragon")
deepDiveAnimation(IdsC.RENDER_BABY_GREEN_DRAGON, "Dragon", "Baby Green Dragon")
deepDiveAnimation(IdsC.RENDER_BABY_WHITE_DRAGON, "Dragon", "Baby White Dragon")
deepDiveAnimation(IdsC.RENDER_JUVENILE_BRONZE_DRAGON, "Dragon", "Juvenile Bronze Dragon")
deepDiveAnimation(IdsC.RENDER_MATURE_BRONZE_DRAGON, "Dragon", "Mature Bronze Dragon")
deepDiveAnimation(IdsC.RENDER_MUD_WYVERN, "Dragon", "Mud Wyvern")
deepDiveAnimation(IdsC.RENDER_POISON_DRAKE, "Dragon", "Poison Drake")
deepDiveAnimation(IdsC.RENDER_PYGMY_WYVERN, "Dragon", "Pygmy Wyvern")
deepDiveAnimation(IdsC.RENDER_VIRIDIAN_DRAKE, "Dragon", "Viridian Drake")
deepDiveAnimation(IdsC.RENDER_YOUNG_BRASS_DRAGON, "Dragon", "Young Brass Dragon")
deepDiveAnimation(IdsC.RENDER_YOUNG_RED_DRAGON, "Dragon", "Young Red Dragon")

// Holy Animations
deepDiveAnimation(IdsC.RENDER_BLESSED_GLADIATOR, "Holy", "Blessed Gladiator")
deepDiveAnimation(IdsC.RENDER_BOLD_MAN_AT_ARMS, "Holy", "Bold Man At Arms")
deepDiveAnimation(IdsC.RENDER_DETERMINED_SOLDIER, "Holy", "Determined Soldier")
deepDiveAnimation(IdsC.RENDER_DEVOUT_ACOLYTE, "Holy", "Devout Acolyte")
deepDiveAnimation(IdsC.RENDER_DIVINE_PLANETAR, "Holy", "Divine Planetar")
deepDiveAnimation(IdsC.RENDER_FAVORED_CLERIC, "Holy", "Favored Cleric")
deepDiveAnimation(IdsC.RENDER_FLOATING_CHERUB, "Holy", "Floating Cherub")
deepDiveAnimation(IdsC.RENDER_GENTLE_SHEPARD, "Holy", "Gentle Shepard")
deepDiveAnimation(IdsC.RENDER_HOLY_CRUSADER, "Holy", "Holy Crusader")
deepDiveAnimation(IdsC.RENDER_JOVIAL_FRIAR, "Holy", "Jovial Friar")
deepDiveAnimation(IdsC.RENDER_RESOLUTE_ANGEL, "Holy", "Resolute Angel")
deepDiveAnimation(IdsC.RENDER_RIGHTEOUS_DEVA, "Holy", "Righteous Deva")
deepDiveAnimation(IdsC.RENDER_SWORD_ARCHON, "Holy", "Sword Archon")
deepDiveAnimation(IdsC.RENDER_VETERAN_SWORDSMAN, "Holy", "Veteran Swordsman")
deepDiveAnimation(IdsC.RENDER_ZEALOUS_PRIEST, "Holy", "Zealous Priest")

// Humanoid Animations
deepDiveAnimation(IdsC.RENDER_BESTIAL_LIZARDFOLK, "Humanoid", "Bestial Lizardfolk")
deepDiveAnimation(IdsC.RENDER_GOBLIN_ARCHER, "Humanoid", "Goblin Archer")
deepDiveAnimation(IdsC.RENDER_GOBLIN_FANATIC, "Humanoid", "Goblin Fanatic")
deepDiveAnimation(IdsC.RENDER_GOBLIN_FIGHTER, "Humanoid", "Goblin Fighter")
deepDiveAnimation(IdsC.RENDER_GOBLIN_OCCULTIST, "Humanoid", "Goblin Occultist")
deepDiveAnimation(IdsC.RENDER_GOBLIN_WOLF_RIDER, "Humanoid", "Goblin Wolf Rider")
deepDiveAnimation(IdsC.RENDER_HALFLING_ASSASSIN, "Humanoid", "Halfling Assassin")
deepDiveAnimation(IdsC.RENDER_HALFLING_BARD, "Humanoid", "Halfling Bard")
deepDiveAnimation(IdsC.RENDER_HALFLING_RANGER, "Humanoid", "Halfling Ranger")
deepDiveAnimation(IdsC.RENDER_HALFLING_ROGUE, "Humanoid", "Halfling Rogue")
deepDiveAnimation(IdsC.RENDER_HALFLING_SLINGER, "Humanoid", "Halfling Slinger")
deepDiveAnimation(IdsC.RENDER_LIZARDFOLK_ARCHER, "Humanoid", "Lizardfolk Archer")
deepDiveAnimation(IdsC.RENDER_LIZARDFOLK_GLADIATOR, "Humanoid", "Lizardfolk Gladiator")
deepDiveAnimation(IdsC.RENDER_LIZARDFOLK_SCOUT, "Humanoid", "Lizardfolk Scout")
deepDiveAnimation(IdsC.RENDER_LIZARDFOLK_SPEARMAN, "Humanoid", "Lizardfolk Spearman")

// Humanoid II Animations
deepDiveAnimation(IdsC.RENDER_ADVENTUROUS_ADOLESCENT, "Humanoid II", "Adventurous Adolescent")
deepDiveAnimation(IdsC.RENDER_BOISTEROUS_YOUTH, "Humanoid II", "Boisterous Youth")
deepDiveAnimation(IdsC.RENDER_ELF_BLADEDANCER, "Humanoid II", "Elf Bladedancer")
deepDiveAnimation(IdsC.RENDER_ELF_ENCHANTER, "Humanoid II", "Elf Enchanter")
deepDiveAnimation(IdsC.RENDER_ELF_LORD, "Humanoid II", "Elf Lord")
deepDiveAnimation(IdsC.RENDER_ELF_SHARPSHOOTER, "Humanoid II", "Elf Sharpshooter")
deepDiveAnimation(IdsC.RENDER_ELF_WAYFARER, "Humanoid II", "Elf Wayfarer")
deepDiveAnimation(IdsC.RENDER_JOYFUL_KID, "Humanoid II", "Joyful Kid")
deepDiveAnimation(IdsC.RENDER_MERFOLK_AQUAMANCER, "Humanoid II", "Merfolk Aquamancer")
deepDiveAnimation(IdsC.RENDER_MERFOLK_IMPALER, "Humanoid II", "Merfolk Impaler")
deepDiveAnimation(IdsC.RENDER_MERFOLK_JAVELINEER, "Humanoid II", "Merfolk Javelineer")
deepDiveAnimation(IdsC.RENDER_MERFOLK_MYSTIC, "Humanoid II", "Merfolk Mystic")
deepDiveAnimation(IdsC.RENDER_MERFOLK_SCOUT, "Humanoid II", "Merfolk Scout")
deepDiveAnimation(IdsC.RENDER_OVERWORKED_VILLAGER, "Humanoid II", "Overworked Villager")
deepDiveAnimation(IdsC.RENDER_PLAYFUL_CHILD, "Humanoid II", "Playful Child")

// Magical Animations
deepDiveAnimation(IdsC.RENDER_ADEPT_NECROMANCER, "Magical", "Adept Necromancer")
deepDiveAnimation(IdsC.RENDER_CORRUPTED_TREANT, "Magical", "Corrupted Treant")
deepDiveAnimation(IdsC.RENDER_DEFT_SORCERESS, "Magical", "Deft Sorceress")
deepDiveAnimation(IdsC.RENDER_EARTH_ELEMENTAL, "Magical", "Earth Elemental")
deepDiveAnimation(IdsC.RENDER_EXPERT_DRUID, "Magical", "Expert Druid")
deepDiveAnimation(IdsC.RENDER_FIRE_ELEMENTAL, "Magical", "Fire Elemental")
deepDiveAnimation(IdsC.RENDER_FLUTTERING_PIXIE, "Magical", "Fluttering Pixie")
deepDiveAnimation(IdsC.RENDER_GLOWING_WISP, "Magical", "Glowing Wisp")
deepDiveAnimation(IdsC.RENDER_GRIZZLED_TREANT, "Magical", "Grizzled Treant")
deepDiveAnimation(IdsC.RENDER_ICE_GOLEM, "Magical", "Ice Golem")
deepDiveAnimation(IdsC.RENDER_IRON_GOLEM, "Magical", "Iron Golem")
deepDiveAnimation(IdsC.RENDER_MAGICAL_FAIRY, "Magical", "Magical Fairy")
deepDiveAnimation(IdsC.RENDER_NOVICE_PYROMANCER, "Magical", "Novice Pyromancer")
deepDiveAnimation(IdsC.RENDER_VILE_WITCH, "Magical", "Vile Witch")
deepDiveAnimation(IdsC.RENDER_WATER_ELEMENTAL, "Magical", "Water Elemental")

// Monster Animations
deepDiveAnimation(IdsC.RENDER_BLINDED_GRIMLOCK, "Monster", "Blinded Grimlock")
deepDiveAnimation(IdsC.RENDER_BLOODSHOT_EYE, "Monster", "Bloodshot Eye")
deepDiveAnimation(IdsC.RENDER_CRIMSON_SLAAD, "Monster", "Crimson Slaad")
deepDiveAnimation(IdsC.RENDER_CRUSHING_CYCLOPS, "Monster", "Crushing Cyclops")
deepDiveAnimation(IdsC.RENDER_DEATH_SLIME, "Monster", "Death Slime")
deepDiveAnimation(IdsC.RENDER_FUNGAL_MYCONID, "Monster", "Fungal Myconid")
deepDiveAnimation(IdsC.RENDER_HUMONGOUS_ETTIN, "Monster", "Humongous Ettin")
deepDiveAnimation(IdsC.RENDER_MURKY_SLAAD, "Monster", "Murky Slaad")
deepDiveAnimation(IdsC.RENDER_OCHRE_JELLY, "Monster", "Ochre Jelly")
deepDiveAnimation(IdsC.RENDER_OCULAR_WATCHER, "Monster", "Ocular Watcher")
deepDiveAnimation(IdsC.RENDER_BRAWNY_OGRE, "Monster", "Brawny Ogre")
deepDiveAnimation(IdsC.RENDER_RED_CAP, "Monster", "Red Cap")
deepDiveAnimation(IdsC.RENDER_SHRIEKER_MUSHROOM, "Monster", "Shrieker Mushroom")
deepDiveAnimation(IdsC.RENDER_STONE_TROLL, "Monster", "Stone Troll")
deepDiveAnimation(IdsC.RENDER_SWAMP_TROLL, "Monster", "Swamp Troll")

// Undead Animations
deepDiveAnimation(IdsC.RENDER_BOUND_CADAVER, "Undead", "Bound Cadaver")
deepDiveAnimation(IdsC.RENDER_BRITTLE_ARCHER, "Undead", "Brittle Archer")
deepDiveAnimation(IdsC.RENDER_CARCASS_FEEDER, "Undead", "Carcass Feeder")
deepDiveAnimation(IdsC.RENDER_DECREPIT_BONES, "Undead", "Decrepit Bones")
deepDiveAnimation(IdsC.RENDER_DISMEMBERED_CRAWLER, "Undead", "Dismembered Crawler")
deepDiveAnimation(IdsC.RENDER_GHASTLY_EYE, "Undead", "Ghastly Eye")
deepDiveAnimation(IdsC.RENDER_GIANT_ROYAL_SCARAB, "Undead", "Giant Royal Scarab")
deepDiveAnimation(IdsC.RENDER_GRAVE_REVENANT, "Undead", "Grave Revenant")
deepDiveAnimation(IdsC.RENDER_MUTILATED_STUMBLER, "Undead", "Mutilated Stumbler")
deepDiveAnimation(IdsC.RENDER_ROYAL_SCARAB, "Undead", "Royal Scarab")
deepDiveAnimation(IdsC.RENDER_SAND_GHOUL, "Undead", "Sand Ghoul")
deepDiveAnimation(IdsC.RENDER_SKITTERING_HAND, "Undead", "Skittering Hand")
deepDiveAnimation(IdsC.RENDER_TOXIC_HOUND, "Undead", "Toxic Hound")
deepDiveAnimation(IdsC.RENDER_UNRAVELING_CRAWLER, "Undead", "Unraveling Crawler")
deepDiveAnimation(IdsC.RENDER_VAMPIRE_BAT, "Undead", "Vampire Bat")

// Vermin Animations
deepDiveAnimation(IdsC.RENDER_ACID_ANT, "Vermin", "Acid Ant")
deepDiveAnimation(IdsC.RENDER_BLOATED_BEDBUG, "Vermin", "Bloated Bedbug")
deepDiveAnimation(IdsC.RENDER_DUNG_BEETLE, "Vermin", "Dung Beetle")
deepDiveAnimation(IdsC.RENDER_ENGORGED_TICK, "Vermin", "Engorged Tick")
deepDiveAnimation(IdsC.RENDER_FAMISHED_TICK, "Vermin", "Famished Tick")
deepDiveAnimation(IdsC.RENDER_FORAGING_MAGGOT, "Vermin", "Foraging Maggot")
deepDiveAnimation(IdsC.RENDER_INFECTED_MOUSE, "Vermin", "Infected Mouse")
deepDiveAnimation(IdsC.RENDER_LAVA_ANT, "Vermin", "Lava Ant")
deepDiveAnimation(IdsC.RENDER_MAWING_BEAVER, "Vermin", "Mawing Beaver")
deepDiveAnimation(IdsC.RENDER_TUNNELING_MOLE, "Vermin", "Tunneling Mole")
deepDiveAnimation(IdsC.RENDER_PLAGUE_BAT, "Vermin", "Plague Bat")
deepDiveAnimation(IdsC.RENDER_RHINO_BEETLE, "Vermin", "Rhino Beetle")
deepDiveAnimation(IdsC.RENDER_SOLDIER_ANT, "Vermin", "Soldier Ant")
deepDiveAnimation(IdsC.RENDER_SWOOPING_BAT, "Vermin", "Swooping Bat")
deepDiveAnimation(IdsC.RENDER_TAINTED_COCKROACH, "Vermin", "Tainted Cockroach")

fun deepDiveAnimation(id: String, grouping: String, name: String) {
    val texture = textureMap {
        this.id = id
        location = "deepdivegamingsprites/Basic $grouping Animations/$name/${name.replace(" ", "")}.png"
        width = 16
        height = 16
    }.register()
    animationRenderable {
        this.id = id
        textureMapId = texture.id
        frameDuration = 0.5f
    }.register()
}
