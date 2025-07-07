import heroes.journey.mods.gameMod

gameMod("Base Game", 0) {
    log("Loading action files...")
    includeScriptsFromDirectory("mods/basegame/actions")

    log("Loading other content files...")
    includeScripts(
        "mods/basegame/textures.kts",
        "mods/basegame/items.kts",
        "mods/basegame/quests.kts"
    )

    log("Loading challenge files...")
    includeScriptsFromDirectory("mods/basegame/challenges")

    log("Loading world gen files...")
    includeScripts(
        "mods/basegame/worldgen/terrains.kts",
        "mods/basegame/worldgen/biomes.kts",
        parallel = false
    )
}
