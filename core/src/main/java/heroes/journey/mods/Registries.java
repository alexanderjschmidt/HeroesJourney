package heroes.journey.mods;

import heroes.journey.entities.Approach;
import heroes.journey.entities.Challenge;
import heroes.journey.entities.Quest;
import heroes.journey.entities.actions.Action;
import heroes.journey.entities.tagging.Stat;
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

    public static Registry<Quest> QuestManager = new Registry<>();
    public static Registry<Challenge> ChallengeManager = new Registry<>();
    public static Registry<Approach> ApproachManager = new Registry<>();
    public static Registry<TurnConfig> TurnConfigManager = heroes.journey.modlib.registries.Registries.INSTANCE.getTurnConfigManager();

    public static Registry<Biome> BiomeManager = heroes.journey.modlib.registries.Registries.INSTANCE.getBiomeManager();
    public static Registry<FeatureType> FeatureTypeManager = new Registry<>();

    public static Registry<Terrain> TerrainManager = heroes.journey.modlib.registries.Registries.INSTANCE.getTerrainManager();
    public static Registry<TileLayout> TileLayoutManager = heroes.journey.modlib.registries.Registries.INSTANCE.getTileLayoutManager();
    public static Registry<TileBatch> TileBatchManager = heroes.journey.modlib.registries.Registries.INSTANCE.getTileBatchManager();

    public static Registry<Stat> StatManager = new Registry<>();

    public static Registry<Renderable> RenderableManager = new Registry<>();
    public static Registry<TextureMap> TextureManager = heroes.journey.modlib.registries.Registries.INSTANCE.getTextureManager();

}
