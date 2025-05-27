package heroes.journey.registries;

import heroes.journey.entities.Buff;
import heroes.journey.entities.actions.Action;
import heroes.journey.entities.items.Item;
import heroes.journey.entities.quests.Quest;
import heroes.journey.tilemap.wavefunctiontiles.Terrain;

public class Registries {

    public static Registry<Item> ItemManager = new Registry<>();
    public static Registry<Action> ActionManager = new Registry<>();
    public static Registry<Buff> BuffManager = new Registry<>();
    public static Registry<Terrain> TerrainManager = new Registry<>();
    public static Registry<Quest> QuestManager = new Registry<>();

}
