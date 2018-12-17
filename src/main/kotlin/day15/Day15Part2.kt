package day15

import parseWorld
import java.io.BufferedReader
import java.io.FileReader
import java.util.stream.Collectors

fun main(vararg args: String) {

    val lines = BufferedReader(FileReader("input/day15/map.txt"))
            .lines()
            .collect(Collectors.toList())

    var elfAp = 3
    while (true) {
        println("Checking for $elfAp AP")

        val world = parseWorld(lines, elfAp)
        val startingElvesCount = world.getElvesCount()
        val outcome = world.getOutcome()
        val endingElvesCount = world.getElvesCount()

        if(startingElvesCount == endingElvesCount) {
            println("\nAll eves survive if they have at least $elfAp AP\nOutcome is $outcome")
            return
        }

        elfAp++
    }

}

fun World.getElvesCount(): Int {
    return entities.count { it is Elf }
}