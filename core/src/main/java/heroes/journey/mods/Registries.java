package heroes.journey.mods;

import heroes.journey.entities.Approach;
import heroes.journey.modlib.misc.Buff;
import heroes.journey.entities.Challenge;
import heroes.journey.entities.Quest;
import heroes.journey.entities.actions.Action;
import heroes.journey.entities.items.Item;
import heroes.journey.modlib.items.ItemSubType;
import heroes.journey.entities.tagging.Stat;
import heroes.journey.modlib.attributes.Group;
import heroes.journey.modlib.registries.Registry;
import heroes.journey.modlib.worldgen.Biome;
import heroes.journey.tilemap.FeatureType;
import heroes.journey.tilemap.TileBatch;
import heroes.journey.tilemap.TileLayout;
import heroes.journey.modlib.worldgen.Terrain;
import heroes.journey.utils.art.Renderable;
import heroes.journey.modlib.art.TextureMap;

public class Registries {

    public static Registry<Item> ItemManager = new Registry<>();
    public static Registry<ItemSubType> ItemSubTypeManager = heroes.journey.modlib.registries.Registries.INSTANCE.getItemSubTypeManager();

    public static Registry<Action> ActionManager = new Registry<>();
    public static Registry<Buff> BuffManager = heroes.journey.modlib.registries.Registries.INSTANCE.getBuffManager();

    public static Registry<Quest> QuestManager = new Registry<>();
    public static Registry<Challenge> ChallengeManager = new Registry<>();
    public static Registry<Approach> ApproachManager = new Registry<>();

    public static Registry<Biome> BiomeManager = heroes.journey.modlib.registries.Registries.INSTANCE.getBiomeManager();
    public static Registry<FeatureType> FeatureTypeManager = new Registry<>();

    public static Registry<Terrain> TerrainManager = heroes.journey.modlib.registries.Registries.INSTANCE.getTerrainManager();
    public static Registry<TileLayout> TileLayoutManager = new Registry<>();
    public static Registry<TileBatch> TileBatchManager = new Registry<>();

    public static Registry<Stat> StatManager = new Registry<>();
    public static Registry<Group> GroupManager = heroes.journey.modlib.registries.Registries.INSTANCE.getGroupManager();

    public static Registry<Renderable> RenderableManager = new Registry<>();
    public static Registry<TextureMap> TextureManager = heroes.journey.modlib.registries.Registries.INSTANCE.getTextureManager();

}
