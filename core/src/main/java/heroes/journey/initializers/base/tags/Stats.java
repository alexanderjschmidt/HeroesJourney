package heroes.journey.initializers.base.tags;

import heroes.journey.entities.tagging.ConfluenceTag;
import heroes.journey.entities.tagging.Tag;
import heroes.journey.initializers.InitializerInterface;
import heroes.journey.registries.Tags;

public class Stats implements InitializerInterface {

    public static Tag BODY, MIND, MAGIC, CHARISMA;

    public static ConfluenceTag CHARM;

    static {
        BODY = new Tag("body", "Body", 1, 10, Groups.Body, Groups.BaseStats).register();
        MIND = new Tag("mind", "Mind", 1, 10, Groups.Mind, Groups.BaseStats).register();
        MAGIC = new Tag("magic", "Magic", 1, 10, Groups.Magic, Groups.BaseStats).register();
        CHARISMA = new Tag("charisma", "Charisma", 1, 10, Groups.Charisma, Groups.BaseStats).register();

        MIGHT = new ConfluenceTag("might", "MIGHT", 1, 10, BODY, BODY, BODY).register();
        CHARM = new ConfluenceTag("charm", "Charm", 1, 10, BODY, MIND, CHARISMA).register();
        System.out.println(Tags.get());
    }

    @Override
    public void init() {
    }
}
