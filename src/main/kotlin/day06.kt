fun main() {
    withInputData("day06.txt") { data ->
        var i = 0

        val groups = mutableListOf<Day06.Group>()

        var responses = mutableListOf<String>()
        while (i < data.size) {
            responses.add(data[i])
            if (data[i].isBlank() || i == data.lastIndex) {
                groups.add(Day06.Group(responses.filter { it.isNotBlank() }))
                responses = mutableListOf()
            }
            i++
        }

        val sumOfYesAnyone = groups
            .map { it.yesForAnyone }
            .sumOf { it.size }

        println(sumOfYesAnyone)

        val sumOfYesEveryone = groups
            .map { it.yesForEveryone }
            .sumOf { it.size }

        println(sumOfYesEveryone)
    }
}

object Day06 {
    data class Group(
        val responses: List<String>,
    ) {
        val yesForAnyone: Set<Char>
            get() = responses.joinToString("").toSet()

        val yesForEveryone: Set<Char>
            get() = yesForAnyone.filter { question ->
                responses.all { it.contains(question) }
            }.toSet()
    }
}
