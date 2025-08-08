package heroes.journey.mods;

import heroes.journey.modlib.misc.Approach;
import heroes.journey.modlib.misc.Challenge;
import heroes.journey.modlib.misc.Quest;
import heroes.journey.entities.actions.Action;
import heroes.journey.modlib.attributes.Stat;
import heroes.journey.modlib.art.TextureMap;
import heroes.journey.modlib.config.TurnConfig;
import heroes.journey.modlib.misc.Buff;
import heroes.journey.modlib.registries.Registry;
import heroes.journey.modlib.worldgen.Biome;
import heroes.journey.modlib.worldgen.Terrain;
import heroes.journey.modlib.worldgen.TileBatch;
import heroes.journey.modlib.worldgen.TileLayout;
import heroes.journey.tilemap.FeatureType;
import heroes.journey.utils.art.Renderable;

public class Registries {

    public static Registry<Action> ActionManager = new Registry<>();
    public static Registry<Buff> BuffManager = heroes.journey.modlib.registries.Registries.INSTANCE.getBuffManager();

    public static Registry<Quest> QuestManager = heroes.journey.modlib.registries.Registries.INSTANCE.getQuestManager();
    public static Registry<Challenge> ChallengeManager = heroes.journey.modlib.registries.Registries.INSTANCE.getChallengeManager();
    public static Registry<Approach> ApproachManager = heroes.journey.modlib.registries.Registries.INSTANCE.getApproachManager();
    public static Registry<TurnConfig> TurnConfigManager = heroes.journey.modlib.registries.Registries.INSTANCE.getTurnConfigManager();

    public static Registry<Biome> BiomeManager = heroes.journey.modlib.registries.Registries.INSTANCE.getBiomeManager();
    public static Registry<FeatureType> FeatureTypeManager = new Registry<>();

    public static Registry<Terrain> TerrainManager = heroes.journey.modlib.registries.Registries.INSTANCE.getTerrainManager();
    public static Registry<TileLayout> TileLayoutManager = heroes.journey.modlib.registries.Registries.INSTANCE.getTileLayoutManager();
    public static Registry<TileBatch> TileBatchManager = heroes.journey.modlib.registries.Registries.INSTANCE.getTileBatchManager();

    public static Registry<Stat> StatManager = heroes.journey.modlib.registries.Registries.INSTANCE.getStatManager();

    public static Registry<Renderable> RenderableManager = new Registry<>();
    public static Registry<TextureMap> TextureManager = heroes.journey.modlib.registries.Registries.INSTANCE.getTextureManager();

}
