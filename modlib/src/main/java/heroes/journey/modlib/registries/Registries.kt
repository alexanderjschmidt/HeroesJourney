package heroes.journey.modlib.registries

import heroes.journey.modlib.actions.IAction
import heroes.journey.modlib.art.IRenderable
import heroes.journey.modlib.art.TextureMap
import heroes.journey.modlib.attributes.IStat
import heroes.journey.modlib.config.TurnConfig
import heroes.journey.modlib.misc.Buff
import heroes.journey.modlib.misc.IApproach
import heroes.journey.modlib.misc.IChallenge
import heroes.journey.modlib.misc.Quest
import heroes.journey.modlib.worldgen.*

object Registries {
    open val QuestManager: Registry<Quest> = Registry()
    lateinit var StatManager: Registry<IStat>
    open val BuffManager: Registry<Buff> = Registry()
    lateinit var ChallengeManager: Registry<IChallenge>
    lateinit var ApproachManager: Registry<IApproach>
    lateinit var ActionManager: Registry<IAction>
    open val TurnConfigManager: Registry<TurnConfig> = Registry()
    open val BiomeManager: Registry<Biome> = Registry()
    lateinit var FeatureTypeManager: Registry<IFeatureType>
    open val TerrainManager: Registry<Terrain> = Registry()
    open val TileLayoutManager: Registry<TileLayout> = Registry()
    open val TileBatchManager: Registry<TileBatch> = Registry()
    lateinit var RenderableManager: Registry<IRenderable>
    open val TextureManager: Registry<TextureMap> = Registry()
}
