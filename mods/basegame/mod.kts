import heroes.journey.modlib.gameMod

gameMod("Base Game", 0) {
    includeScriptsFromDirectory("Stats", "stats")

    includeScriptsFromDirectory("Actions", "actions")

    includeScripts(
        "Misc",
        "textures.kts",
        "quests.kts",
    )

    includeScriptsFromDirectory(
        "Challenges", "challenges"
    )

    includeScriptsFromDirectory(
        "Config", "config"
    )

    includeScripts(
        "World-Gen",
        "worldgen/terrains.kts",
        "worldgen/biomes.kts",
        "worldgen/tiles.kts",
        parallel = false
    )
}
