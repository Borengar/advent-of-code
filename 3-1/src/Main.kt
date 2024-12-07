import java.io.File

fun main() {
    val inputData = File("src/input.txt").readText()
    val regex = Regex("""mul\((\d+),(\d+)\)""")

    val regexResult = regex.findAll(inputData)
    var sum = 0
    for (match in regexResult) {
        sum += match.groupValues[1].toInt() * match.groupValues[2].toInt()
    }

    println(sum)
}