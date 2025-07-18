package heroes.journey.modlib

import heroes.journey.modlib.actions.IAction

object Registries {
    lateinit var QuestManager: Registry<IQuest>
    lateinit var StatManager: Registry<IStat>
    lateinit var BuffManager: Registry<IBuff>
    lateinit var ChallengeManager: Registry<IChallenge>
    lateinit var ActionManager: Registry<IAction>
    lateinit var ItemManager: Registry<IItem>
    lateinit var ItemSubTypeManager: Registry<IItemSubType>
    lateinit var BiomeManager: Registry<IBiome>
    lateinit var FeatureTypeManager: Registry<IFeatureType>
    lateinit var TerrainManager: Registry<ITerrain>
    lateinit var TileLayoutManager: Registry<ITileLayout>
    lateinit var TileBatchManager: Registry<ITileBatch>
    lateinit var GroupManager: Registry<IGroup>
    lateinit var RenderableManager: Registry<IRenderable>
    lateinit var TextureManager: Registry<ITextureMap>
}
