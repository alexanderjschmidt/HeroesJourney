package heroes.journey.entities.actions.results;

public class StringResult implements ActionResult {

    private final String result;

    public StringResult(String result) {
        this.result = result;
    }

    public String toString() {
        return result;
    }

}
