package day13.model

import day13.model.Direction.*
import org.junit.Assert.assertEquals
import org.junit.Test

class DirectionKtTest {

    @Test
    fun rotateLeft() {
        assertEquals(West.vector, North.vector.rotateLeft())
        assertEquals(South.vector, West.vector.rotateLeft())
        assertEquals(East.vector, South.vector.rotateLeft())
        assertEquals(North.vector, East.vector.rotateLeft())
    }

    @Test
    fun rotateRight() {
        assertEquals(East.vector, North.vector.rotateRight())
        assertEquals(South.vector, East.vector.rotateRight())
        assertEquals(West.vector, South.vector.rotateRight())
        assertEquals(North.vector, West.vector.rotateRight())
    }

}