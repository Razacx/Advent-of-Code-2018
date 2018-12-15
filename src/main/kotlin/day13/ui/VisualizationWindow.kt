package day13.ui

import day06.Bounds
import day06.Coordinates
import day13.model.DoubleCoordinates
import day13.model.Track
import day13.model.Track.*
import java.awt.*
import java.awt.event.*
import java.io.File
import javax.imageio.ImageIO
import javax.swing.JFrame
import javax.swing.JPanel

fun main(vararg args: String) {
    VisualizationWindow()
}

data class Camera(var zoom: Double, var offsetX: Double, var offsetY: Double)

class VisualizationWindow : JFrame("Track visualisation"), MouseMotionListener, MouseListener, MouseWheelListener {

    private val camera: Camera
    private val staticRenderPanel: StaticRenderPanel

    init {
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        setSize(1000, 1000)

        camera = Camera(64.0, 0.0, 0.0)

        staticRenderPanel = StaticRenderPanel(camera)
        add(staticRenderPanel, BorderLayout.CENTER)

        this.addMouseMotionListener(this)
        this.addMouseListener(this)
        this.addMouseWheelListener(this)

        isVisible = true

        staticRenderPanel.repaint()
    }

    private var previousPoint: Point? = null

    override fun mouseDragged(e: MouseEvent?) {
        e!!
        if(previousPoint != null) {
            val previousPoint = previousPoint!!
            camera.offsetX -= (e.x - previousPoint.x) / camera.zoom
            camera.offsetY -= (e.y - previousPoint.y) / camera.zoom

            staticRenderPanel.repaint()
        }
        previousPoint = e.point
    }

    override fun mouseMoved(e: MouseEvent?) {
    }

    override fun mouseReleased(e: MouseEvent?) {
        previousPoint = null
    }

    override fun mouseEntered(e: MouseEvent?) {
    }

    override fun mouseClicked(e: MouseEvent?) {
    }

    override fun mouseExited(e: MouseEvent?) {
    }

    override fun mousePressed(e: MouseEvent?) {
    }

    override fun mouseWheelMoved(e: MouseWheelEvent?) {
        val delta = e!!.wheelRotation * 5
        val cursorWorldPos = screenToWorldCoordinates(camera, Coordinates(e.x, e.y))
        camera.zoom = clamp(10.0, 100.0, camera.zoom - delta)

        val newScreenCoordinates = worldToScreenCoordinates(camera, cursorWorldPos)
        camera.offsetX += (newScreenCoordinates.x - e.x) / camera.zoom
        camera.offsetY += (newScreenCoordinates.y - e.y) / camera.zoom

        staticRenderPanel.repaint()
    }

}

fun clamp(min: Double, max: Double, current: Double): Double {
    if(current < min) return min
    if(current > max) return max
    return current
}

class StaticRenderPanel(private val camera: Camera) : JPanel() {

    private val grass: Image
    private val tracks: Map<Track, Image>

    init {
        grass = ImageIO.read(File("input/day13/img/grass.png"))
        tracks = mapOf(
                Horizontal to ImageIO.read(File("input/day13/img/track-horizontal.png")),
                Vertical to ImageIO.read(File("input/day13/img/track-vertical.png")),
                MainDiagonal to ImageIO.read(File("input/day13/img/track-diagonal-main.png")),
                AntiDiagonal to ImageIO.read(File("input/day13/img/track-diagonal-anti.png"))
        )
    }

    override fun paintComponent(g: Graphics?) {

        val g2d = g!! as Graphics2D

        g2d.color = Color.black
        g2d.fillRect(0, 0, width, height)

        val worldBounds = getWorldBounds(camera, width, height)
        for (x in worldBounds.minX until worldBounds.maxX) {
            for (y in worldBounds.minY until worldBounds.maxY) {
                val screenCoordinates = worldToScreenCoordinates(camera, DoubleCoordinates(x.toDouble(), y.toDouble()))
                g2d.drawImage(grass, screenCoordinates.x, screenCoordinates.y, camera.zoom.toInt(), camera.zoom.toInt(), null)
            }
        }

        val testTracks = listOf(
                DoubleCoordinates(1.0, 1.0) to AntiDiagonal,
                DoubleCoordinates(2.0, 1.0) to Horizontal,
                DoubleCoordinates(3.0, 1.0) to Horizontal,
                DoubleCoordinates(4.0, 1.0) to Horizontal,
                DoubleCoordinates(5.0, 1.0) to MainDiagonal,
                DoubleCoordinates(5.0, 2.0) to Vertical,
                DoubleCoordinates(5.0, 3.0) to Vertical,
                DoubleCoordinates(5.0, 4.0) to Vertical
        )

        for(testTrack in testTracks) {
            val screenCoordinates = worldToScreenCoordinates(camera, testTrack.first)
            g2d.drawImage(tracks[testTrack.second], screenCoordinates.x, screenCoordinates.y, camera.zoom.toInt(), camera.zoom.toInt(), null)
        }

    }

}

private fun worldToScreenCoordinates(camera: Camera, coordinates: DoubleCoordinates): Coordinates {
    return Coordinates(
            ((coordinates.x - camera.offsetX) * camera.zoom).toInt(),
            ((coordinates.y - camera.offsetY) * camera.zoom).toInt()
    )
}

fun screenToWorldCoordinates(camera: Camera, coordinates: Coordinates): DoubleCoordinates {
    return DoubleCoordinates(
            (coordinates.x / camera.zoom) + camera.offsetX,
            (coordinates.y / camera.zoom) + camera.offsetY
    )
}

fun getWorldBounds(camera: Camera, screenWidth: Int, screenHeight: Int): Bounds {
    return Bounds(
            Math.floor(screenToWorldCoordinates(camera, Coordinates(0, 0)).x).toInt(),
            Math.floor(screenToWorldCoordinates(camera, Coordinates(0, 0)).y).toInt(),
            Math.ceil(screenToWorldCoordinates(camera, Coordinates(screenWidth, 0)).x).toInt(),
            Math.ceil(screenToWorldCoordinates(camera, Coordinates(0, screenHeight)).y).toInt()
    )
}