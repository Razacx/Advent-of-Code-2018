package day13

import day13.model.World
import day13.parse.parseCarts
import day13.parse.parseTracks
import day13.ui.VisualizationWindow
import java.io.BufferedReader
import java.io.FileReader
import java.util.stream.Collectors

fun main(vararg args: String) {

    val TRACKS = "input/day13/tracks.txt"
    val TEST_1 = "input/day13/test-tracks.txt"
    val TEST_2 = "input/day13/test-tracks-2.txt"

    val ACTIVE_TRACKS = TRACKS

    val lines = BufferedReader(FileReader(ACTIVE_TRACKS))
            .lines()
            .collect(Collectors.toList())

    val world = World(parseTracks(lines), parseCarts(lines))

    VisualizationWindow(world, 200e-3, 144)
}