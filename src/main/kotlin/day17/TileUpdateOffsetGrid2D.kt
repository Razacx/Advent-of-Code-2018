package day17

import day06.Bounds
import day06.Coordinates
import day11.Grid2D
import day11.createGrid2D
import day13.model.Direction
import day13.model.plus
import day15.getNeighbours

class TileUpdateOffsetGrid2D<K>(x: Int, y: Int, grid: Grid2D<K>) : OffsetGrid2D<K>(x, y, grid) {

    private val _tilesToUpdate = mutableSetOf<Coordinates>()
    val tilesToUpdate get(): Set<Coordinates> = _tilesToUpdate

    override operator fun get(x: Int): OffsetGrid2DColumn<K> {
        return TileUpdateOffsetGrid2DColumn(this, x)
    }

    operator fun get(coordinates: Coordinates): K {
        return this[coordinates.x][coordinates.y]
    }

    internal fun addTilesToUpdate(coordinates: List<Coordinates>) {
        val bounds = bounds()
        _tilesToUpdate.addAll(coordinates.filter { bounds.contains(it) })
    }

    fun resetTilesToUpdate() {
        _tilesToUpdate.clear()
    }

    fun getAndResetTilesToUpdate(): Set<Coordinates> {
        val tilesToUpdateCopy = tilesToUpdate.toSet()
        resetTilesToUpdate()
        return tilesToUpdateCopy
    }

}

class TileUpdateOffsetGrid2DColumn<K>(offsetGrid2D: TileUpdateOffsetGrid2D<K>, x: Int) : OffsetGrid2DColumn<K>(offsetGrid2D, x) {

    override operator fun set(y: Int, value: K) {
        (offsetGrid2D as TileUpdateOffsetGrid2D).addTilesToUpdate(Coordinates(x, y).getNeigbours())
        return super.set(y, value)
    }

}

inline fun <reified K> createTileUpdateOffsetGrid2D(x: Int, y: Int, width: Int, height: Int, defaultValue: K): TileUpdateOffsetGrid2D<K> {
    val grid = createGrid2D(width, height, defaultValue)
    return TileUpdateOffsetGrid2D(x, y, grid)
}


inline fun <reified K> createTileUpdateOffsetGrid2D(bounds: Bounds, defaultValue: K): TileUpdateOffsetGrid2D<K> {
    return createTileUpdateOffsetGrid2D(bounds.minX, bounds.minY, bounds.width(), bounds.height(), defaultValue)
}


fun Coordinates.getNeigbours(): List<Coordinates> {
    return getDirectNeighbours() + getDiagonalNeighbours()
}

fun Coordinates.getDirectNeighbours(): List<Coordinates> {
    return getNeighbours(this)
}

fun Coordinates.getDiagonalNeighbours(): List<Coordinates> {
    return listOf(
            this + Direction.North.vector + Direction.West.vector,
            this + Direction.North.vector + Direction.East.vector,
            this + Direction.South.vector + Direction.West.vector,
            this + Direction.South.vector + Direction.East.vector
    )
}


fun Bounds.contains(coordinates: Coordinates): Boolean {
    return coordinates.x in minX..maxX && coordinates.y in minY..maxY
}