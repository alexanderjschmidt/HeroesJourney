package heroes.journey.initializers.base;

import heroes.journey.entities.EntityClass;
import heroes.journey.initializers.InitializerInterface;
import heroes.journey.utils.art.ResourceManager;

public class Classes implements InitializerInterface {

    public static final String HERO = "Hero";
    public static EntityClass hero, goblin;

    static {
        hero = new EntityClass(HERO, "You", ResourceManager.get(LoadTextures.Sprites)[1][1]);
        goblin = new EntityClass("Goblin", "Goblin", ResourceManager.get(LoadTextures.Sprites)[2][8]);
    }

}
