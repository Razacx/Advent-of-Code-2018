package day13.model

import day10.Vector2

enum class JunctionState(val direct: (Vector2) -> Vector2) {

    Left({it.rotateLeft()}),
    Straight({it}),
    Right({it.rotateRight()});

    // Wow this syntax sucks compared to java
    private var _nextState: JunctionState? = null
    val nextState: JunctionState
        get() = _nextState!!

    companion object {
        init {
            Left._nextState = Straight
            Straight._nextState = Right
            Right._nextState = Left
        }
    }

}