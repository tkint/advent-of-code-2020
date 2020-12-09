import java.math.BigInteger

fun main() {
    withInputData("day03.txt") { data ->
        val matrix = data.map { it.toCharArray().toTypedArray() }.toTypedArray()

        val slopes = listOf(
            1 to 1,
            3 to 1,
            5 to 1,
            7 to 1,
            1 to 2,
        )

        val allTreesEncountered = slopes.map { slope ->
            val (nbTrees, nbSquares) = Day03.getEncountered(matrix, slope)

            val msg = """
                $slope
                Trees:   $nbTrees
                Squares: $nbSquares
            """.trimIndent()
            println("$msg\n")

            nbTrees.toBigInteger()
        }

        println(allTreesEncountered.reduce(BigInteger::times))
    }
}

object Day03 {
    fun getEncountered(matrix: Array<Array<Char>>, slope: Pair<Int, Int>): Pair<Int, Int> {
        var x = 0
        var y = 0
        val encountered = mutableListOf<Char>()
        while (y < matrix.size) {
            encountered.add(matrix[y][x])
            x += slope.first
            x %= matrix[0].size
            y += slope.second
        }

        val nbTrees = encountered.count { it == '#' }
        val nbSquares = encountered.size - nbTrees

        return nbTrees to nbSquares
    }
}
