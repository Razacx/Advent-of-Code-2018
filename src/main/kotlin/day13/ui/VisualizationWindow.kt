package day13.ui

import day13.model.World
import day13.parse.parseCarts
import day13.parse.parseTracks
import java.awt.BorderLayout
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import java.io.BufferedReader
import java.io.FileReader
import java.util.stream.Collectors
import javax.swing.JFrame
import javax.swing.Timer

fun main(vararg args: String) {
    val lines = BufferedReader(FileReader("input/day13/test-tracks.txt"))
            .lines()
            .collect(Collectors.toList())

    val world = World(parseTracks(lines), parseCarts(lines))

    VisualizationWindow(world)
}

class VisualizationWindow(world: World) : JFrame("Track visualisation") {

    private val camera: Camera

    init {
        // JFrame setup
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        setSize(1000, 1000)

        // Create camera
        camera = Camera(64.0, 0.0, 0.0)

        // Create ticker
        val ticker = Ticker(200e-3) { world.moveCarts() }

        // Create rendering surfaces
        val renderer = Renderer(camera, world, ticker)
        add(renderer, BorderLayout.CENTER)

        // Add interaction functionality
        val cameraPanner = CameraPannerMouseMotionListener(camera)
        this.addMouseMotionListener(cameraPanner)
        this.addMouseListener(cameraPanner)

        val cameraZoom = CameraZoomMouseWheelListener(camera)
        this.addMouseWheelListener(cameraZoom)

        // Make window visible
        isVisible = true

        // Start render loop
        val renderLoop = Timer(1000 / 144) {
            ticker.tryTick()
            renderer.repaint()
        }
        renderLoop.start()
        addWindowListener(object : WindowAdapter() {
            override fun windowClosing(e: WindowEvent?) {
                super.windowClosing(e)
                renderLoop.stop()
            }
        })
    }

}

