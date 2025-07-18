import heroes.journey.modlib.art.animationRenderable
import heroes.journey.modlib.art.stillRenderable
import heroes.journey.modlib.art.textureMap
import heroes.journey.modlib.Ids

// Textures - included by basegame mod

val Sprites = textureMap("sprites", "sprites.png", 16, 16).register()
val OverworldTileset = textureMap(Ids.OVERWORLD_TILESET, "Overworld_Tileset.png", 16, 16).register()

stillRenderable(Ids.PLAYER_SPRITE, Sprites.id, 1, 1).register()
stillRenderable(Ids.CAPITAL_SPRITE, OverworldTileset.id, 9, 14).register()
stillRenderable(Ids.TOWN_SPRITE, OverworldTileset.id, 7, 12).register()
stillRenderable(Ids.DUNGEON_SPRITE, OverworldTileset.id, 17, 4).register()

stillRenderable(Ids.LIGHT_FOG, Sprites.id, 0, 1).register()
stillRenderable(Ids.DENSE_FOG, Sprites.id, 0, 0).register()

stillRenderable(Ids.RED, "ui", 2, 0).register()
stillRenderable(Ids.LIGHT_BLUE, "ui", 4, 1).register()
stillRenderable(Ids.PURPLE, "ui", 4, 0).register()
stillRenderable(Ids.YELLOW, "ui", 3, 1).register()

animationRenderable(Ids.CURSOR, "ui", 0.5f, frames = listOf(0 to 0, 0 to 0, 0 to 1)).register()
animationRenderable(Ids.MAP_POINTER, "ui", 0.5f, frames = listOf(3 to 3, 3 to 3, 3 to 4)).register()

// Demon Animations
deepDiveAnimation(Ids.ANTLERED_RASCAL, "Demon", "Antlered Rascal")
deepDiveAnimation(Ids.CLAWED_ABOMINATION, "Demon", "Clawed Abomination")
deepDiveAnimation(Ids.CRIMSON_IMP, "Demon", "Crimson Imp")
deepDiveAnimation(Ids.DEPRAVED_BLACKGUARD, "Demon", "Depraved Blackguard")
deepDiveAnimation(Ids.FLEDGLING_DEMON, "Demon", "Fledgling Demon")
deepDiveAnimation(Ids.FLOATING_EYE, "Demon", "Floating Eye")
deepDiveAnimation(Ids.FOUL_GOUGER, "Demon", "Foul Gouger")
deepDiveAnimation(Ids.GRINNING_GREMLIN, "Demon", "Grinning Gremlin")
deepDiveAnimation(Ids.NEFARIOUS_SCAMP, "Demon", "Nefarious Scamp")
deepDiveAnimation(Ids.PIT_BALOR, "Demon", "Pit Balor")
deepDiveAnimation(Ids.POINTED_DEMONSPAWN, "Demon", "Pointed Demonspawn")
deepDiveAnimation(Ids.RASCALLY_DEMONLING, "Demon", "Rascally Demonling")
deepDiveAnimation(Ids.SKEWERING_STALKER, "Demon", "Skewering Stalker")
deepDiveAnimation(Ids.TAINTED_SCOUNDREL, "Demon", "Tainted Scoundrel")
deepDiveAnimation(Ids.WARP_SKULL, "Demon", "Warp Skull")

// Dragon Animations
deepDiveAnimation(Ids.ADULT_GREEN_DRAGON, "Dragon", "Adult Green Dragon")
deepDiveAnimation(Ids.ADULT_WHITE_DRAGON, "Dragon", "Adult White Dragon")
deepDiveAnimation(Ids.AQUA_DRAKE, "Dragon", "Aqua Drake")
deepDiveAnimation(Ids.BABY_BRASS_DRAGON, "Dragon", "Baby Brass Dragon")
deepDiveAnimation(Ids.BABY_COPPER_DRAGON, "Dragon", "Baby Copper Dragon")
deepDiveAnimation(Ids.BABY_GREEN_DRAGON, "Dragon", "Baby Green Dragon")
deepDiveAnimation(Ids.BABY_WHITE_DRAGON, "Dragon", "Baby White Dragon")
deepDiveAnimation(Ids.JUVENILE_BRONZE_DRAGON, "Dragon", "Juvenile Bronze Dragon")
deepDiveAnimation(Ids.MATURE_BRONZE_DRAGON, "Dragon", "Mature Bronze Dragon")
deepDiveAnimation(Ids.MUD_WYVERN, "Dragon", "Mud Wyvern")
deepDiveAnimation(Ids.POISON_DRAKE, "Dragon", "Poison Drake")
deepDiveAnimation(Ids.PYGMY_WYVERN, "Dragon", "Pygmy Wyvern")
deepDiveAnimation(Ids.VIRIDIAN_DRAKE, "Dragon", "Viridian Drake")
deepDiveAnimation(Ids.YOUNG_BRASS_DRAGON, "Dragon", "Young Brass Dragon")
deepDiveAnimation(Ids.YOUNG_RED_DRAGON, "Dragon", "Young Red Dragon")

