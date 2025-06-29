package heroes.journey.registries;

import heroes.journey.entities.Buff;
import heroes.journey.entities.Quest;
import heroes.journey.entities.actions.Action;
import heroes.journey.entities.items.Item;
import heroes.journey.entities.items.ItemSubType;
import heroes.journey.tilemap.Biome;
import heroes.journey.tilemap.FeatureType;
import heroes.journey.tilemap.wavefunctiontiles.Terrain;
import heroes.journey.utils.worldgen.MapGenerationEffect;

public class Registries {

    public static Registry<Item> ItemManager = new Registry<>();
    public static Registry<ItemSubType> ItemSubTypeManager = new Registry<>();
    public static Registry<Action> ActionManager = new Registry<>();
    public static Registry<Buff> BuffManager = new Registry<>();
    public static Registry<Terrain> TerrainManager = new Registry<>();
    public static Registry<FeatureType> FeatureTypeManager = new Registry<>();
    public static Registry<Quest> QuestManager = new Registry<>();
    public static Registry<MapGenerationEffect> MapGenerationManager = new Registry<>();
    public static Registry<Biome> BiomeManager = new Registry<>();

    public Registry<Item> itemManager;
    public Registry<ItemSubType> itemSubTypeManager;
    public Registry<Action> actionManager;
    public Registry<Buff> buffManager;
    public Registry<Terrain> terrainManager;
    public Registry<Quest> questManager;
    public Registry<MapGenerationEffect> mapGenerationManager;
    public Registry<Biome> biomeManager;

    public Registries() {
        itemManager = ItemManager;
        itemSubTypeManager = ItemSubTypeManager;
        actionManager = ActionManager;
        buffManager = BuffManager;
        terrainManager = TerrainManager;
        questManager = QuestManager;
        mapGenerationManager = MapGenerationManager;
        biomeManager = BiomeManager;
    }

}
