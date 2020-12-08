import java.io.File

fun withInputData(fileName: String, call: (data: List<String>) -> Unit) =
    call(File("input/$fileName").readLines())
