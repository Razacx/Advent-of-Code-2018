package day13.model

import day06.Coordinates
import day10.Vector2
import day13.model.Direction.*
import java.lang.IllegalStateException

// Perhaps have move function on cart that calls Track move function with coordinates + direction
// -> Hard because of JunctionState exception... (context object?)
enum class Track(val char: Char, val move: (Cart) -> Unit) {

    None(' ', { throw IllegalStateException("Cart went of track: $it") }),

    Vertical('|', { it.position += it.direction.vector }),
    Horizontal('-', { it.position += it.direction.vector }),

    MainDiagonal('\\', {
        it.direction = Direction.fromVector2(when (it.direction) {
            East, West -> it.direction.vector.rotateRight()
            North, South -> it.direction.vector.rotateLeft()
        })
        it.position += it.direction.vector
    }),
    AntiDiagonal('/', {
        it.direction = Direction.fromVector2(when (it.direction) {
            North, South -> it.direction.vector.rotateRight()
            East, West -> it.direction.vector.rotateLeft()
        })
        it.position += it.direction.vector
    }),

    Intersection('+', {
        it.direction = Direction.fromVector2(it.junctionState.direct(it.direction.vector))
        it.junctionState = it.junctionState.nextState
        it.position += it.direction.vector
    });

    companion object {
        fun fromCharacter(char: Char): Track {
            return Track.values().find { it.char == char }!!
        }
    }

}

operator fun Coordinates.plus(vector: Vector2): Coordinates {
    return Coordinates(x + vector.x, y + vector.y)
}