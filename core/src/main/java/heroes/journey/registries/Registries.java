package heroes.journey.registries;

import heroes.journey.entities.Buff;
import heroes.journey.entities.Quest;
import heroes.journey.entities.actions.Action;
import heroes.journey.entities.items.Item;
import heroes.journey.modding.api.definitions.ItemSubType;
import heroes.journey.modding.api.registration.Registry;
import heroes.journey.tilemap.wavefunctiontiles.Terrain;
import heroes.journey.utils.worldgen.FeatureType;
import heroes.journey.utils.worldgen.MapGenerationEffect;

public class Registries extends heroes.journey.modding.api.registration.Registries {

    private static final Registries registries = new Registries();

    public static Registries get() {
        return registries;
    }

    // Migrated to Modlib
    public static Registry<ItemSubType> ItemSubTypeManager = registries.getItemSubTypeManager();

    // Definitions still in core
    public static Registry<Item> ItemManager = registries.itemManager;
    public static Registry<Action> ActionManager = registries.actionManager;
    public static Registry<Buff> BuffManager = registries.buffManager;
    public static Registry<Terrain> TerrainManager = registries.terrainManager;
    public static Registry<Quest> QuestManager = registries.questManager;
    public static Registry<MapGenerationEffect> MapGenerationManager = registries.mapGenerationManager;
    public static Registry<FeatureType> FeatureTypeManager = registries.featureTypeManager;

    public Registry<Item> itemManager;
    public Registry<Action> actionManager;
    public Registry<Buff> buffManager;
    public Registry<Terrain> terrainManager;
    public Registry<Quest> questManager;
    public Registry<MapGenerationEffect> mapGenerationManager;
    public Registry<FeatureType> featureTypeManager;

    public Registries() {
        itemManager = new Registry<Item>();
        actionManager = new Registry<Action>();
        buffManager = new Registry<Buff>();
        terrainManager = new Registry<Terrain>();
        questManager = new Registry<Quest>();
        mapGenerationManager = new Registry<MapGenerationEffect>();
        featureTypeManager = new Registry<FeatureType>();
    }

}
