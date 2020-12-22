import java.math.BigInteger

fun main() {
    withInputData("day09.txt") { data ->
        val numbers = data.map { it.toBigInteger() }

        val weakness = Day09.findWeakness(numbers)

        println(weakness)

        val encryptionWeakness = Day09.findEncryptionWeakness(numbers, weakness)

        println(encryptionWeakness)
    }
}

object Day09 {
    fun findWeakness(numbers: List<BigInteger>): BigInteger {
        val preambleSize = 25

        var weakness: BigInteger? = null
        var i = 0
        while (i < numbers.size && weakness == null) {
            val preamble = numbers.subList(i, i + preambleSize)
            val num = numbers[i + preambleSize]
            val pairs = findPairsForSum(preamble, num)
            if (pairs.isEmpty()) {
                weakness = num
            }
            i++
        }

        return weakness ?: throw Exception("No weakness found!")
    }

    fun findEncryptionWeakness(numbers: List<BigInteger>, weakness: BigInteger): BigInteger {
        val indexOfWeakness = numbers.indexOf(weakness)

        var listOfNumbers = listOf<BigInteger>()

        var start = 0
        while (start < numbers.size) {
            var end = numbers.size - 1
            while (end > start) {
                val subListOfNumbers = numbers.subList(start, end)
                if (end != indexOfWeakness && start != indexOfWeakness && subListOfNumbers.sumOf { it } == weakness) {
                    listOfNumbers = subListOfNumbers
                    break
                }
                end--
            }
            start++
        }

        return listOfNumbers.minOf { it } + listOfNumbers.maxOf { it }
    }

    private fun findPairsForSum(numbers: List<BigInteger>, sum: BigInteger): List<Pair<BigInteger, BigInteger>> {
        val pairs = mutableListOf<Pair<BigInteger, BigInteger>>()

        numbers.forEach { n1 ->
            numbers.forEach { n2 ->
                if (n1 != n2 && n1 + n2 == sum) {
                    pairs.add(n1 to n2)
                }
            }
        }

        return pairs
    }
}
