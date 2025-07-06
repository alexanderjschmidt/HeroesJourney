import heroes.journey.initializers.base.Ids
import heroes.journey.initializers.base.Ids.*
import heroes.journey.utils.art.ResourceManager
import heroes.journey.utils.art.StillRenderable
import heroes.journey.utils.art.TextureMap
import heroes.journey.utils.art.animationRenderable

// Textures - included by basegame mod

val Sprites = TextureMap("sprites", "sprites.png", 16, 16).register()
val OverworldTileset = TextureMap(OVERWORLD_TILESET, "Overworld_Tileset.png", 16, 16).register()

StillRenderable(Ids.PLAYER_SPRITE, Sprites, 1, 1).register()
StillRenderable(Ids.CAPITAL_SPRITE, OverworldTileset, 9, 14).register()
StillRenderable(Ids.TOWN_SPRITE, OverworldTileset, 7, 12).register()
StillRenderable(Ids.DUNGEON_SPRITE, OverworldTileset, 17, 4).register()

StillRenderable(Ids.LIGHT_FOG, Sprites, 0, 1).register()
StillRenderable(Ids.DENSE_FOG, Sprites, 0, 0).register()

StillRenderable(Ids.RED, ResourceManager.UI, 2, 0).register()
StillRenderable(Ids.LIGHT_BLUE, ResourceManager.UI, 4, 1).register()
StillRenderable(Ids.PURPLE, ResourceManager.UI, 4, 0).register()
StillRenderable(Ids.YELLOW, ResourceManager.UI, 3, 1).register()

animationRenderable(Ids.CURSOR, ResourceManager.UI) {
    frameDuration = 0.5f
    frame(0, 0)
    frame(0, 0) // repeated intentionally
    frame(0, 1)
}.register()

animationRenderable(Ids.MAP_POINTER, ResourceManager.UI) {
    frameDuration = 0.5f
    frame(3, 3)
    frame(3, 3) // repeated
    frame(3, 4)
}.register()


// Demon Animations
deepDiveAnimation(ANTLERED_RASCAL, "Demon", "Antlered Rascal")
deepDiveAnimation(CLAWED_ABOMINATION, "Demon", "Clawed Abomination")
deepDiveAnimation(CRIMSON_IMP, "Demon", "Crimson Imp")
deepDiveAnimation(DEPRAVED_BLACKGUARD, "Demon", "Depraved Blackguard")
deepDiveAnimation(FLEDGLING_DEMON, "Demon", "Fledgling Demon")
deepDiveAnimation(FLOATING_EYE, "Demon", "Floating Eye")
deepDiveAnimation(FOUL_GOUGER, "Demon", "Foul Gouger")
deepDiveAnimation(GRINNING_GREMLIN, "Demon", "Grinning Gremlin")
deepDiveAnimation(NEFARIOUS_SCAMP, "Demon", "Nefarious Scamp")
deepDiveAnimation(PIT_BALOR, "Demon", "Pit Balor")
deepDiveAnimation(POINTED_DEMONSPAWN, "Demon", "Pointed Demonspawn")
deepDiveAnimation(RASCALLY_DEMONLING, "Demon", "Rascally Demonling")
deepDiveAnimation(SKEWERING_STALKER, "Demon", "Skewering Stalker")
deepDiveAnimation(TAINTED_SCOUNDREL, "Demon", "Tainted Scoundrel")
deepDiveAnimation(WARP_SKULL, "Demon", "Warp Skull")

