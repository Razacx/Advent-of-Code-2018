package day13.ui

import day06.Bounds
import day06.Coordinates
import day13.model.DoubleCoordinates

class Camera(var zoom: Double, var offsetX: Double, var offsetY: Double) {

    fun worldToScreenCoordinates(coordinates: DoubleCoordinates): Coordinates {
        return Coordinates(
                ((coordinates.x - this.offsetX) * this.zoom).toInt(),
                ((coordinates.y - this.offsetY) * this.zoom).toInt()
        )
    }

    fun screenToWorldCoordinates(coordinates: Coordinates): DoubleCoordinates {
        return DoubleCoordinates(
                (coordinates.x / this.zoom) + this.offsetX,
                (coordinates.y / this.zoom) + this.offsetY
        )
    }

    fun getWorldBounds(screenWidth: Int, screenHeight: Int): Bounds {
        return Bounds(
                Math.floor(screenToWorldCoordinates(Coordinates(0, 0)).x).toInt(),
                Math.floor(screenToWorldCoordinates(Coordinates(0, 0)).y).toInt(),
                Math.ceil(screenToWorldCoordinates(Coordinates(screenWidth, 0)).x).toInt(),
                Math.ceil(screenToWorldCoordinates(Coordinates(0, screenHeight)).y).toInt()
        )
    }

}
