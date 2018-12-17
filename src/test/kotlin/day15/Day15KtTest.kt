package day15

import day06.Coordinates
import org.junit.Test
import parseWorld
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class Day15KtTest {

    @Test
    fun test_example() {
        val world = parseWorld(listOf(
                "#######",
                "#.G...#",
                "#...EG#",
                "#.#.#G#",
                "#..G#E#",
                "#.....#",
                "#######"
        ))

        println(world.render().reduce { acc, s -> acc + "\n" + s })
        for (i in 0 until 47) {
            world.simulate()
            println("\nRound ${i + 1}")
            println(world.render().reduce { acc, s -> acc + "\n" + s })
        }


        assertTrue(world.entities.none { it is Elf })

        assertEquals(world.entities[0].position, Coordinates(1, 1))
        assertEquals(world.entities[0].hp, 200)
        assertEquals(world.entities[1].position, Coordinates(2, 2))
        assertEquals(world.entities[1].hp, 131)
        assertEquals(world.entities[2].position, Coordinates(5, 3))
        assertEquals(world.entities[2].hp, 59)
        assertEquals(world.entities[3].position, Coordinates(5, 5))
        assertEquals(world.entities[3].hp, 200)
    }

    @Test
    fun test_outcomes() {
        val world = parseWorld(listOf(
            "#######",
            "#G..#E#",
            "#E#E.E#",
            "#G.##.#",
            "#...#E#",
            "#...E.#",
            "#######"
        ))
        val outcome = world.getOutcome()
        assertEquals(36334, outcome)
    }
}