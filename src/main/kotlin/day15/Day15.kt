package day15

import parseWorld
import java.io.BufferedReader
import java.io.FileReader
import java.util.stream.Collectors

fun main(vararg args: String) {

    val world = parseWorld(
            BufferedReader(FileReader("input/day15/map.txt"))
                    .lines()
                    .collect(Collectors.toList())
    )

    println(world.render().reduce { acc, s -> acc + "\n" + s })
    val outcome = world.getOutcome()
    println(world.render().reduce { acc, s -> acc + "\n" + s })

    println("Outcome: $outcome")

//    while (readLine() != "exit") {
//
//        world.simulate()
//        println()
//        println(world.render().reduce { acc, s -> acc + "\n" + s })
//
//    }

}

