package day16

import org.junit.Test

import org.junit.Assert.*

class Day16KtTest {

    @Test
    fun getPossibleOperationCount() {
        val sample = parseSample(listOf(
                "Before: [3, 2, 1, 1]",
                "9 2 1 2",
                "After:  [3, 2, 2, 1]"
        ))

        assertEquals(3, day16.getPossibleOperationCount(sample))
    }

}