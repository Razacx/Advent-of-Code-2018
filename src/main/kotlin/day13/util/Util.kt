package day13.util

fun clamp(min: Double, max: Double, current: Double): Double {
    if(current < min) return min
    if(current > max) return max
    return current
}