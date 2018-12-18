package day06

data class IdentifiedCoordinates(val id: Int, val coordinates: Coordinates)

data class Coordinates(val x: Int, val y: Int) {

    companion object

    fun manhattanDistance(other: Coordinates): Int {
        return Math.abs(x - other.x) + Math.abs(y - other.y)
    }

}

fun findClosestIdentifiedCoordinatesId(identifiedCoordinates: List<IdentifiedCoordinates>, coordinates: Coordinates): Int {
    val closestIdentifiedCoordinates = identifiedCoordinates
            .minBy { it.coordinates.manhattanDistance(coordinates) }!!

    val coordinatesWithSameDistance = identifiedCoordinates.filter {
        it.coordinates.manhattanDistance(coordinates) == closestIdentifiedCoordinates.coordinates.manhattanDistance(coordinates)
    }.count()

    return if (coordinatesWithSameDistance > 1) {
        -1
    } else {
        closestIdentifiedCoordinates.id
    }
}

