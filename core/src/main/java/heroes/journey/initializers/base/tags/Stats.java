package heroes.journey.initializers.base.tags;

import heroes.journey.entities.tagging.ConfluenceTag;
import heroes.journey.entities.tagging.Tag;
import heroes.journey.initializers.InitializerInterface;
import heroes.journey.registries.Tags;

public class Stats implements InitializerInterface {

    // BASE
    public static Tag BODY, MIND, MAGIC, CHARISMA;

    // DOUBLES
    // BODY, BODY, (BODY, MIND, MAGIC, CHARISMA)
    public static ConfluenceTag MIGHT, SKILL, EMPOWERMENT, BRAVADO;
    // MIND, MIND, (BODY, MIND, MAGIC, CHARISMA)
    public static ConfluenceTag TECHNIQUE, LOGIC, RUNE_CRAFT, CUNNING;
    // MAGIC, MAGIC, (BODY, MIND, MAGIC, CHARISMA)
    public static ConfluenceTag ENCHANTING, WIZARDRY, SORCERY, ILLUSION;
    // CHARISMA, CHARISMA, (BODY, MIND, MAGIC, CHARISMA)
    public static ConfluenceTag INTIMIDATION, PERSUASION, CHARM, SEDUCTION;

    static {
        // BASE
        BODY = new Tag("body", "Body", 1, 10, Groups.Body, Groups.BaseStats).register();
        MIND = new Tag("mind", "Mind", 1, 10, Groups.Mind, Groups.BaseStats).register();
        MAGIC = new Tag("magic", "Magic", 1, 10, Groups.Magic, Groups.BaseStats).register();
        CHARISMA = new Tag("charisma", "Charisma", 1, 10, Groups.Charisma, Groups.BaseStats).register();

        // BODY Physical problem solving
        MIGHT = new ConfluenceTag("might", "Might", 1, 10, BODY, BODY, BODY).register();
        SKILL = new ConfluenceTag("skill", "Skill", 1, 10, BODY, BODY, MIND).register();
        EMPOWERMENT = new ConfluenceTag("empowerment", "Empowerment", 1, 10, BODY, BODY, MAGIC).register();
        BRAVADO = new ConfluenceTag("bravado", "Bravado", 1, 10, BODY, BODY, CHARISMA).register();

        // MIND Mental problem solving
        TECHNIQUE = new ConfluenceTag("technique", "Technique", 1, 10, MIND, MIND, BODY).register();
        LOGIC = new ConfluenceTag("logic", "Logic", 1, 10, MIND, MIND, MIND).register();
        RUNE_CRAFT = new ConfluenceTag("rune_craft", "Rune Craft", 1, 10, MIND, MIND, MAGIC).register();
        CUNNING = new ConfluenceTag("cunning", "Cunning", 1, 10, MIND, MIND, CHARISMA).register();

        // MAGIC magically problem solving
        ENCHANTING = new ConfluenceTag("enchanting", "Enchanting", 1, 10, MAGIC, MAGIC, BODY).register();
        WIZARDRY = new ConfluenceTag("wizardry", "Wizardry", 1, 10, MAGIC, MAGIC, MIND).register();
        SORCERY = new ConfluenceTag("sorcery", "Sorcery", 1, 10, MAGIC, MAGIC, MAGIC).register();
        ILLUSION = new ConfluenceTag("illusion", "Illusion", 1, 10, MAGIC, MAGIC, CHARISMA).register();

        // CHARISMA social problem solving
        INTIMIDATION = new ConfluenceTag("intimidation", "Intimidation", 1, 10, CHARISMA, CHARISMA,
            BODY).register();
        PERSUASION = new ConfluenceTag("persuasion", "Persuasion", 1, 10, CHARISMA, CHARISMA,
            MIND).register();
        CHARM = new ConfluenceTag("charm", "Charm", 1, 10, CHARISMA, CHARISMA, MAGIC).register();
        SEDUCTION = new ConfluenceTag("seduction", "Seduction", 1, 10, CHARISMA, CHARISMA,
            CHARISMA).register();

        System.out.println(Tags.get());
    }

    @Override
    public void init() {
    }
}