// Dragon Animations
deepDiveAnimation(ADULT_GREEN_DRAGON, "Dragon", "Adult Green Dragon")
deepDiveAnimation(ADULT_WHITE_DRAGON, "Dragon", "Adult White Dragon")
deepDiveAnimation(AQUA_DRAKE, "Dragon", "Aqua Drake")
deepDiveAnimation(BABY_BRASS_DRAGON, "Dragon", "Baby Brass Dragon")
deepDiveAnimation(BABY_COPPER_DRAGON, "Dragon", "Baby Copper Dragon")
deepDiveAnimation(BABY_GREEN_DRAGON, "Dragon", "Baby Green Dragon")
deepDiveAnimation(BABY_WHITE_DRAGON, "Dragon", "Baby White Dragon")
deepDiveAnimation(JUVENILE_BRONZE_DRAGON, "Dragon", "Juvenile Bronze Dragon")
deepDiveAnimation(MATURE_BRONZE_DRAGON, "Dragon", "Mature Bronze Dragon")
deepDiveAnimation(MUD_WYVERN, "Dragon", "Mud Wyvern")
deepDiveAnimation(POISON_DRAKE, "Dragon", "Poison Drake")
deepDiveAnimation(PYGMY_WYVERN, "Dragon", "Pygmy Wyvern")
deepDiveAnimation(VIRIDIAN_DRAKE, "Dragon", "Viridian Drake")
deepDiveAnimation(YOUNG_BRASS_DRAGON, "Dragon", "Young Brass Dragon")
deepDiveAnimation(YOUNG_RED_DRAGON, "Dragon", "Young Red Dragon")

// Holy Animations
deepDiveAnimation(BLESSED_GLADIATOR, "Holy", "Blessed Gladiator")
deepDiveAnimation(BOLD_MAN_AT_ARMS, "Holy", "Bold Man At Arms")
deepDiveAnimation(DETERMINED_SOLDIER, "Holy", "Determined Soldier")
deepDiveAnimation(DEVOUT_ACOLYTE, "Holy", "Devout Acolyte")
deepDiveAnimation(DIVINE_PLANETAR, "Holy", "Divine Planetar")
deepDiveAnimation(FAVORED_CLERIC, "Holy", "Favored Cleric")
deepDiveAnimation(FLOATING_CHERUB, "Holy", "Floating Cherub")
deepDiveAnimation(GENTLE_SHEPARD, "Holy", "Gentle Shepard")
deepDiveAnimation(HOLY_CRUSADER, "Holy", "Holy Crusader")
deepDiveAnimation(JOVIAL_FRIAR, "Holy", "Jovial Friar")
deepDiveAnimation(RESOLUTE_ANGEL, "Holy", "Resolute Angel")
deepDiveAnimation(RIGHTEOUS_DEVA, "Holy", "Righteous Deva")
deepDiveAnimation(SWORD_ARCHON, "Holy", "Sword Archon")
deepDiveAnimation(VETERAN_SWORDSMAN, "Holy", "Veteran Swordsman")
deepDiveAnimation(ZEALOUS_PRIEST, "Holy", "Zealous Priest")

// Humanoid Animations
deepDiveAnimation(BESTIAL_LIZARDFOLK, "Humanoid", "Bestial Lizardfolk")
deepDiveAnimation(GOBLIN_ARCHER, "Humanoid", "Goblin Archer")
deepDiveAnimation(GOBLIN_FANATIC, "Humanoid", "Goblin Fanatic")
deepDiveAnimation(GOBLIN_FIGHTER, "Humanoid", "Goblin Fighter")
deepDiveAnimation(GOBLIN_OCCULTIST, "Humanoid", "Goblin Occultist")
deepDiveAnimation(GOBLIN_WOLF_RIDER, "Humanoid", "Goblin Wolf Rider")
deepDiveAnimation(HALFLING_ASSASSIN, "Humanoid", "Halfling Assassin")
deepDiveAnimation(HALFLING_BARD, "Humanoid", "Halfling Bard")
deepDiveAnimation(HALFLING_RANGER, "Humanoid", "Halfling Ranger")
deepDiveAnimation(HALFLING_ROGUE, "Humanoid", "Halfling Rogue")
deepDiveAnimation(HALFLING_SLINGER, "Humanoid", "Halfling Slinger")
deepDiveAnimation(LIZARDFOLK_ARCHER, "Humanoid", "Lizardfolk Archer")
deepDiveAnimation(LIZARDFOLK_GLADIATOR, "Humanoid", "Lizardfolk Gladiator")
deepDiveAnimation(LIZARDFOLK_SCOUT, "Humanoid", "Lizardfolk Scout")
deepDiveAnimation(LIZARDFOLK_SPEARMAN, "Humanoid", "Lizardfolk Spearman")

