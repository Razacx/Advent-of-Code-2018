package day16

enum class Operation(val execute: (Register, a: Int, b: Int, c: Int) -> Unit) {

    ADDR({ register, a, b, c -> register[c] = register[a] + register[b] }),
    ADDI({ register, a, b, c -> register[c] = register[a] + b }),

    MULR({ register, a, b, c -> register[c] = register[a] * register[b] }),
    MULI({ register, a, b, c -> register[c] = register[a] * b }),

    BANR({ register, a, b, c -> register[c] = register[a] and register[b] }),
    BANI({ register, a, b, c -> register[c] = register[a] and b }),

    BORR({ register, a, b, c -> register[c] = register[a] or register[b] }),
    BORI({ register, a, b, c -> register[c] = register[a] or b }),

    SETR({ register, a, _, c -> register[c] = register[a] }),
    SETI({ register, a, _, c -> register[c] = a }),

    GTIR({ register, a, b, c -> register[c] = if (a > register[b]) 1 else 0 }),
    GTRI({ register, a, b, c -> register[c] = if (register[a] > b) 1 else 0 }),
    GTRR({ register, a, b, c -> register[c] = if (register[a] > register[b]) 1 else 0 }),

    EQIR({ register, a, b, c -> register[c] = if (a == register[b]) 1 else 0 }),
    EQRI({ register, a, b, c -> register[c] = if (register[a] == b) 1 else 0 }),
    EQRR({ register, a, b, c -> register[c] = if (register[a] == register[b]) 1 else 0 })

}

fun execute(operation: Operation, register: Register, a: Int, b: Int, c: Int): Register {
    val updatedRegister = Register(register)
    operation.execute(updatedRegister, a, b, c)
    return updatedRegister
}