// Holy Animations
deepDiveAnimation(Ids.BLESSED_GLADIATOR, "Holy", "Blessed Gladiator")
deepDiveAnimation(Ids.BOLD_MAN_AT_ARMS, "Holy", "Bold Man At Arms")
deepDiveAnimation(Ids.DETERMINED_SOLDIER, "Holy", "Determined Soldier")
deepDiveAnimation(Ids.DEVOUT_ACOLYTE, "Holy", "Devout Acolyte")
deepDiveAnimation(Ids.DIVINE_PLANETAR, "Holy", "Divine Planetar")
deepDiveAnimation(Ids.FAVORED_CLERIC, "Holy", "Favored Cleric")
deepDiveAnimation(Ids.FLOATING_CHERUB, "Holy", "Floating Cherub")
deepDiveAnimation(Ids.GENTLE_SHEPARD, "Holy", "Gentle Shepard")
deepDiveAnimation(Ids.HOLY_CRUSADER, "Holy", "Holy Crusader")
deepDiveAnimation(Ids.JOVIAL_FRIAR, "Holy", "Jovial Friar")
deepDiveAnimation(Ids.RESOLUTE_ANGEL, "Holy", "Resolute Angel")
deepDiveAnimation(Ids.RIGHTEOUS_DEVA, "Holy", "Righteous Deva")
deepDiveAnimation(Ids.SWORD_ARCHON, "Holy", "Sword Archon")
deepDiveAnimation(Ids.VETERAN_SWORDSMAN, "Holy", "Veteran Swordsman")
deepDiveAnimation(Ids.ZEALOUS_PRIEST, "Holy", "Zealous Priest")

// Humanoid Animations
deepDiveAnimation(Ids.BESTIAL_LIZARDFOLK, "Humanoid", "Bestial Lizardfolk")
deepDiveAnimation(Ids.GOBLIN_ARCHER, "Humanoid", "Goblin Archer")
deepDiveAnimation(Ids.GOBLIN_FANATIC, "Humanoid", "Goblin Fanatic")
deepDiveAnimation(Ids.GOBLIN_FIGHTER, "Humanoid", "Goblin Fighter")
deepDiveAnimation(Ids.GOBLIN_OCCULTIST, "Humanoid", "Goblin Occultist")
deepDiveAnimation(Ids.GOBLIN_WOLF_RIDER, "Humanoid", "Goblin Wolf Rider")
deepDiveAnimation(Ids.HALFLING_ASSASSIN, "Humanoid", "Halfling Assassin")
deepDiveAnimation(Ids.HALFLING_BARD, "Humanoid", "Halfling Bard")
deepDiveAnimation(Ids.HALFLING_RANGER, "Humanoid", "Halfling Ranger")
deepDiveAnimation(Ids.HALFLING_ROGUE, "Humanoid", "Halfling Rogue")
deepDiveAnimation(Ids.HALFLING_SLINGER, "Humanoid", "Halfling Slinger")
deepDiveAnimation(Ids.LIZARDFOLK_ARCHER, "Humanoid", "Lizardfolk Archer")
deepDiveAnimation(Ids.LIZARDFOLK_GLADIATOR, "Humanoid", "Lizardfolk Gladiator")
deepDiveAnimation(Ids.LIZARDFOLK_SCOUT, "Humanoid", "Lizardfolk Scout")
deepDiveAnimation(Ids.LIZARDFOLK_SPEARMAN, "Humanoid", "Lizardfolk Spearman")

