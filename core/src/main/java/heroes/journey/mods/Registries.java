package heroes.journey.mods;

import heroes.journey.entities.Buff;
import heroes.journey.entities.Challenge;
import heroes.journey.entities.ChallengeType;
import heroes.journey.entities.Approach;
import heroes.journey.entities.Quest;
import heroes.journey.entities.actions.Action;
import heroes.journey.entities.items.Item;
import heroes.journey.entities.items.ItemSubType;
import heroes.journey.entities.tagging.Group;
import heroes.journey.entities.tagging.Stat;
import heroes.journey.modlib.registries.Registry;
import heroes.journey.tilemap.Biome;
import heroes.journey.tilemap.FeatureType;
import heroes.journey.tilemap.TileBatch;
import heroes.journey.tilemap.TileLayout;
import heroes.journey.tilemap.wavefunctiontiles.Terrain;
import heroes.journey.utils.art.Renderable;
import heroes.journey.utils.art.TextureMap;

public class Registries {

    public static Registry<Item> ItemManager = new Registry<>();
    public static Registry<ItemSubType> ItemSubTypeManager = new Registry<>();

    public static Registry<Action> ActionManager = new Registry<>();
    public static Registry<Buff> BuffManager = new Registry<>();

    public static Registry<Quest> QuestManager = new Registry<>();
    public static Registry<Challenge> ChallengeManager = new Registry<>();
    public static Registry<ChallengeType> ChallengeTypeManager = new Registry<>();
    public static Registry<Approach> ApproachManager = new Registry<>();

    public static Registry<Biome> BiomeManager = new Registry<>();
    public static Registry<FeatureType> FeatureTypeManager = new Registry<>();

    public static Registry<Terrain> TerrainManager = new Registry<>();
    public static Registry<TileLayout> TileLayoutManager = new Registry<>();
    public static Registry<TileBatch> TileBatchManager = new Registry<>();

    public static Registry<Stat> StatManager = new Registry<>();
    public static Registry<Group> GroupManager = new Registry<>();

    public static Registry<Renderable> RenderableManager = new Registry<>();
    public static Registry<TextureMap> TextureManager = new Registry<>();

}
