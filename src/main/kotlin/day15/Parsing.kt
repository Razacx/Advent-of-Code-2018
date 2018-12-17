import day06.Coordinates
import day11.createGrid2D
import day15.Entity
import day15.EntityFactory
import day15.Goblin
import day15.TileType.Floor
import day15.TileType.Wall
import day15.World
import java.lang.IllegalArgumentException
import kotlin.String

fun parseWorld(lines: List<String>): World {
    val world = parseMap(lines)
    parseEntities(world, lines)
    return world
}

fun parseMap(lines: List<String>): World {
    val width = lines.maxBy { it.length }!!.length
    val height = lines.size

    val grid = createGrid2D(width, height, Wall)

    for (y in 0 until lines.size) {
        val chars = lines[y].toCharArray()
        for (x in 0 until chars.size) {
            grid[x][y] = when (chars[x]) {
                '.', 'G', 'E' -> Floor
                '#' -> Wall
                else -> throw IllegalArgumentException("Unable to parse character: ${chars[x]}")
            }
        }
    }

    return World(grid)
}

fun parseEntities(world: World, lines: List<String>) {
    val entities = mutableListOf<Entity>()
    val entityFactory = EntityFactory(world)

    for (y in 0 until lines.size) {
        val chars = lines[y].toCharArray()
        for (x in 0 until chars.size) {
            val position = Coordinates(x, y)
            when(chars[x]) {
                'G' -> entityFactory.createEntity<Goblin>(entities.size, position, 3, 200)
                'E' -> entityFactory.createEntity<Goblin>(entities.size, position, 3, 200)
            }
        }
    }
}