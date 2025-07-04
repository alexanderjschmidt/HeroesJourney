import heroes.journey.initializers.base.LoadTextures
import heroes.journey.initializers.base.LoadTextures.OVERWORLD_TILESET
import heroes.journey.mods.gameMod
import heroes.journey.utils.art.ResourceManager
import heroes.journey.utils.art.StillRenderable
import heroes.journey.utils.art.TextureMap
import heroes.journey.utils.art.animationRenderable

gameMod("Base Game Textures", 0) {
    val Sprites = TextureMap("sprites", "sprites.png", 16, 16).register()
    val OverworldTileset = TextureMap(OVERWORLD_TILESET, "Overworld_Tileset.png", 16, 16).register()
    val Adventurer = TextureMap(
        "adventurer",
        "deepdivegamingsprites/Basic Humanoid II Animations/Adventurous Adolescent/AdventurousAdolescent.png",
        16,
        16
    ).register()

    StillRenderable(LoadTextures.PLAYER_SPRITE, Sprites, 1, 1).register()
    StillRenderable(LoadTextures.CAPITAL_SPRITE, OverworldTileset, 9, 14).register()
    StillRenderable(LoadTextures.TOWN_SPRITE, OverworldTileset, 7, 12).register()
    StillRenderable(LoadTextures.DUNGEON_SPRITE, OverworldTileset, 17, 4).register()

    StillRenderable(LoadTextures.LIGHT_FOG, Sprites, 0, 1).register()
    StillRenderable(LoadTextures.DENSE_FOG, Sprites, 0, 0).register()

    StillRenderable(LoadTextures.RED, ResourceManager.UI, 2, 0).register()
    StillRenderable(LoadTextures.LIGHT_BLUE, ResourceManager.UI, 4, 1).register()
    StillRenderable(LoadTextures.PURPLE, ResourceManager.UI, 4, 0).register()
    StillRenderable(LoadTextures.YELLOW, ResourceManager.UI, 3, 1).register()

    animationRenderable(LoadTextures.CURSOR, ResourceManager.UI) {
        frameDuration = 0.5f
        frame(0, 0)
        frame(0, 0) // repeated intentionally
        frame(0, 1)
    }.register()

    animationRenderable(LoadTextures.MAP_POINTER, ResourceManager.UI) {
        frameDuration = 0.5f
        frame(3, 3)
        frame(3, 3) // repeated
        frame(3, 4)
    }.register()

    animationRenderable(LoadTextures.ADVENTURER, Adventurer) {
        frameDuration = 0.25f
    }.register()
}
