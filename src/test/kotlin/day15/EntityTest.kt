package day15

import day06.Coordinates
import day11.createGrid2D
import day15.TileType.Floor
import org.junit.Test

import org.junit.Assert.*
import parseWorld

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
    fun move_one_enemy() {
        val world = parseWorld(listOf(
                ".....",
                ".G.E.",
                "....."
        ))

        val goblin = world.entities[0]
        assertTrue(goblin is Goblin)

        goblin.move()
        assertEquals(Coordinates(2, 1), goblin.position)
    }

    @Test
    fun move_first_reading_order() {
        val world = parseWorld(listOf(
                "E....",
                ".G.E.",
                "....."
        ))

        val goblin = world.entities[1]
        assertTrue(goblin is Goblin)

        goblin.move()
        assertEquals(Coordinates(1, 0), goblin.position)
    }

    @Test
    fun move_first_closest() {
        val world = parseWorld(listOf(
                "E....",
                "..G.E",
                "....."
        ))

        val goblin = world.entities[1]
        assertTrue(goblin is Goblin)

        goblin.move()
        assertEquals(Coordinates(3, 1), goblin.position)
    }
}