// Humanoid II Animations
deepDiveAnimation(Ids.ADVENTUROUS_ADOLESCENT, "Humanoid II", "Adventurous Adolescent")
deepDiveAnimation(Ids.BOISTEROUS_YOUTH, "Humanoid II", "Boisterous Youth")
deepDiveAnimation(Ids.ELF_BLADEDANCER, "Humanoid II", "Elf Bladedancer")
deepDiveAnimation(Ids.ELF_ENCHANTER, "Humanoid II", "Elf Enchanter")
deepDiveAnimation(Ids.ELF_LORD, "Humanoid II", "Elf Lord")
deepDiveAnimation(Ids.ELF_SHARPSHOOTER, "Humanoid II", "Elf Sharpshooter")
deepDiveAnimation(Ids.ELF_WAYFARER, "Humanoid II", "Elf Wayfarer")
deepDiveAnimation(Ids.JOYFUL_KID, "Humanoid II", "Joyful Kid")
deepDiveAnimation(Ids.MERFOLK_AQUAMANCER, "Humanoid II", "Merfolk Aquamancer")
deepDiveAnimation(Ids.MERFOLK_IMPALER, "Humanoid II", "Merfolk Impaler")
deepDiveAnimation(Ids.MERFOLK_JAVELINEER, "Humanoid II", "Merfolk Javelineer")
deepDiveAnimation(Ids.MERFOLK_MYSTIC, "Humanoid II", "Merfolk Mystic")
deepDiveAnimation(Ids.MERFOLK_SCOUT, "Humanoid II", "Merfolk Scout")
deepDiveAnimation(Ids.OVERWORKED_VILLAGER, "Humanoid II", "Overworked Villager")
deepDiveAnimation(Ids.PLAYFUL_CHILD, "Humanoid II", "Playful Child")

// Magical Animations
deepDiveAnimation(Ids.ADEPT_NECROMANCER, "Magical", "Adept Necromancer")
deepDiveAnimation(Ids.CORRUPTED_TREANT, "Magical", "Corrupted Treant")
deepDiveAnimation(Ids.DEFT_SORCERESS, "Magical", "Deft Sorceress")
deepDiveAnimation(Ids.EARTH_ELEMENTAL, "Magical", "Earth Elemental")
deepDiveAnimation(Ids.EXPERT_DRUID, "Magical", "Expert Druid")
deepDiveAnimation(Ids.FIRE_ELEMENTAL, "Magical", "Fire Elemental")
deepDiveAnimation(Ids.FLUTTERING_PIXIE, "Magical", "Fluttering Pixie")
deepDiveAnimation(Ids.GLOWING_WISP, "Magical", "Glowing Wisp")
deepDiveAnimation(Ids.GRIZZLED_TREANT, "Magical", "Grizzled Treant")
deepDiveAnimation(Ids.ICE_GOLEM, "Magical", "Ice Golem")
deepDiveAnimation(Ids.IRON_GOLEM, "Magical", "Iron Golem")
deepDiveAnimation(Ids.MAGICAL_FAIRY, "Magical", "Magical Fairy")
deepDiveAnimation(Ids.NOVICE_PYROMANCER, "Magical", "Novice Pyromancer")
deepDiveAnimation(Ids.VILE_WITCH, "Magical", "Vile Witch")
deepDiveAnimation(Ids.WATER_ELEMENTAL, "Magical", "Water Elemental")

// Monster Animations
deepDiveAnimation(Ids.BLINDED_GRIMLOCK, "Monster", "Blinded Grimlock")
deepDiveAnimation(Ids.BLOODSHOT_EYE, "Monster", "Bloodshot Eye")
deepDiveAnimation(Ids.CRIMSON_SLAAD, "Monster", "Crimson Slaad")
deepDiveAnimation(Ids.CRUSHING_CYCLOPS, "Monster", "Crushing Cyclops")
deepDiveAnimation(Ids.DEATH_SLIME, "Monster", "Death Slime")
deepDiveAnimation(Ids.FUNGAL_MYCONID, "Monster", "Fungal Myconid")
deepDiveAnimation(Ids.HUMONGOUS_ETTIN, "Monster", "Humongous Ettin")
deepDiveAnimation(Ids.MURKY_SLAAD, "Monster", "Murky Slaad")
deepDiveAnimation(Ids.OCHRE_JELLY, "Monster", "Ochre Jelly")
deepDiveAnimation(Ids.OCULAR_WATCHER, "Monster", "Ocular Watcher")
deepDiveAnimation(Ids.BRAWNY_OGRE, "Monster", "Brawny Ogre")
deepDiveAnimation(Ids.RED_CAP, "Monster", "Red Cap")
deepDiveAnimation(Ids.SHRIEKER_MUSHROOM, "Monster", "Shrieker Mushroom")
deepDiveAnimation(Ids.STONE_TROLL, "Monster", "Stone Troll")
deepDiveAnimation(Ids.SWAMP_TROLL, "Monster", "Swamp Troll")

