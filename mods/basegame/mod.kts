import heroes.journey.mods.gameMod

gameMod("Base Game", 0) {
  includeScript("stats.kts")

  includeScriptsFromDirectory("Actions", "actions")

  includeScripts(
    "Misc",
    "textures.kts",
    "items.kts",
    "quests.kts"
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
