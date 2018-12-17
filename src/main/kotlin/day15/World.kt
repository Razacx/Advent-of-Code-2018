package day15

import day06.Coordinates
import day11.Grid2D
import day11.height
import day11.width

class World(val grid: Grid2D<TileType>) {

    val entities: MutableList<Entity> = mutableListOf()

    fun simulate() {
        for (entity in entities.sortedWith(Comparator { o1, o2 -> ReadingOrderCoordinatesComparator().compare(o1.position, o2.position) })) {
            if (entity.hp > 0) { //Could possibly die while going through the list
                entity.move()
                entity.attack()
            }
        }
    }

    // Hehe... refactor this if possible
    fun render(): List<String> {
        val lines = mutableListOf<String>()

        // Build map
        for (y in 0 until grid.height()) {
            var line = ""
            for (x in 0 until grid.width()) {

                val position = Coordinates(x, y)
                val entity = entities.find { it.position == position }

                if (entity != null) {
                    line += when (entity) {
                        is Goblin -> "\u001b[32mG"
                        is Elf -> "\u001b[31mE"
                        else -> throw IllegalArgumentException("Unknown Entity")
                    }
                } else {
                    line += when (grid[x][y]) {
                        TileType.Wall -> "\u001b[37m#"
                        TileType.Floor -> "\u001b[30m."
                    }
                }

            }
            lines.add(line)
        }

        // Add entity information on every line
        for (y in 0 until grid.height()) {
            val entitiesOnLine = entities
                    .filter { it.position.y == y }
                    .sortedBy { it.position.x }

            val entityInfo = entitiesOnLine.map {
                var info = when (it) {
                    is Goblin -> "\u001B[32mG"
                    is Elf -> "\u001B[31mE"
                    else -> throw IllegalArgumentException("Unknown Entity")
                }
                info += "(${it.hp})"
                info
            }.joinToString(", ")

            lines[y] += " $entityInfo"
        }

        return lines
    }

    fun getOutcome(): Int {
        var rounds = 0
        while(entities.map { it.javaClass }.distinct().count() > 1) {
            simulate()
            rounds++
        }
        val totalRemainingHp = entities.map { it.hp }.sum()
        return (rounds - 1) * totalRemainingHp
    }

}