// Undead Animations
deepDiveAnimation(Ids.BOUND_CADAVER, "Undead", "Bound Cadaver")
deepDiveAnimation(Ids.BRITTLE_ARCHER, "Undead", "Brittle Archer")
deepDiveAnimation(Ids.CARCASS_FEEDER, "Undead", "Carcass Feeder")
deepDiveAnimation(Ids.DECREPIT_BONES, "Undead", "Decrepit Bones")
deepDiveAnimation(Ids.DISMEMBERED_CRAWLER, "Undead", "Dismembered Crawler")
deepDiveAnimation(Ids.GHASTLY_EYE, "Undead", "Ghastly Eye")
deepDiveAnimation(Ids.GIANT_ROYAL_SCARAB, "Undead", "Giant Royal Scarab")
deepDiveAnimation(Ids.GRAVE_REVENANT, "Undead", "Grave Revenant")
deepDiveAnimation(Ids.MUTILATED_STUMBLER, "Undead", "Mutilated Stumbler")
deepDiveAnimation(Ids.ROYAL_SCARAB, "Undead", "Royal Scarab")
deepDiveAnimation(Ids.SAND_GHOUL, "Undead", "Sand Ghoul")
deepDiveAnimation(Ids.SKITTERING_HAND, "Undead", "Skittering Hand")
deepDiveAnimation(Ids.TOXIC_HOUND, "Undead", "Toxic Hound")
deepDiveAnimation(Ids.UNRAVELING_CRAWLER, "Undead", "Unraveling Crawler")
deepDiveAnimation(Ids.VAMPIRE_BAT, "Undead", "Vampire Bat")

// Vermin Animations
deepDiveAnimation(Ids.ACID_ANT, "Vermin", "Acid Ant")
deepDiveAnimation(Ids.BLOATED_BEDBUG, "Vermin", "Bloated Bedbug")
deepDiveAnimation(Ids.DUNG_BEETLE, "Vermin", "Dung Beetle")
deepDiveAnimation(Ids.ENGORGED_TICK, "Vermin", "Engorged Tick")
deepDiveAnimation(Ids.FAMISHED_TICK, "Vermin", "Famished Tick")
deepDiveAnimation(Ids.FORAGING_MAGGOT, "Vermin", "Foraging Maggot")
deepDiveAnimation(Ids.INFECTED_MOUSE, "Vermin", "Infected Mouse")
deepDiveAnimation(Ids.LAVA_ANT, "Vermin", "Lava Ant")
deepDiveAnimation(Ids.MAWING_BEAVER, "Vermin", "Mawing Beaver")
deepDiveAnimation(Ids.TUNNELING_MOLE, "Vermin", "Tunneling Mole")
deepDiveAnimation(Ids.PLAGUE_BAT, "Vermin", "Plague Bat")
deepDiveAnimation(Ids.RHINO_BEETLE, "Vermin", "Rhino Beetle")
deepDiveAnimation(Ids.SOLDIER_ANT, "Vermin", "Soldier Ant")
deepDiveAnimation(Ids.SWOOPING_BAT, "Vermin", "Swooping Bat")
deepDiveAnimation(Ids.TAINTED_COCKROACH, "Vermin", "Tainted Cockroach")

fun deepDiveAnimation(id: String, grouping: String, name: String) {
    val texture = textureMap(
        id,
        "deepdivegamingsprites/Basic " + grouping + " Animations/" + name + "/" + name.replace(
            " ",
            ""
        ) + ".png",
        16, 16
    ).register()

    animationRenderable(id, texture.id, 0.5f).register()
}
