package heroes.journey.entities.tagging;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Collectors;

public enum Stat {

    // BASE STATS (simple stats)
    BODY("body", "Body", 1, 10, Group.Body, Group.BaseStats),
    MIND("mind", "Mind", 1, 10, Group.Mind, Group.BaseStats),
    MAGIC("magic", "Magic", 1, 10, Group.Magic, Group.BaseStats),
    CHARISMA("charisma", "Charisma", 1, 10, Group.Charisma, Group.BaseStats),

    // RENOWN STATS (simple stats)
    VALOR("valor", "Valor", 0, 10, Group.Renown),
    INSIGHT("insight", "Insight", 0, 10, Group.Renown),
    ARCANUM("arcanum", "Arcanum", 0, 10, Group.Renown),
    INFLUENCE("influence", "Influence", 0, 10, Group.Renown),

    // CONFLUENCE STATS (complex stats with parts)
    // BODY Physical problem solving
    MIGHT("might", "Might", 1, 10, BODY, BODY, BODY),
    SKILL("skill", "Skill", 1, 10, BODY, BODY, MIND),
    EMPOWERMENT("empowerment", "Empowerment", 1, 10, BODY, BODY, MAGIC),
    CHIVALRY("chivalry", "Chivalry", 1, 10, BODY, BODY, CHARISMA),

    // MIND Mental problem solving
    TECHNIQUE("technique", "Technique", 1, 10, MIND, MIND, BODY),
    LOGIC("logic", "Logic", 1, 10, MIND, MIND, MIND),
    CONCENTRATION("concentration", "Concentration", 1, 10, MIND, MIND, MAGIC),
    CUNNING("cunning", "Cunning", 1, 10, MIND, MIND, CHARISMA),

    // MAGIC magically problem solving
    ENCHANTING("enchanting", "Enchanting", 1, 10, MAGIC, MAGIC, BODY),
    ILLUSION("illusion", "Illusion", 1, 10, MAGIC, MAGIC, MIND),
    SORCERY("sorcery", "Sorcery", 1, 10, MAGIC, MAGIC, MAGIC),
    BEWITCHING("bewitching", "Bewitching", 1, 10, MAGIC, MAGIC, CHARISMA),

    // CHARISMA social problem solving
    BRAVADO("bravado", "Bravado", 1, 10, CHARISMA, CHARISMA, BODY),
    PERSUASION("persuasion", "Persuasion", 1, 10, CHARISMA, CHARISMA, MIND),
    MESMERISM("mesmerism", "Mesmerism", 1, 10, CHARISMA, CHARISMA, MAGIC),
    CHARM("charm", "Charm", 1, 10, CHARISMA, CHARISMA, CHARISMA);

    private final String id;
    private final String nameInternal;
    private final int min, max;
    private final List<Group> groups;
    private final Map<Stat, Integer> parts;
    private final int totalParts;

    // Constructor for simple stats (no parts)
    Stat(@NotNull String id, @Nullable String nameInternal, int min, int max, Group... groups) {
        this.id = id;
        this.nameInternal = nameInternal;
        this.min = min;
        this.max = max;
        this.groups = new ArrayList<>(Arrays.stream(groups).toList());
        this.parts = new HashMap<>();
        this.totalParts = 0;
    }

    // Constructor for confluence stats (with parts)
    Stat(@NotNull String id, @Nullable String nameInternal, int min, int max, Stat... partsFromStats) {
        this.id = id;
        this.nameInternal = nameInternal;
        this.min = min;
        this.max = max;
        
        // Build groups from parts
        this.groups = new ArrayList<>();
        Set<Group> allGroups = Arrays.stream(partsFromStats)
            .flatMap(stat -> stat.getGroups().stream())
            .filter(group -> group != Group.BaseStats)
            .collect(Collectors.toSet());
        this.groups.addAll(allGroups);
        
        // Build parts map
        this.parts = new HashMap<>();
        int partsCount = 0; 
        for (Stat stat : partsFromStats) {
            if (!this.parts.containsKey(stat)) {
                this.parts.put(stat, 0);
            }
            this.parts.put(stat, this.parts.get(stat) + 1);
            partsCount++;
        }
        this.totalParts = partsCount;
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

    public Map<Stat, Integer> getParts() {
        return parts;
    }

    public int getTotalParts() {
        return totalParts;
    }

    public boolean isConfluenceStat() {
        return !parts.isEmpty();
    }

    @NotNull
    public Stat register() {
        return this;
    }

    // Static methods to replace Tags functionality
    public static Set<Stat> getByGroup(Group group) {
        return Arrays.stream(values())
                .filter(stat -> stat.has(group))
                .collect(Collectors.toSet());
    }

    public static Stat getById(String id) {
        return Arrays.stream(values())
                .filter(stat -> stat.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
} 