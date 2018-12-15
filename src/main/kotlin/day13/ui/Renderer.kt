package day13.ui

import day06.Coordinates
import day11.Grid2D
import day11.height
import day11.width
import day13.model.DoubleCoordinates
import day13.model.Track
import day13.model.Track.*
import day13.model.World
import day13.util.toDoubleCoordinates
import java.awt.Color
import java.awt.Graphics
import java.awt.Graphics2D
import javax.swing.JPanel

class Renderer(
        private val camera: Camera,
        private val world: World,
        private val ticker: Ticker
) : JPanel() {

    private val sprites = SpriteDatabase()

    override fun paintComponent(g: Graphics?) {
        val g2d = g as Graphics2D

        // Clear screen
        g2d.color = Color.black
        g2d.fillRect(0, 0, width, height)

        // Render
        drawGrass(g2d)
        drawTracks(g2d)
        drawCarts(g2d)
        drawFps(g2d)
    }

    private fun drawGrass(g2d: Graphics2D) {
        val worldBounds = camera.getWorldBounds(width, height)
        for (x in worldBounds.minX until worldBounds.maxX) {
            for (y in worldBounds.minY until worldBounds.maxY) {
                val screenCoordinates = camera.worldToScreenCoordinates(DoubleCoordinates(x.toDouble(), y.toDouble()))
                g2d.drawImage(sprites.grass, screenCoordinates.x, screenCoordinates.y, camera.zoom.toInt(), camera.zoom.toInt(), null)
            }
        }
    }

    private fun drawTracks(g2d: Graphics2D) {
        val tracks = world.tracks

        for (x in 0 until tracks.width()) {
            for (y in 0 until tracks.height()) {
                val track = tracks[x][y]
                if (track != None) {

                    val worldCoordinates = Coordinates(x, y)
                    val screenCoordinates = camera.worldToScreenCoordinates(worldCoordinates.toDoubleCoordinates())

                    val oldTransform = g2d.transform

                    g2d.rotate(
                            getTrackRotation(tracks, worldCoordinates),
                            screenCoordinates.x + camera.zoom / 2.0,
                            screenCoordinates.y + camera.zoom / 2.0
                    )
                    g2d.drawImage(
                            sprites.tracks[track],
                            screenCoordinates.x,
                            screenCoordinates.y,
                            camera.zoom.toInt(),
                            camera.zoom.toInt(),
                            null
                    )

                    g2d.transform = oldTransform

                }
            }
        }
    }

    fun getTrackRotation(tracks: Grid2D<Track>, coordinates: Coordinates): Double {
        return when (tracks[coordinates.x][coordinates.y]) {
            MainDiagonal -> {
                val rightTrack = if (coordinates.x < tracks.width() - 1) tracks[coordinates.x + 1][coordinates.y] else null
                if (rightTrack != null && (rightTrack == Horizontal || rightTrack == Intersection)) {
                    Math.toRadians(180.0)
                } else {
                    Math.toRadians(0.0)
                }
            }
            AntiDiagonal -> {
                val leftTrack = if(coordinates.x > 0 ) tracks[coordinates.x - 1][coordinates.y] else null
                if (leftTrack != null && (leftTrack == Horizontal || leftTrack == Intersection)) {
                    Math.toRadians(180.0)
                } else {
                    Math.toRadians(0.0)
                }
            }
            else -> Math.toRadians(0.0)
        }
    }

    private fun drawCarts(g2d: Graphics2D) {
        for (cart in world.carts) {
            val coordinates = cart.getInterpolatedCoordinates(ticker.nextTickProgress)
            val screenCoordinates = camera.worldToScreenCoordinates(coordinates)
            g2d.drawImage(sprites.carts[cart.direction], screenCoordinates.x, screenCoordinates.y, camera.zoom.toInt(), camera.zoom.toInt(), null)
        }
    }

    private var lastFrameTime = System.nanoTime() / 1e9
    private var frameTimeMovingAverage = 60.0
    private var totalFrames = 0

    private fun drawFps(g2d: Graphics2D) {
        val currentTime = System.nanoTime() / 1e9

        // Calculate delta time
        val timeSinceLastFrame = currentTime - lastFrameTime
        lastFrameTime = currentTime

        // Exponential averaging of fps to make it change smoothly
        val smoothingFactor = 0.5
        frameTimeMovingAverage = (smoothingFactor * timeSinceLastFrame) + ((1 - smoothingFactor) * frameTimeMovingAverage)

        // Draw fps
        val fpsString = "%.2f".format(1 / frameTimeMovingAverage) + " FPS"
        val fpsStringWidth = g2d.fontMetrics.stringWidth(fpsString)

        g2d.color = Color.WHITE
        g2d.drawString(fpsString, width - fpsStringWidth - 10, 20)
    }

}

