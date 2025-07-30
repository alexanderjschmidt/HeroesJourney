/**
 * Centralized collection of string IDs for all built-in content in Heroes Journey.
 *
 * Modders should use these IDs when referencing built-in content (e.g., stats, items, renderables, etc.),
 * and may define their own unique string IDs for custom content.
 *
 * Example usage:
 * ```kotlin
 * stat(id = Ids.STAT_BODY, ...)
 * item(id = Ids.ITEM_WOOD, ...)
 * ```
 */
package heroes.journey.modlib

object Ids {
    // TextureMaps
    const val OVERWORLD_TILESET: String = "overworld_tileset"

    // Renderables
    const val PLAYER_SPRITE: String = "player"
    const val CAPITAL_SPRITE: String = "capital"
    const val TOWN_SPRITE: String = "town"
    const val DUNGEON_SPRITE: String = "dungeon"

    const val LIGHT_FOG: String = "light_fog"
    const val DENSE_FOG: String = "dense_fog"

    const val RED: String = "red"
    const val GREEN: String = "green"
    const val BLUE: String = "blue"
    const val LIGHT_BLUE: String = "light_blue"
    const val PURPLE: String = "purple"
    const val YELLOW: String = "yellow"

    const val CURSOR: String = "cursor"
    const val MAP_POINTER: String = "map_pointer"

    // Action IDs - Core Actions
    const val OPEN_ACTION_MENU: String = "open_action_menu"
    const val EXIT_GAME: String = "exit_game"
    const val END_TURN: String = "end_turn"
    const val SAVE: String = "save"
    const val POPUP: String = "popup"
    const val REST: String = "rest"
    const val COMPLETE_QUEST: String = "complete_quest"
    const val COMPLETE_SPECIFIC_QUEST: String = "complete_specific_quest"

    // Action IDs - Training Actions
    const val WORKOUT_TRAINING: String = "workout_training"
    const val STUDY_TRAINING: String = "study_training"
    const val PRACTICE_TRAINING: String = "practice_training"
    const val SOCIALIZE_TRAINING: String = "socialize_training"
    const val TRAINING: String = "training"

    // Action IDs - Travel Actions
    const val TRAVEL_TO: String = "travel_to"
    const val TRAVEL: String = "travel"

    // Action IDs - Challenge Actions
    const val QUEST: String = "quest"
    const val QUEST_BOARD: String = "quest_board"
    const val CHOOSE_APPROACH: String = "choose_approach"
    const val FACE_CHALLENGE: String = "face_challenge"
    const val FACE_CHALLENGES: String = "face_challenges"

    // Action IDs - Delve Actions
    const val DELVE: String = "delve"
    const val DELVE_DUNGEON: String = "delve_dungeon"
    const val GAIN_FITNESS: String = "gain_fitness"
    const val FIND_LOST_RUIN: String = "find_lost_ruin"
    const val STUDY_TEXTS: String = "study_texts"
    const val NOBLE_DEED: String = "noble_deed"
    const val MASTER_ARCANE: String = "master_arcane"

    // Action IDs - Options Actions
    const val DEBUG: String = "debug"
    const val MUSIC: String = "music"

    // Terrain IDs
    const val TERRAIN_NULL: String = "null"

    const val TERRAIN_WATER: String = "water"
    const val TERRAIN_PLAINS: String = "plains"
    const val TERRAIN_HILLS: String = "hills"
    const val TERRAIN_SAND: String = "sand"
    const val TERRAIN_PATH: String = "path"
    const val TERRAIN_TREES: String = "trees"
    const val TERRAIN_PLAINS_TO_HILL: String = "plains_to_hill"
    const val TERRAIN_SAND_TO_HILL: String = "sand_to_hill"
    const val TERRAIN_PLAINS_TO_SAND: String = "plains_to_sand"
    const val TERRAIN_PLAINS_TO_WATER: String = "plains_to_water"
    const val TERRAIN_HILL_TO_WATER: String = "hill_to_water"
    const val TERRAIN_SAND_TO_WATER: String = "sand_to_water"

    // Biome IDs
    const val BIOME_KINGDOM: String = "kingdom"
    const val BIOME_DESERT_KINGDOM: String = "desert_kingdom"
    const val BIOME_MESA_KINGDOM: String = "mesa_kingdom"

    // FeatureType IDs
    const val KINGDOM: String = "kingdom"
    const val TOWN: String = "town"
    const val DUNGEON: String = "dungeon"
    const val MINE: String = "mine"

    // TileLayout IDs
    const val TILE_LAYOUT_WANG_CORNER: String = "wang_corner"
    const val TILE_LAYOUT_CLIFF_TRANSITION_TAPPER: String = "cliff_transition_tapper"
    const val TILE_LAYOUT_CLIFF_TRANSITION: String = "cliff_transition"
    const val TILE_LAYOUT_WANG_EDGE: String = "wang_edge"

    // TileBatch IDs
    const val TILE_BATCH_PLAINS_TO_HILL_CORNER: String = "plains_to_hill_corner"
    const val TILE_BATCH_CLIFF_TRANSITION_TAPPER_PLAINS_HILLS: String = "cliff_transition_tapper_plains_hills"
    const val TILE_BATCH_SAND_TO_HILL_CORNER: String = "sand_to_hill_corner"
    const val TILE_BATCH_CLIFF_TRANSITION_TAPPER_SAND_HILLS: String = "cliff_transition_tapper_sand_hills"
    const val TILE_BATCH_CLIFF_TRANSITION_SAND_HILL: String = "cliff_transition_sand_hill"
    const val TILE_BATCH_PLAINS_TO_SAND_CORNER: String = "plains_to_sand_corner"
    const val TILE_BATCH_TREES_CORNER: String = "trees_corner"
    const val TILE_BATCH_PATH_EDGE: String = "path_edge"

