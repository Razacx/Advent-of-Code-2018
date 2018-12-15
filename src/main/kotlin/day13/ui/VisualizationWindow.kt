package day13.ui

import java.awt.*
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import javax.swing.JFrame
import javax.swing.Timer

fun main(vararg args: String) {
    VisualizationWindow()
}

class VisualizationWindow : JFrame("Track visualisation") {

    private val camera: Camera
    private val testRenderer: TestRenderer

    init {
        // JFrame setup
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        setSize(1000, 1000)

        // Create camera
        camera = Camera(64.0, 0.0, 0.0)

        // Create rendering surfaces
        testRenderer = TestRenderer(camera)
        add(testRenderer, BorderLayout.CENTER)

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
            testRenderer.repaint()
        }
        renderLoop.start()
        addWindowListener(object: WindowAdapter() {
            override fun windowClosing(e: WindowEvent?) {
                super.windowClosing(e)
                renderLoop.stop()
            }
        })
    }

}

