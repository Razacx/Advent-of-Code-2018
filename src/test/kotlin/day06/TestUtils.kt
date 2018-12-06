package day06

import org.junit.Test
import kotlin.test.assertTrue

typealias _2DIntArray = Array<Array<Int>>

fun _2DIntArray.transpose(): _2DIntArray {
    val width = this.size
    val height = this[0].size
    val newArray = Array(height) { Array(width) { 0 } }

    for (x in 0 until width) {
        for (y in 0 until height) {
            newArray[y][x] = this[x][y]
        }
    }

    return newArray
}

class TestUtilsTest {

    @Test
    fun test_2DIntArray_transpose() {
        assertTrue(
                arrayOf(
                        arrayOf(1, 3, 5),
                        arrayOf(2, 4, 6)
                ) contentDeepEquals arrayOf(
                        arrayOf(1, 2),
                        arrayOf(3, 4),
                        arrayOf(5, 6)
                ).transpose()
        )
    }

}
