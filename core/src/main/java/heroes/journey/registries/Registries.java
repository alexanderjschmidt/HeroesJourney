package heroes.journey.registries;

import heroes.journey.entities.Buff;
import heroes.journey.entities.Quest;
import heroes.journey.entities.actions.Action;
import heroes.journey.entities.items.Item;
import heroes.journey.entities.items.ItemSubType;
import heroes.journey.tilemap.wavefunctiontiles.Terrain;
import heroes.journey.utils.worldgen.MapGenerationEffect;

public class Registries {

    public static Registry<Item> ItemManager = new Registry<>();
    public static Registry<ItemSubType> ItemSubTypeManager = new Registry<>();
    public static Registry<Action> ActionManager = new Registry<>();
    public static Registry<Buff> BuffManager = new Registry<>();
    public static Registry<Terrain> TerrainManager = new Registry<>();
    public static Registry<Quest> QuestManager = new Registry<>();
    public static Registry<MapGenerationEffect> MapGenerationManager = new Registry<>();

    public Registry<Item> itemManager;
    public Registry<ItemSubType> itemSubTypeManager;

    public Registries() {
        itemManager = ItemManager;
        itemSubTypeManager = ItemSubTypeManager;
    }

}
