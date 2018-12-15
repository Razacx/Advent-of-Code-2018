package day13.util

import day06.Coordinates
import day13.model.DoubleCoordinates

fun clamp(min: Double, max: Double, current: Double): Double {
    if(current < min) return min
    if(current > max) return max
    return current
}

fun Coordinates.toDoubleCoordinates(): DoubleCoordinates {
    return DoubleCoordinates(x.toDouble(), y.toDouble())
}