    // Animated Water Transitions
    const val TILE_BATCH_PLAINS_TO_WATER_CORNER_ANIMATED: String = "plains_to_water_corner_animated"
    const val TILE_BATCH_HILL_TO_WATER_CORNER_ANIMATED: String = "hill_to_water_corner_animated"
    const val TILE_BATCH_CLIFF_TRANSITION_PLAINS_HILL_WATER_ANIMATED: String =
        "cliff_transition_plains_hill_water_animated"
    const val TILE_BATCH_SAND_TO_WATER_CORNER_ANIMATED: String = "sand_to_water_corner_animated"
    const val TILE_BATCH_CLIFF_TRANSITION_SAND_HILL_WATER_ANIMATED: String =
        "cliff_transition_sand_hill_water_animated"
    const val TILE_BATCH_CLIFF_TRANSITION_SAND_PLAINS_WATER_ANIMATED: String =
        "cliff_transition_sand_plains_water_animated"

    // BaseTile IDs
    const val BASE_TILE_NULL: String = "base_tile_null"
    const val BASE_TILE_WATER: String = "base_tile_water"
    const val BASE_TILE_PLAINS: String = "base_tile_plains"
    const val BASE_TILE_HILLS: String = "base_tile_hills"
    const val BASE_TILE_SAND: String = "base_tile_sand"

    // Resource Stat IDs
    const val STAT_FAME: String = "fame"
    const val STAT_BODY: String = "body"
    const val STAT_MIND: String = "mind"
    const val STAT_MAGIC: String = "magic"
    const val STAT_CHARISMA: String = "charisma"
    const val STAT_CHALLENGE_POWER_TIER = "power_tier"
    const val STAT_STAMINA: String = "stamina"
    const val STAT_FOCUS: String = "focus"
    const val STAT_MANA: String = "mana"
    const val STAT_MOXIE: String = "moxie"
    const val STAT_CHALLENGE_HEALTH: String = "challenge_health"
    const val STAT_STAMINA_REGEN: String = "stamina_regen"
    const val STAT_FOCUS_REGEN: String = "focus_regen"
    const val STAT_MANA_REGEN: String = "mana_regen"
    const val STAT_MOXIE_REGEN: String = "moxie_regen"
    const val STAT_CHALLENGE_HEALTH_REGEN: String = "challenge_health_regen"
    const val STAT_STAMINA_MAX: String = "stamina_max"
    const val STAT_FOCUS_MAX: String = "focus_max"
    const val STAT_MANA_MAX: String = "mana_max"
    const val STAT_MOXIE_MAX: String = "moxie_max"
    const val STAT_CHALLENGE_HEALTH_MAX: String = "challenge_health_max"
    const val STAT_SPEED: String = "speed"
    const val STAT_VISION: String = "vision"
    const val STAT_CARRY_CAPACITY: String = "carry_capacity"

    // Race Stat IDs
    const val STAT_DEMON_RACE: String = "demon_race"
    const val STAT_DRAGON_RACE: String = "dragon_race"
    const val STAT_HOLY_RACE: String = "holy_race"
    const val STAT_HUMANOID_RACE: String = "humanoid_race"
    const val STAT_MAGICAL_RACE: String = "magical_race"
    const val STAT_MONSTER_RACE: String = "monster_race"
    const val STAT_UNDEAD_RACE: String = "undead_race"
    const val STAT_VERMIN_RACE: String = "vermin_race"

    // Descriptor Stat IDs
    const val STAT_PHYSICAL: String = "physical"
    const val STAT_INCORPOREAL: String = "incorporeal"
    const val STAT_FERAL: String = "feral"
    const val STAT_SENTIENT: String = "sentient"

    // Meta Group IDs
    const val GROUP_MAX: String = "max_group"
    const val GROUP_MIN: String = "min_group"
    const val GROUP_MULT: String = "mult_group"

    // Group IDs
    const val GROUP_FAME: String = "fame_group"
    const val GROUP_BODY: String = "body_group"
    const val GROUP_MIND: String = "mind_group"
    const val GROUP_MAGIC: String = "magic_group"
    const val GROUP_CHARISMA: String = "charisma_group"
    const val GROUP_RESOURCES: String = "resources_group"
    const val GROUP_REGEN: String = "regen_group"
    const val GROUP_RACE: String = "race_group"
    const val GROUP_DESCRIPTOR: String = "descriptor_group"
    const val GROUP_CHALLENGE: String = "challenge_group"

    // Approach IDs
    const val APPROACH_FIGHT: String = "approach_fight" // physical
    const val APPROACH_TRICK: String = "approach_trick" // Not Feral
    const val APPROACH_MAGIC_MISSILE: String = "approach_magic_missile" // physical or incorporeal
    const val APPROACH_NEGOTIATE: String = "approach_negotiate" // sentient

    const val APPROACH_INTIMIDATE: String = "approach_intimidate" // physical and sentient
    const val APPROACH_TRAP: String =
        "approach_trap" // physical and feral (maybe also a rune trap for incorporeal and feral)

}
