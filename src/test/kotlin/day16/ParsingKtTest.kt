package day16

import org.junit.Test

import org.junit.Assert.*

class ParsingKtTest {

    @Test
    fun parseInstruction() {
        assertEquals(Instruction(10, 1, 2, 3), parseInstruction("10 1 2 3"))
        assertEquals(Instruction(6, 0, 0, 2), parseInstruction("6 0 0 2"))
    }

    @Test
    fun parseSample() {
        val data = listOf(
                "Before: [3, 1, 2, 1]",
                "2 3 2 2",
                "After:  [3, 1, 1, 1]"
        )
        val sample = parseSample(data)
        val expectedSample = Sample(
                Register(intArrayOf(3, 1, 2, 1)),
                Instruction(2, 3, 2, 2),
                Register(intArrayOf(3, 1, 1, 1))
        )

        assertEquals(sample, expectedSample)
    }

    @Test
    fun parseSamples() {
        val data = listOf(
                "Before: [3, 1, 2, 1]",
                "2 3 2 2",
                "After:  [3, 1, 1, 1]",
                "",
                "Before: [3, 1, 2, 1]",
                "2 3 2 2",
                "After:  [3, 1, 1, 1]"
        )
        val samples = parseSamples(data)
        val expectedSample = Sample(
                Register(intArrayOf(3, 1, 2, 1)),
                Instruction(2, 3, 2, 2),
                Register(intArrayOf(3, 1, 1, 1))
        )
        val expectedSamples = listOf(expectedSample, expectedSample)

        assertEquals(samples, expectedSamples)
    }
}