// Humanoid II Animations
deepDiveAnimation(ADVENTUROUS_ADOLESCENT, "Humanoid II", "Adventurous Adolescent")
deepDiveAnimation(BOISTEROUS_YOUTH, "Humanoid II", "Boisterous Youth")
deepDiveAnimation(ELF_BLADEDANCER, "Humanoid II", "Elf Bladedancer")
deepDiveAnimation(ELF_ENCHANTER, "Humanoid II", "Elf Enchanter")
deepDiveAnimation(ELF_LORD, "Humanoid II", "Elf Lord")
deepDiveAnimation(ELF_SHARPSHOOTER, "Humanoid II", "Elf Sharpshooter")
deepDiveAnimation(ELF_WAYFARER, "Humanoid II", "Elf Wayfarer")
deepDiveAnimation(JOYFUL_KID, "Humanoid II", "Joyful Kid")
deepDiveAnimation(MERFOLK_AQUAMANCER, "Humanoid II", "Merfolk Aquamancer")
deepDiveAnimation(MERFOLK_IMPALER, "Humanoid II", "Merfolk Impaler")
deepDiveAnimation(MERFOLK_JAVELINEER, "Humanoid II", "Merfolk Javelineer")
deepDiveAnimation(MERFOLK_MYSTIC, "Humanoid II", "Merfolk Mystic")
deepDiveAnimation(MERFOLK_SCOUT, "Humanoid II", "Merfolk Scout")
deepDiveAnimation(OVERWORKED_VILLAGER, "Humanoid II", "Overworked Villager")
deepDiveAnimation(PLAYFUL_CHILD, "Humanoid II", "Playful Child")

// Magical Animations
deepDiveAnimation(ADEPT_NECROMANCER, "Magical", "Adept Necromancer")
deepDiveAnimation(CORRUPTED_TREANT, "Magical", "Corrupted Treant")
deepDiveAnimation(DEFT_SORCERESS, "Magical", "Deft Sorceress")
deepDiveAnimation(EARTH_ELEMENTAL, "Magical", "Earth Elemental")
deepDiveAnimation(EXPERT_DRUID, "Magical", "Expert Druid")
deepDiveAnimation(FIRE_ELEMENTAL, "Magical", "Fire Elemental")
deepDiveAnimation(FLUTTERING_PIXIE, "Magical", "Fluttering Pixie")
deepDiveAnimation(GLOWING_WISP, "Magical", "Glowing Wisp")
deepDiveAnimation(GRIZZLED_TREANT, "Magical", "Grizzled Treant")
deepDiveAnimation(ICE_GOLEM, "Magical", "Ice Golem")
deepDiveAnimation(IRON_GOLEM, "Magical", "Iron Golem")
deepDiveAnimation(MAGICAL_FAIRY, "Magical", "Magical Fairy")
deepDiveAnimation(NOVICE_PYROMANCER, "Magical", "Novice Pyromancer")
deepDiveAnimation(VILE_WITCH, "Magical", "Vile Witch")
deepDiveAnimation(WATER_ELEMENTAL, "Magical", "Water Elemental")

// Monster Animations
deepDiveAnimation(BLINDED_GRIMLOCK, "Monster", "Blinded Grimlock")
deepDiveAnimation(BLOODSHOT_EYE, "Monster", "Bloodshot Eye")
deepDiveAnimation(CRIMSON_SLAAD, "Monster", "Crimson Slaad")
deepDiveAnimation(CRUSHING_CYCLOPS, "Monster", "Crushing Cyclops")
deepDiveAnimation(DEATH_SLIME, "Monster", "Death Slime")
deepDiveAnimation(FUNGAL_MYCONID, "Monster", "Fungal Myconid")
deepDiveAnimation(HUMONGOUS_ETTIN, "Monster", "Humongous Ettin")
deepDiveAnimation(MURKY_SLAAD, "Monster", "Murky Slaad")
deepDiveAnimation(OCHRE_JELLY, "Monster", "Ochre Jelly")
deepDiveAnimation(OCULAR_WATCHER, "Monster", "Ocular Watcher")
deepDiveAnimation(BRAWNY_OGRE, "Monster", "Brawny Ogre")
deepDiveAnimation(RED_CAP, "Monster", "Red Cap")
deepDiveAnimation(SHRIEKER_MUSHROOM, "Monster", "Shrieker Mushroom")
deepDiveAnimation(STONE_TROLL, "Monster", "Stone Troll")
deepDiveAnimation(SWAMP_TROLL, "Monster", "Swamp Troll")

