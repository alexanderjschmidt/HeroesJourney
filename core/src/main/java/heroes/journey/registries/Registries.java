package heroes.journey.registries;

import heroes.journey.entities.Buff;
import heroes.journey.entities.Challenge;
import heroes.journey.entities.Quest;
import heroes.journey.entities.actions.Action;
import heroes.journey.entities.items.Item;
import heroes.journey.entities.items.ItemSubType;
import heroes.journey.tilemap.Biome;
import heroes.journey.tilemap.FeatureType;
import heroes.journey.tilemap.TileBatch;
import heroes.journey.tilemap.TileLayout;
import heroes.journey.tilemap.wavefunctiontiles.Terrain;

public class Registries {

    public static Registry<Item> ItemManager = new Registry<>();
    public static Registry<ItemSubType> ItemSubTypeManager = new Registry<>();
    
    public static Registry<Action> ActionManager = new Registry<>();
    public static Registry<Buff> BuffManager = new Registry<>();

    public static Registry<Quest> QuestManager = new Registry<>();
    public static Registry<Challenge> ChallengeManager = new Registry<>();

    public static Registry<Biome> BiomeManager = new Registry<>();
    public static Registry<FeatureType> FeatureTypeManager = new Registry<>();

    public static Registry<Terrain> TerrainManager = new Registry<>();
    public static Registry<TileLayout> TileLayoutManager = new Registry<>();
    public static Registry<TileBatch> TileBatchManager = new Registry<>();

}
