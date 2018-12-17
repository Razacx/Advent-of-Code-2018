package day15

import day06.Coordinates

abstract class Entity(val id: Int, private val world: World, private var _position: Coordinates, private val ap: Int, private var _hp: Int) {

    val hp get() = _hp
    val position get() = _position

    val onDeath = Event<Entity>()

    fun damage(damage: Int) {
        _hp -= damage
        if (hp <= 0) onDeath(this)
    }

    abstract fun getIsEnemyPredicate(): (Entity) -> Boolean

    fun findTargetInRange(): Entity? {
        // Keep all possible enemies
        val enemies = world.entities.filter(getIsEnemyPredicate())
        val inRangeEnemies = enemies.filter { isDirectNeighbour(position, it.position) }

        // Sort by lowest health and then reading order
        val sortedInRangeEnemies = inRangeEnemies
                .sortedWith(
                        Comparator { e1, e2 ->
                            val healthComparison = e1.hp.compareTo(e2.hp)
                            if(healthComparison != 0) {
                                healthComparison
                            } else {
                                ReadingOrderCoordinatesComparator().compare(e1.position, e2.position)
                            }
                        }
                )

        return if (!sortedInRangeEnemies.isEmpty()) {
            sortedInRangeEnemies.first()
        } else {
            null
        }
    }

    // TODO Write test
    fun move() {
        val enemies = world.entities.filter(getIsEnemyPredicate())
        val nextStep = findNextStep(worldToCollisionGrid(world.grid, world.entities), position) { coordinates ->
            enemies.any { enemy -> isDirectNeighbour(enemy.position, coordinates) }
        }
        if (nextStep != null) {
            _position = nextStep
        }
    }

    fun attack() {
        findTargetInRange()?.damage(ap)
    }

}

class Goblin(id: Int, world: World, position: Coordinates, _ap: Int, _hp: Int) : Entity(id, world, position, _ap, _hp) {

    override fun getIsEnemyPredicate(): (Entity) -> Boolean {
        return { it is Elf }
    }

}

class Elf(id: Int, world: World, position: Coordinates, _ap: Int, _hp: Int) : Entity(id, world, position, _ap, _hp) {

    override fun getIsEnemyPredicate(): (Entity) -> Boolean {
        return { it is Goblin }
    }

}

fun isDirectNeighbour(coordinates1: Coordinates, coordinates2: Coordinates): Boolean {
    return coordinates1.manhattanDistance(coordinates2) == 1
}

class ReadingOrderCoordinatesComparator : Comparator<Coordinates> {

    override fun compare(c1: Coordinates, c2: Coordinates): Int {
        return when {
            c1.y != c2.y -> c1.y - c2.y
            c1.x != c2.x -> c1.x - c2.x
            else -> 0
        }
    }

}