// Undead Animations
deepDiveAnimation(BOUND_CADAVER, "Undead", "Bound Cadaver")
deepDiveAnimation(BRITTLE_ARCHER, "Undead", "Brittle Archer")
deepDiveAnimation(CARCASS_FEEDER, "Undead", "Carcass Feeder")
deepDiveAnimation(DECREPIT_BONES, "Undead", "Decrepit Bones")
deepDiveAnimation(DISMEMBERED_CRAWLER, "Undead", "Dismembered Crawler")
deepDiveAnimation(GHASTLY_EYE, "Undead", "Ghastly Eye")
deepDiveAnimation(GIANT_ROYAL_SCARAB, "Undead", "Giant Royal Scarab")
deepDiveAnimation(GRAVE_REVENANT, "Undead", "Grave Revenant")
deepDiveAnimation(MUTILATED_STUMBLER, "Undead", "Mutilated Stumbler")
deepDiveAnimation(ROYAL_SCARAB, "Undead", "Royal Scarab")
deepDiveAnimation(SAND_GHOUL, "Undead", "Sand Ghoul")
deepDiveAnimation(SKITTERING_HAND, "Undead", "Skittering Hand")
deepDiveAnimation(TOXIC_HOUND, "Undead", "Toxic Hound")
deepDiveAnimation(UNRAVELING_CRAWLER, "Undead", "Unraveling Crawler")
deepDiveAnimation(VAMPIRE_BAT, "Undead", "Vampire Bat")

// Vermin Animations
deepDiveAnimation(ACID_ANT, "Vermin", "Acid Ant")
deepDiveAnimation(BLOATED_BEDBUG, "Vermin", "Bloated Bedbug")
deepDiveAnimation(DUNG_BEETLE, "Vermin", "Dung Beetle")
deepDiveAnimation(ENGORGED_TICK, "Vermin", "Engorged Tick")
deepDiveAnimation(FAMISHED_TICK, "Vermin", "Famished Tick")
deepDiveAnimation(FORAGING_MAGGOT, "Vermin", "Foraging Maggot")
deepDiveAnimation(INFECTED_MOUSE, "Vermin", "Infected Mouse")
deepDiveAnimation(LAVA_ANT, "Vermin", "Lava Ant")
deepDiveAnimation(MAWING_BEAVER, "Vermin", "Mawing Beaver")
deepDiveAnimation(TUNNELING_MOLE, "Vermin", "Tunneling Mole")
deepDiveAnimation(PLAGUE_BAT, "Vermin", "Plague Bat")
deepDiveAnimation(RHINO_BEETLE, "Vermin", "Rhino Beetle")
deepDiveAnimation(SOLDIER_ANT, "Vermin", "Soldier Ant")
deepDiveAnimation(SWOOPING_BAT, "Vermin", "Swooping Bat")
deepDiveAnimation(TAINTED_COCKROACH, "Vermin", "Tainted Cockroach")

fun deepDiveAnimation(id: String, grouping: String, name: String) {
    val texture = TextureMap(
        id,
        "deepdivegamingsprites/Basic " + grouping + " Animations/" + name + "/" + name.replace(
            " ",
            ""
        ) + ".png",
        16, 16
    ).register()

    animationRenderable(id, texture) {
        frameDuration = 0.5f
    }.register()
}
