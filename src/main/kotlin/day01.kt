fun main() {
    withInputData("day01.txt") { data ->
        val intData = data.map { it.toInt() }

        val sumOf2 = findSumOf2(intData, 2020)
        val sumOf3 = findSumOf3(intData, 2020)

        println(sumOf2)
        println(sumOf3)
    }
}

fun findSumOf2(data: List<Int>, sum: Int): Int? {
    var value: Int? = null
    var i = 0
    while (i < data.size && value == null) {
        var j = i
        while (j < data.size && value == null) {
            if (data[i] + data[j] == sum) {
                value = data[i] * data[j]
            }
            j++
        }
        i++
    }
    return value
}

fun findSumOf3(data: List<Int>, sum: Int): Int? {
    var value: Int? = null
    var i = 0
    while (i < data.size && value == null) {
        val subSum = findSumOf2(data, sum - data[i])
        if (subSum != null) {
            value = data[i] * subSum
        }
        i++
    }
    return value
}
