package day17

import day06.Coordinates

fun parseVeins(lines: List<String>): List<Coordinates> {
    return lines.flatMap { parseCoordinateRange(it) }
}

fun parseCoordinateRange(line: String): List<Coordinates> {

    val tokens = line.trim().split(", ")

    val xRange = parseToIntRange(tokens.find { it.startsWith("x") }!!.split("=")[1])
    val yRange = parseToIntRange(tokens.find { it.startsWith("y") }!!.split("=")[1])

    return Coordinates.range(xRange, yRange)
}

fun parseToIntRange(data: String): IntRange {
    return if (data.contains('.')) {
        val tokens = data.split("..");
        IntRange(tokens[0].toInt(), tokens[1].toInt())
    } else {
        IntRange(data.toInt(), data.toInt())
    }
}

fun Coordinates.Companion.range(xRange: IntRange, yRange: IntRange): List<Coordinates> {
    val coordinates = mutableListOf<Coordinates>()
    for (x in xRange) {
        for (y in yRange) {
            coordinates.add(Coordinates(x, y))
        }
    }
    return coordinates
}