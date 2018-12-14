package day13.model

import day10.Vector2

enum class Direction(val vector: Vector2) {

    North(Vector2(0, -1)),
    East(Vector2(1, 0)),
    South(Vector2(0, 1)),
    West(Vector2(-1, 0));

    companion object {
        fun fromVector2(vector: Vector2): Direction {
            return Direction.values().find { it.vector == vector }!!
        }
    }

}

fun Vector2.rotateLeft(): Vector2 {
    return Vector2(y, -x)
}

fun Vector2.rotateRight(): Vector2 {
    return Vector2(-y, x)
}