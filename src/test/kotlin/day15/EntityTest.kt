package day15

import day06.Coordinates
import day11.createGrid2D
import day15.TileType.Floor
import org.junit.Test

import org.junit.Assert.*

class EntityTest {

    @Test
    fun findTargetInRange_inRange() {
        val entityFactory = EntityFactory(World(createGrid2D(5, 5, Floor)))

        val goblin = entityFactory.createEntity<Goblin>(1, Coordinates(1, 0), 3, 200)

        val elf = entityFactory.createEntity<Elf>(0, Coordinates(0, 0), 3, 200)
        val target = elf.findTargetInRange()

        assertEquals(goblin, target)
    }

    @Test
    fun findTargetInRange_outOfRange() {
        val entityFactory = EntityFactory(World(createGrid2D(5, 5, Floor)))

        entityFactory.createEntity<Goblin>(1, Coordinates(2, 0), 3, 200)

        val elf = entityFactory.createEntity<Elf>(0, Coordinates(0, 0), 3, 200)
        val target = elf.findTargetInRange()

        assertEquals(null, target)
    }

    @Test
    fun findTargetInRange_diagonal_isOutOfRange() {
        val entityFactory = EntityFactory(World(createGrid2D(5, 5, Floor)))

        entityFactory.createEntity<Goblin>(1, Coordinates(1, 1), 3, 200)

        val elf = entityFactory.createEntity<Elf>(0, Coordinates(0, 0), 3, 200)
        val target = elf.findTargetInRange()

        assertEquals(null, target)
    }

    @Test
    fun findTargetInRange_multipleGoblinsInRange() {
        val entityFactory = EntityFactory(World(createGrid2D(5, 5, Floor)))

        entityFactory.createEntity<Goblin>(1, Coordinates(0, 1), 3, 200)
        val goblin = entityFactory.createEntity<Goblin>(2, Coordinates(1, 0), 3, 200)

        val elf = entityFactory.createEntity<Elf>(0, Coordinates(0, 0), 3, 200)
        val target = elf.findTargetInRange()

        assertEquals(goblin, target)
    }

    @Test
    fun move() {



    }

}