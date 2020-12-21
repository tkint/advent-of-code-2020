fun main() {
    withInputData("day08.txt") { data ->
        val instructions = data.map { Day08.Instruction.fromString(it) }

        val accOnBreak = Day08.accumulateAndBreak(instructions)

        println(accOnBreak)

        val accOnFix = Day08.accumulateAndFix(instructions)

        println(accOnFix)
    }
}

object Day08 {
    const val ACC = "acc"
    const val JMP = "jmp"
    const val NOP = "nop"

    data class Instruction(
        val operation: String,
        val argument: Int,
    ) {
        companion object {
            fun fromString(str: String): Instruction = str.split(" ").let {
                Instruction(
                    operation = it[0],
                    argument = it[1].toInt(),
                )
            }
        }
    }

    fun accumulateAndBreak(instructions: List<Instruction>): Int {
        val indexes = mutableSetOf<Int>()
        var acc = 0
        var i = 0
        while (i < instructions.size) {
            if (indexes.contains(i)) break
            indexes.add(i)
            val instruction = instructions[i]
            when (instruction.operation) {
                ACC -> {
                    acc += instruction.argument
                    i++
                }
                JMP -> i += instruction.argument
                else -> i++
            }
        }
        return acc
    }

    fun accumulateAndFix(instructions: List<Instruction>): Int {
        var localInstructions = mutableListOf(*instructions.toTypedArray())

        val jumps = localInstructions.mapIndexed { index, instruction ->
            if (instruction.operation == JMP) instruction to index else null
        }.filterNotNull().toMutableList()

        val indexes = mutableSetOf<Int>()
        var acc = 0
        var i = 0
        // Tant qu'on est pas passé par tous les index
        // Et qu'on a pas tenté tous les jumps
        while (indexes.size < localInstructions.size && jumps.size > 0) {
            // Si on est déjà passé par la
            if (indexes.contains(i)) {
                // On change le prochain jmp en nop
                val (jump, index) = jumps.removeAt(0)
                localInstructions = mutableListOf(*instructions.toTypedArray())
                localInstructions[index] = jump.copy(operation = NOP)
                // Et on recommence
                indexes.clear()
                acc = 0
                i = 0
            } else {
                if (i > localInstructions.size - 1) {
                    println("BOOM")
                    break
                }
                indexes.add(i)
                val instruction = localInstructions[i]
                when (instruction.operation) {
                    ACC -> {
                        acc += instruction.argument
                        i++
                    }
                    JMP -> i += instruction.argument
                    else -> i++
                }
            }
        }
        return acc
    }
}
