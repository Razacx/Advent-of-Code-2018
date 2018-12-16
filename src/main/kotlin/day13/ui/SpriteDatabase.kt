package day13.ui

import day13.model.Direction
import day13.model.Track
import java.awt.Image
import java.io.File
import javax.imageio.ImageIO

class SpriteDatabase {

    val grass: Image
    val tracks: Map<Track, Image>
    val carts: Map<Direction, Image>
    val crash: Image

    init {
        grass = ImageIO.read(File("input/day13/img/grass.png"))

        tracks = mapOf(
                Track.Horizontal to ImageIO.read(File("input/day13/img/track-horizontal.png")),
                Track.Vertical to ImageIO.read(File("input/day13/img/track-vertical.png")),
                Track.MainDiagonal to ImageIO.read(File("input/day13/img/track-diagonal-main.png")),
                Track.AntiDiagonal to ImageIO.read(File("input/day13/img/track-diagonal-anti.png")),
                Track.Intersection to ImageIO.read(File("input/day13/img/track-intersection-alt-2.png"))
        )

        val horizontalCart = File("input/day13/img/cart-horizontal.png")
        val verticalCart = File("input/day13/img/cart-vertical.png")
        carts = mapOf(
                Direction.North to ImageIO.read(verticalCart),
                Direction.South to ImageIO.read(verticalCart),
                Direction.East to ImageIO.read(horizontalCart),
                Direction.West to ImageIO.read(horizontalCart)
        )

        crash = ImageIO.read(File("input/day13/img/crash.png"))
    }

}