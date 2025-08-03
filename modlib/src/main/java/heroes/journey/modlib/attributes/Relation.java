package heroes.journey.modlib.attributes;

public enum Relation {
    MIN(RelationType.ONE, Integer.MIN_VALUE),
    MAX(RelationType.ONE, Integer.MAX_VALUE),
    MULTIPLIER(RelationType.ONE, 1),
    MULTIPLICAND(RelationType.ONE, 0),
    CAP(RelationType.ONE, Integer.MAX_VALUE),
    REGEN(RelationType.ONE, 0),
    BASE(RelationType.ONE, 0),
    RESOURCE(RelationType.ONE, 0),
    PARENT(RelationType.ONE, 0),
    CHILD(RelationType.MANY, 0);

    private final RelationType type;
    private final int defaultValue;

    Relation(RelationType type, int defaultValue) {
        this.type = type;
        this.defaultValue = defaultValue;
    }

    public boolean isMany() {
        return type == RelationType.MANY;
    }

    public boolean isOne() {
        return type == RelationType.ONE;
    }

    public int getDefaultValue() {
        return defaultValue;
    }

    public enum RelationType {
        ONE, MANY
    }
}
