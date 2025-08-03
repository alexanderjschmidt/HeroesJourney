package heroes.journey.entities.tagging;

import heroes.journey.modlib.attributes.IAttributes;
import heroes.journey.modlib.attributes.IStat;
import heroes.journey.modlib.attributes.Relation;
import heroes.journey.modlib.registries.Registrable;
import kotlin.jvm.functions.Function1;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static heroes.journey.mods.Registries.StatManager;

public class Stat extends Registrable implements IStat {

    private final Map<Relation, IStat> relatedStats; // For ONE relations
    private final Map<Relation, List<IStat>> relatedStatsMany; // For MANY relations
    private final Function1<IAttributes, Integer> calc;
    private final Integer defaultValue;

    public Stat(
        @NotNull String id,
        Function1<IAttributes, Integer> calc,
        Integer defaultValue) {
        super(id);
        this.calc = calc;
        this.defaultValue = defaultValue;
        this.relatedStats = new HashMap<>();
        this.relatedStatsMany = new HashMap<>();
    }

    public Integer getDefaultValue() {
        return defaultValue;
    }

    @Override
    public IStat getRelation(Relation relation) {
        return relatedStats.get(relation);
    }

    @Override
    public Integer getRelation(IAttributes attributes, Relation relation) {
        assert attributes != null;

        if (relation.isOne()) {
            IStat relatedStat = relatedStats.get(relation);
            if (relatedStat != null) {
                return attributes.get(relatedStat);
            }
        } else if (relation.isMany()) {
            throw new RuntimeException("This relationship (" + relation + ") has many stats associated to it.");
        }
        
        // Return the default value for this relation type
        return relation.getDefaultValue();
    }

    @Nullable
    @Override
    public List<IStat> getRelatedStats(@NotNull Relation relation) {
        List<IStat> relatedStats = relatedStatsMany.get(relation);
        return relatedStats != null ? relatedStats : new ArrayList<>();
    }

    @Override
    public Function1<IAttributes, Integer> getFormula() {
        return calc;
    }

    @Override
    public Stat register() {
        return StatManager.register(this);
    }

    @Override
    public void addRelatedStat(Relation relation, IStat stat) {
        if (relation.isOne()) {
            relatedStats.put(relation, stat);
        } else if (relation.isMany()) {
            relatedStatsMany.computeIfAbsent(relation, k -> new ArrayList<>()).add(stat);
        }
    }
}


