import heroes.journey.modlib.gameMod

gameMod("Base Game", 0) {
    includeScripts(
        "Stats",
        "stats.kts",
        "challenge_stats.kts",
    )

    includeScriptsFromDirectory("Actions", "actions")

    includeScripts(
        "Misc",
        "textures.kts",
        "quests.kts",
    )

    includeScriptsFromDirectory(
        "Challenges", "challenges"
    )

    includeScripts(
        "World-Gen",
        "worldgen/terrains.kts",
        "worldgen/biomes.kts",
        "worldgen/tiles.kts",
        parallel = false
    )
}
