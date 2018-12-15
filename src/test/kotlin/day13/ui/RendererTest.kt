package day13.ui

import day06.Coordinates
import day13.model.World
import day13.parse.parseTracks
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class RendererTest {

    lateinit var world: World
    lateinit var renderer: Renderer

    @Before
    fun setUp() {
        world = World(parseTracks(
                listOf(
                        """/->-\        """,
                        """|   |  /----\""",
                        """| /-+--+-\  |""",
                        """| | |  | v  |""",
                        """\-+-/  \-+--/""",
                        """  \------/   """
                )
        ), listOf())
        renderer = Renderer(Camera(1.0, 0.0, 0.0), world, Ticker(0.0) {})
    }

    @Test
    fun getTrackRotation_mainDiagonal() {
        val rotation = renderer.getTrackRotation(world.tracks, Coordinates(0, 4))
        assertEquals(Math.toRadians(180.0), rotation)
    }

    @Test
    fun getTrackRotation_antiDiagonal() {
        val rotation = renderer.getTrackRotation(world.tracks, Coordinates(12, 4))
        assertEquals(Math.toRadians(180.0), rotation)
    }

    @Test
    fun getTrackRotation_intersections() {
        world = World(parseTracks(
                listOf(
                        """+/""",
                        """||""",
                        """\+"""
                )
        ), listOf())
        renderer = Renderer(Camera(1.0, 0.0, 0.0), world, Ticker(0.0) {})

        val antiDiagonalRotation = renderer.getTrackRotation(world.tracks, Coordinates(1, 0))
        assertEquals(Math.toRadians(180.0), antiDiagonalRotation)

        val mainDiagonalRotation = renderer.getTrackRotation(world.tracks, Coordinates(0, 2))
        assertEquals(Math.toRadians(180.0), mainDiagonalRotation)
    }



}