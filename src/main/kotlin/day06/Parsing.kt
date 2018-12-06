package day06

import java.io.BufferedReader
import java.io.FileReader
import java.util.stream.Collectors

fun loadIdentifiedCoordinates(): List<IdentifiedCoordinates> {
    val coordinates = BufferedReader(FileReader("input/day06/coordinates.txt"))
            .lines()
            .map { parseCoordinates(it) }
            .collect(Collectors.toList())

    val identifiedCoordinates = mutableListOf<IdentifiedCoordinates>()
    for (i in 0 until coordinates.size) {
        identifiedCoordinates.add(IdentifiedCoordinates(i, coordinates[i]))
    }

    return identifiedCoordinates
}

fun parseCoordinates(line: String): Coordinates {
    val result = """(\d*), (\d*)""".toRegex().matchEntire(line.trim())!!
    return Coordinates(
            result.groups[1]!!.value.toInt(),
            result.groups[2]!!.value.toInt()
    )
}


