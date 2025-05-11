package heroes.journey.entities.tagging;

import java.util.function.BiFunction;

public enum Operation {
    ADD((a, b) -> a + b), SUBTRACT((a, b) -> a - b), MULTIPLY((a, b) -> a * b), DIVIDE(
        (a, b) -> b == 0 ? a : a / b);

    private final BiFunction<Integer, Integer, Integer> operation;

    Operation(BiFunction<Integer, Integer, Integer> op) {
        this.operation = op;
    }

    public int apply(int a, int b) {
        return operation.apply(a, b);
    }
}
