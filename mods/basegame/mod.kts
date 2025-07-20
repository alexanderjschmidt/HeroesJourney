import heroes.journey.modlib.gameMod

gameMod("Base Game", 0) {
    includeScript("stats.kts")

    includeScriptsFromDirectory("Actions", "actions")

    includeScripts(
        "Misc",
        "textures.kts",
        "items.kts",
        "quests.kts",
        "challenges/approaches.kts",
        "challenges/challenge_types.kts"
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
