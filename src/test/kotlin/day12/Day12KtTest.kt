package day12

import org.junit.Test

class Day12KtTest {

    @Test
    fun example() {
        val initialGeneration = "#..#.#..##......###...###".toCharArray()
                .mapIndexed { index, c -> if (c == '#') index.toLong() else -1 }
                .filter { it != -1L }

        val ruleSet = RuleSet(listOf(
                ReproductionRule("...##"),
                ReproductionRule("..#.."),
                ReproductionRule(".#..."),
                ReproductionRule(".#.#."),
                ReproductionRule(".#.##"),
                ReproductionRule(".##.."),
                ReproductionRule(".####"),
                ReproductionRule("#.#.#"),
                ReproductionRule("#.###"),
                ReproductionRule("##.#."),
                ReproductionRule("##.##"),
                ReproductionRule("###.."),
                ReproductionRule("###.#"),
                ReproductionRule("####.")
        ))

        var currentGeneration = initialGeneration
        for (i in 0 until 20) {
            currentGeneration = evolve(currentGeneration, ruleSet)
        }

        println("Sum of pot numbers: ${currentGeneration.sum()}")
    }

    @Test
    fun solution() {
        val initialGeneration = "#.#.#...#..##..###.##.#...#.##.#....#..#.#....##.#.##...###.#...#######.....##.###.####.#....#.#..##".toCharArray()
                .mapIndexed { index, c -> if (c == '#') index.toLong() else -1 }
                .filter { it != -1L }

        val ruleSet = RuleSet(listOf(
                ReproductionRule("#...#"),
                ReproductionRule("##..#"),
                ReproductionRule(".#.##"),
                ReproductionRule("###.#"),
                ReproductionRule(".#.#."),
                ReproductionRule("#.##."),
                ReproductionRule("..#.#"),
                ReproductionRule(".#..."),
                ReproductionRule(".##.."),
                ReproductionRule(".#..#"),
                ReproductionRule("...##"),
                ReproductionRule("#.#.#"),
                ReproductionRule("####."),
                ReproductionRule("###.."),
                ReproductionRule("##...")
        ))

        var currentGeneration = initialGeneration
        for (i in 0 until 20) {
            currentGeneration = evolve(currentGeneration, ruleSet)
        }
        println("Sum of pot numbers after 20 iterations: ${currentGeneration.sum()}")

        var repeatingPoint: Long? = null
        currentGeneration = initialGeneration
        for(i in 0 until 50e9.toLong()) {
            val newGeneration = evolve(currentGeneration, ruleSet)
            if(newGeneration.map { it - 1 } == currentGeneration) {
                repeatingPoint = i
                println("Reached repeating point at $i")
                currentGeneration = newGeneration
                break
            }
            currentGeneration = newGeneration
        }
        currentGeneration = currentGeneration.map { it + (50e9.toLong() - repeatingPoint!! -1) }
        println("Sum of pot numbers after 50e9 iterations: ${currentGeneration.sum()}")
    }
}