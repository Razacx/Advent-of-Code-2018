package day13.test

import java.awt.BasicStroke
import java.awt.Color
import java.awt.Graphics2D
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

//Used to generate a decent-looking curve because photoshop sucks
fun main(vararg args: String) {

    val image = BufferedImage(128, 128, BufferedImage.TYPE_4BYTE_ABGR)
    val g: Graphics2D = image.createGraphics()

    g.color = Color(68, 68, 68)
    g.stroke = BasicStroke(5f)

    g.drawOval(63 - 51, 63 - 51, 28, 28)

    val output = File("testimage.png")
    if(output.exists()) output.delete()
    ImageIO.write(image, "png", output)

}