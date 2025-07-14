package heroes.journey.entities.tagging;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public enum Stat {

    // BASE STATS (simple stats)
    BODY("body", "Body", 1, 10, Group.Body, Group.BaseStats), MIND("mind", "Mind", 1, 10, Group.Mind,
        Group.BaseStats), MAGIC("magic", "Magic", 1, 10, Group.Magic, Group.BaseStats), CHARISMA("charisma",
        "Charisma", 1, 10, Group.Charisma, Group.BaseStats),

    // RENOWN STATS (simple stats)
    VALOR("valor", "Valor", 0, 10, Group.Renown), INSIGHT("insight", "Insight", 0, 10, Group.Renown), ARCANUM(
        "arcanum", "Arcanum", 0, 10, Group.Renown), INFLUENCE("influence", "Influence", 0, 10, Group.Renown),

    // CONFLUENCE STATS (complex stats with parts)
    // BODY Physical problem solving
    MIGHT("might", "Might", 1, 10, attributes -> (attributes.getDirect(BODY) + attributes.getDirect(BODY) + attributes.getDirect(BODY)) / 3 + attributes.getDirect("might"), Group.Body),
    SKILL("skill", "Skill", 1, 10, attributes -> (attributes.getDirect(BODY) + attributes.getDirect(BODY) + attributes.getDirect(MIND)) / 3 + attributes.getDirect("skill"), Group.Body, Group.Mind),
    EMPOWERMENT("empowerment", "Empowerment", 1, 10, attributes -> (attributes.getDirect(BODY) + attributes.getDirect(BODY) + attributes.getDirect(MAGIC)) / 3 + attributes.getDirect("empowerment"), Group.Body, Group.Magic),
    CHIVALRY("chivalry", "Chivalry", 1, 10, attributes -> (attributes.getDirect(BODY) + attributes.getDirect(BODY) + attributes.getDirect(CHARISMA)) / 3 + attributes.getDirect("chivalry"), Group.Body, Group.Charisma),

    // MIND Mental problem solving
    TECHNIQUE("technique", "Technique", 1, 10, attributes -> (attributes.getDirect(MIND) + attributes.getDirect(MIND) + attributes.getDirect(BODY)) / 3 + attributes.getDirect("technique"), Group.Mind, Group.Body),
    LOGIC("logic", "Logic", 1, 10, attributes -> (attributes.getDirect(MIND) + attributes.getDirect(MIND) + attributes.getDirect(MIND)) / 3 + attributes.getDirect("logic"), Group.Mind),
    CONCENTRATION("concentration", "Concentration", 1, 10, attributes -> (attributes.getDirect(MIND) + attributes.getDirect(MIND) + attributes.getDirect(MAGIC)) / 3 + attributes.getDirect("concentration"), Group.Mind, Group.Magic),
    CUNNING("cunning", "Cunning", 1, 10, attributes -> (attributes.getDirect(MIND) + attributes.getDirect(MIND) + attributes.getDirect(CHARISMA)) / 3 + attributes.getDirect("cunning"), Group.Mind, Group.Charisma),

    // MAGIC magically problem solving
    ENCHANTING("enchanting", "Enchanting", 1, 10, attributes -> (attributes.getDirect(MAGIC) + attributes.getDirect(MAGIC) + attributes.getDirect(BODY)) / 3 + attributes.getDirect("enchanting"), Group.Magic, Group.Body),
    ILLUSION("illusion", "Illusion", 1, 10, attributes -> (attributes.getDirect(MAGIC) + attributes.getDirect(MAGIC) + attributes.getDirect(MIND)) / 3 + attributes.getDirect("illusion"), Group.Magic, Group.Mind),
    SORCERY("sorcery", "Sorcery", 1, 10, attributes -> (attributes.getDirect(MAGIC) + attributes.getDirect(MAGIC) + attributes.getDirect(MAGIC)) / 3 + attributes.getDirect("sorcery"), Group.Magic),
    BEWITCHING("bewitching", "Bewitching", 1, 10, attributes -> (attributes.getDirect(MAGIC) + attributes.getDirect(MAGIC) + attributes.getDirect(CHARISMA)) / 3 + attributes.getDirect("bewitching"), Group.Magic, Group.Charisma),

    // CHARISMA social problem solving
    BRAVADO("bravado", "Bravado", 1, 10, attributes -> (attributes.getDirect(CHARISMA) + attributes.getDirect(CHARISMA) + attributes.getDirect(BODY)) / 3 + attributes.getDirect("bravado"), Group.Charisma, Group.Body),
    PERSUASION("persuasion", "Persuasion", 1, 10, attributes -> (attributes.getDirect(CHARISMA) + attributes.getDirect(CHARISMA) + attributes.getDirect(MIND)) / 3 + attributes.getDirect("persuasion"), Group.Charisma, Group.Mind),
    MESMERISM("mesmerism", "Mesmerism", 1, 10, attributes -> (attributes.getDirect(CHARISMA) + attributes.getDirect(CHARISMA) + attributes.getDirect(MAGIC)) / 3 + attributes.getDirect("mesmerism"), Group.Charisma, Group.Magic),
    CHARM("charm", "Charm", 1, 10, attributes -> (attributes.getDirect(CHARISMA) + attributes.getDirect(CHARISMA) + attributes.getDirect(CHARISMA)) / 3 + attributes.getDirect("charm"), Group.Charisma),

    // CALCULATED STATS (derived from base stats)
    SPEED("speed", "Speed", 1, 10, attributes -> attributes.getDirect(BODY), Group.Body),
    VISION("vision", "Vision", 1, 10, attributes -> attributes.getDirect(BODY) + 3, Group.Body),
    CARRY_CAPACITY("carry_capacity", "Carry Capacity", 1, 100, attributes -> attributes.getDirect(BODY) * 10, Group.Body);

    private final String id;
    private final String nameInternal;
    private final int min, max;
    private final List<Group> groups;
    private final Function<Attributes,Integer> calc;

    // Constructor for simple stats (no parts)
    Stat(
        @NotNull String id,
        @Nullable String nameInternal,
        int min,
        int max,
        Function<Attributes,Integer> calc,
        Group... groups) {
        this.id = id;
        this.nameInternal = nameInternal;
        this.min = min;
        this.max = max;
        this.groups = new ArrayList<>(Arrays.stream(groups).toList());
        this.calc = calc;
    }

    Stat(@NotNull String id, @Nullable String nameInternal, int min, int max, Group... groups) {
        this.id = id;
        this.nameInternal = nameInternal;
        this.min = min;
        this.max = max;
        this.groups = new ArrayList<>(Arrays.stream(groups).toList());
        this.calc = attributes -> attributes.getDirect(this);
    }

    public String getId() {
        return id;
    }

    public String getNameInternal() {
        return nameInternal;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public boolean has(Group group) {
        return groups.contains(group);
    }

    @NotNull
    public Stat register() {
        return this;
    }

    // Static methods to replace Tags functionality
    public static Set<Stat> getByGroup(Group group) {
        return Arrays.stream(values()).filter(stat -> stat.has(group)).collect(Collectors.toSet());
    }

    public static Stat getById(String id) {
        return Arrays.stream(values()).filter(stat -> stat.getId().equals(id)).findFirst().orElse(null);
    }

    public int get(Attributes attributes) {
        return calc.apply(attributes);
    }
}
