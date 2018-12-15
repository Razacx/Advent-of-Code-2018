package day13.ui

import day06.Coordinates
import day13.util.clamp
import java.awt.event.MouseWheelEvent
import java.awt.event.MouseWheelListener

class CameraZoomMouseWheelListener(private val camera: Camera): MouseWheelListener{

    override fun mouseWheelMoved(e: MouseWheelEvent?) {
        val delta = e!!.wheelRotation * 5
        val cursorWorldPos = camera.screenToWorldCoordinates(Coordinates(e.x, e.y))
        camera.zoom = clamp(10.0, 200.0, camera.zoom - delta)

        val newScreenCoordinates = camera.worldToScreenCoordinates(cursorWorldPos)
        camera.offsetX += (newScreenCoordinates.x - e.x) / camera.zoom
        camera.offsetY += (newScreenCoordinates.y - e.y) / camera.zoom
    }

}