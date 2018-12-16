package day13

import day13.model.World
import day13.parse.parseCarts
import day13.parse.parseTracks
import java.io.BufferedReader
import java.io.FileReader
import java.util.stream.Collectors

fun main(vararg args: String) {

    val lines = BufferedReader(FileReader("input/day13/tracks.txt"))
            .lines()
            .collect(Collectors.toList())

    // Part 1 --------------------------------------------------------
    val world1 = World(parseTracks(lines), parseCarts(lines))
    while (world1.crashes.isEmpty()) {
        world1.moveCarts()
    }

    val firstCrash = world1.crashes[0]
    println("First crash occured at position ${firstCrash.coordinates.x},${firstCrash.coordinates.y}")

    // Part 2 --------------------------------------------------------
    val world2 = World(parseTracks(lines), parseCarts(lines))
    while (world2.carts.size > 1) {
        world2.moveCarts()
    }

    val lastCart = world2.carts[0]
    println("Last remaining cart is at position ${lastCart.position.x},${lastCart.position.y}")


}