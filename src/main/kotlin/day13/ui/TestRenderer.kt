package day13.ui

import day06.Coordinates
import day13.model.*
import day13.model.Direction.*
import day13.model.Track.*
import java.awt.Color
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.Image
import java.io.File
import javax.imageio.ImageIO
import javax.swing.JPanel

class TestRenderer(private val camera: Camera) : JPanel() {

    private val grass: Image
    private val tracks: Map<Track, Image>
    private val carts: Map<Direction, Image>

    init {
        grass = ImageIO.read(File("input/day13/img/grass.png"))
        tracks = mapOf(
                Horizontal to ImageIO.read(File("input/day13/img/track-horizontal.png")),
                Vertical to ImageIO.read(File("input/day13/img/track-vertical.png")),
                MainDiagonal to ImageIO.read(File("input/day13/img/track-diagonal-main.png")),
                AntiDiagonal to ImageIO.read(File("input/day13/img/track-diagonal-anti.png")),
                Intersection to ImageIO.read(File("input/day13/img/track-intersection-alt-2.png"))
        )
        val horizontalCart = File("input/day13/img/cart-horizontal.png")
        val verticalCart = File("input/day13/img/cart-vertical.png")
        carts = mapOf(
                North to ImageIO.read(verticalCart),
                South to ImageIO.read(verticalCart),
                East to ImageIO.read(horizontalCart),
                West to ImageIO.read(horizontalCart)
        )
    }

    val testCart: InterpolatedCart = InterpolatedCart(Cart(Coordinates(2, 1), East, JunctionState.Left))

    var lastTick = System.nanoTime() / 1e9
    val tickSpeed = 600e-3

    override fun paintComponent(g: Graphics?) {

        val g2d = g!! as Graphics2D

        g2d.color = Color.black
        g2d.fillRect(0, 0, width, height)

        val worldBounds = camera.getWorldBounds(width, height)
        for (x in worldBounds.minX until worldBounds.maxX) {
            for (y in worldBounds.minY until worldBounds.maxY) {
                val screenCoordinates = camera.worldToScreenCoordinates(DoubleCoordinates(x.toDouble(), y.toDouble()))
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
                DoubleCoordinates(5.0, 4.0) to Vertical,
                DoubleCoordinates(5.0, 5.0) to Intersection,
                DoubleCoordinates(4.0, 5.0) to Horizontal,
                DoubleCoordinates(6.0, 5.0) to Horizontal,
                DoubleCoordinates(5.0, 6.0) to Vertical
        )

        for(testTrack in testTracks) {
            val screenCoordinates = camera.worldToScreenCoordinates(testTrack.first)
            g2d.drawImage(tracks[testTrack.second], screenCoordinates.x, screenCoordinates.y, camera.zoom.toInt(), camera.zoom.toInt(), null)
        }


        if((System.nanoTime() / 1e9) - lastTick > tickSpeed) {
            testCart.position = Coordinates(testCart.position.x + testCart.direction.vector.x, testCart.position.y)
            if(testCart.position.x > 3) {
                testCart.direction = West
            }
            if(testCart.position.x < 3) {
                testCart.direction = East
            }
            lastTick = System.nanoTime() / 1e9
        }
        val tickT = (System.nanoTime() / 1e9 - lastTick) / tickSpeed
        val cartCoordinates = camera.worldToScreenCoordinates(testCart.getInterpolatedCoordinates(tickT))
        g2d.drawImage(carts[East], cartCoordinates.x, cartCoordinates.y, camera.zoom.toInt(), camera.zoom.toInt(), null)

    }

}