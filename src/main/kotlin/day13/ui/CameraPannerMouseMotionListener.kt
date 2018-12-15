package day13.ui

import java.awt.Point
import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import java.awt.event.MouseMotionListener

class CameraPannerMouseMotionListener(private val camera: Camera): MouseMotionListener, MouseListener {

    private var previousPoint: Point? = null

    override fun mouseDragged(e: MouseEvent?) {
        e!!
        if(previousPoint != null) {
            val previousPoint = previousPoint!!
            camera.offsetX -= (e.x - previousPoint.x) / camera.zoom
            camera.offsetY -= (e.y - previousPoint.y) / camera.zoom
        }
        previousPoint = e.point
    }

    override fun mouseReleased(e: MouseEvent?) {
        previousPoint = null
    }

    override fun mouseMoved(e: MouseEvent?) {
    }

    override fun mouseEntered(e: MouseEvent?) {
    }

    override fun mouseClicked(e: MouseEvent?) {
    }

    override fun mouseExited(e: MouseEvent?) {
    }

    override fun mousePressed(e: MouseEvent?) {
    }

}