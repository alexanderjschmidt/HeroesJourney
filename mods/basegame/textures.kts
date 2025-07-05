import heroes.journey.initializers.base.Ids
import heroes.journey.initializers.base.Ids.*
import heroes.journey.mods.gameMod
import heroes.journey.utils.art.ResourceManager
import heroes.journey.utils.art.StillRenderable
import heroes.journey.utils.art.TextureMap
import heroes.journey.utils.art.animationRenderable

val textures = gameMod("Base Game Textures", 0) {
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

    deepDiveAnimation(
        ADVENTURER,
        "Humanoid II", "Adventurous Adolescent"
    )
    deepDiveAnimation(
        BOUND_CADAVER,
        "Undead", "Bound Cadaver"
    )
    deepDiveAnimation(
        OVERWORKED_VILLAGER,
        "Humanoid II", "Overworked Villager"
    )
    deepDiveAnimation(
        OGRE,
        "Monster", "Brawny Ogre"
    )
    deepDiveAnimation(
        MOLE,
        "Vermin", "Tunneling Mole"
    )
}

fun deepDiveAnimation(id: String, grouping: String, name: String) {
    val texture = TextureMap(
        id,
        "deepdivegamingsprites/Basic " + grouping + " Animations/" + name + "/" + name.replace(
            " ",
            ""
        ) + ".png",
        16,
        16
    ).register()
    animationRenderable(id, texture) {
        frameDuration = 0.25f
    }.register()
}

textures
