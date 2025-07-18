package heroes.journey.modlib.attributes

import java.util.function.BiFunction

enum class Operation(private val operation: BiFunction<Int, Int, Int>) {
  ADD(BiFunction { a: Int, b: Int -> a + b }), SUBTRACT(
    BiFunction { a: Int, b: Int -> a - b }),
  MULTIPLY(
    BiFunction { a: Int, b: Int -> a * b }),
  DIVIDE(
    BiFunction { a: Int, b: Int -> if (b == 0) a else a / b });

  fun apply(a: Int, b: Int): Int {
    return operation.apply(a, b)
  }
}
