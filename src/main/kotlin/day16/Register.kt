package day16

import java.util.*

class Register {

    private val values: IntArray

    constructor(size: Int = 4, defaultValue: Int = 0) {
        values = IntArray(size) { defaultValue }
    }

    constructor(register: Register) {
        values = register.values.copyOf()
    }

    constructor(intArray: IntArray) {
        values = intArray.copyOf()
    }

    operator fun get(index: Int): Int {
        return values[index]
    }

    operator fun set(index: Int, value: Int) {
        values[index] = value
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Register

        if (!values.contentEquals(other.values)) return false

        return true
    }

    override fun hashCode(): Int {
        return values.contentHashCode()
    }

    override fun toString(): String {
        return Arrays.toString(values)
    }


}