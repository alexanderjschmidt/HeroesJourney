package heroes.journey.entities.actions;

public enum ShowAction {

    YES, GRAYED, NO;
    
    public ShowAction and(ShowAction other) {
        return this.ordinal() > other.ordinal() ? this : other;
    }

}
