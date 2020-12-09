fun main() {
    withInputData("day02.txt") { data ->
        val nbValid = data.count { line ->
            Day02.Item.fromLine(line).isValid()
        }
        println(nbValid)

        val nbValidBis = data.count { line ->
            Day02.Item.fromLine(line).isValidBis()
        }

        println(nbValidBis)
    }
}

object Day02 {
    data class Item(
        val numbers: List<Int>,
        val letter: String,
        val password: String,
    ) {
        companion object {
            fun fromLine(line: String): Item {
                val parts = line.split(": ")

                val ruleParts = parts[0].split(" ")
                val (numbers, letter) = ruleParts
                val str = parts[1]

                return Item(
                    numbers = numbers.split("-").map { it.toInt() },
                    letter = letter,
                    password = str,
                )
            }
        }

        fun isValid(): Boolean {
            val occurrences = password.count { it.toString() == letter }
            val (min, max) = numbers
            return occurrences in min..max
        }

        fun isValidBis(): Boolean {
            val occurrences = password.toCharArray().withIndex().count { char ->
                char.value.toString() == letter && numbers.contains(char.index + 1)
            }
            return occurrences == 1
        }
    }
}
