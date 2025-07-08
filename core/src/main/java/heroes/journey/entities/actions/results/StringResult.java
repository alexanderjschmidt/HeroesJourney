package heroes.journey.entities.actions.results;

public class StringResult implements ActionResult {

    private final String result;
    private final boolean endTurn;

    public StringResult(String result, boolean endTurn) {
        this.result = result;
        this.endTurn = endTurn;
    }

    public StringResult(String result) {
        this(result, true);
    }

    public String toString() {
        return result;
    }

    public boolean isEndTurn() {
        return endTurn;
    }
}
