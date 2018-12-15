package day13.ui

class Ticker(val frequencyInSeconds: Double, private val onTick: () -> (Unit)) {

    private var _lastTickTime = System.nanoTime() / 1e9
    val lastTickTime get() = _lastTickTime

    val nextTickProgress get() = (System.nanoTime() / 1e9 - _lastTickTime) / (frequencyInSeconds)

    fun tryTick() {
        val currentTime = System.nanoTime() / 1e9
        if(currentTime >= _lastTickTime + frequencyInSeconds) {
            onTick()
            _lastTickTime = currentTime
        }
    }

}