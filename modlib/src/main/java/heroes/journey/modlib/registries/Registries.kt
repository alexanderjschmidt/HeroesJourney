package heroes.journey.modlib.registries

import heroes.journey.modlib.actions.IAction
import heroes.journey.modlib.art.IRenderable
import heroes.journey.modlib.art.ITextureMap
import heroes.journey.modlib.attributes.IGroup
import heroes.journey.modlib.attributes.IStat
import heroes.journey.modlib.items.IItem
import heroes.journey.modlib.items.IItemSubType
import heroes.journey.modlib.misc.IBuff
import heroes.journey.modlib.misc.IChallenge
import heroes.journey.modlib.misc.IChallengeType
import heroes.journey.modlib.misc.IApproach
import heroes.journey.modlib.misc.IFeat
import heroes.journey.modlib.misc.IQuest
import heroes.journey.modlib.worldgen.*

object Registries {
    lateinit var QuestManager: Registry<IQuest>
    lateinit var StatManager: Registry<IStat>
    lateinit var BuffManager: Registry<IBuff>
    lateinit var ChallengeManager: Registry<IChallenge>
    lateinit var ChallengeTypeManager: Registry<IChallengeType>
    lateinit var ApproachManager: Registry<IApproach>
    lateinit var FeatManager: Registry<IFeat>
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
