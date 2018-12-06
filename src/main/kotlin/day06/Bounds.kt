package day06

@Suppress("DataClassPrivateConstructor") //TODO How to work around this?
data class Bounds private constructor(val minX: Int, val minY: Int, val maxX: Int, val maxY: Int) {

    companion object {
        operator fun invoke(minX: Int, minY: Int, maxX: Int, maxY: Int): Bounds {
            if (minX > maxX || minY > maxY) {
                throw IllegalArgumentException("Invalid bounds")
            }
            return Bounds(minX, minY, maxX, maxY)
        }

        fun fromCoordinates(coordinates: List<Coordinates>): Bounds {
            val minX = coordinates.minBy(Coordinates::x)!!.x
            val maxX = coordinates.maxBy(Coordinates::x)!!.x
            val minY = coordinates.minBy(Coordinates::y)!!.y
            val maxY = coordinates.maxBy(Coordinates::y)!!.y

            return invoke(minX, minY, maxX, maxY)
        }
    }

    fun width(): Int {
        return maxX - minX + 1
    }

    fun height(): Int {
        return maxY - minY + 1
    }

}
