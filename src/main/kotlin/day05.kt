fun main() {
    withInputData("day05.txt") { data ->
        // Vérif du decode
        listOf(
            "FBFBBFFRLR" to Day05.Seat(row = 44, column = 5),
            "BFFFBBFRRR" to Day05.Seat(row = 70, column = 7),
            "FFFBBBFRRR" to Day05.Seat(row = 14, column = 7),
            "BBFFBBFRLL" to Day05.Seat(row = 102, column = 4),
        ).forEach { (str, expected) ->
            val seat = Day05.Seat.decode(str)
            if (seat == expected) {
                println("Seat `$str` is valid (${seat.row}, ${seat.column}, ${seat.id})")
            } else {
                System.err.println("Seat `$str` is not valid (${seat.row}, ${seat.column}, ${seat.id})")
                System.err.println("Expected: ${expected.row}, ${expected.column}, ${expected.id}")
            }
        }

        val seats = data.map { Day05.Seat.decode(it) }
        val highestSeatId = seats.maxOf { it.id }

        println("Highest Seat Id: $highestSeatId")

        val minId = 16
        val maxId = highestSeatId - (highestSeatId % 8)

        var i = minId
        while (i < maxId) {
            if (seats.find { it.id == i } == null) {
                println("My Seat Id: $i")
            }
            i++
        }
    }
}

object Day05 {
    data class Seat(
        val row: Int,
        val column: Int,
    ) {
        val id: Int
            get() = row * 8 + column

        companion object {
            fun decode(str: String): Seat {
                val chars = str.toCharArray().toMutableList()
                var (minRow, maxRow) = 0 to 127
                var (minColumn, maxColumn) = 0 to 7
                while (chars.isNotEmpty()) {
                    val middleRow = (maxRow - minRow) / 2
                    val middleColumn = (maxColumn - minColumn) / 2
                    when (chars.removeAt(0)) {
                        'F' -> maxRow -= middleRow + 1
                        'B' -> minRow += middleRow + 1
                        'L' -> maxColumn -= middleColumn + 1
                        'R' -> minColumn += middleColumn + 1
                    }
                }
                return Seat(
                    row = if (str[6] == 'F') minRow else maxRow,
                    column = if (str[9] == 'L') minColumn else maxColumn,
                )
            }
        }
    }
}
