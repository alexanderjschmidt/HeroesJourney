package heroes.journey.modlib.utils

import kotlin.math.abs

class Position(@JvmField var x: Int, @JvmField var y: Int) {
    fun setX(x: Int): Position {
        this.x = x
        return this
    }

    fun setY(y: Int): Position {
        this.y = y
        return this
    }

    fun setPos(x: Int, y: Int): Position {
        this.x = x
        this.y = y
        return this
    }

    override fun toString(): String {
        return "($x, $y)"
    }

    fun distanceTo(other: Position): Int {
        val dx = this.x - other.x
        val dy = this.y - other.y
        // return (int) Math.sqrt(dx * dx + dy * dy); // Euclidean
        return (abs((this.x - other.x).toDouble()) + abs((this.y - other.y).toDouble())).toInt() // Manhattan
    }

    fun reset() {
        x = -1
        y = -1
    }
}
