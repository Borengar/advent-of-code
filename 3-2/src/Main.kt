import java.io.File

fun main() {
    val inputData = File("src/input.txt").readText()
    val regex = Regex("""(?<name>mul|do|don't)\(((?<number1>\d+),(?<number2>\d+))?\)""")

    val regexResult = regex.findAll(inputData)
    var sum = 0
    var enabled = true
    for (match in regexResult) {
        when (match.groups["name"]?.value) {
            "mul" -> {
                if (enabled) {
                    sum += match.groups["number1"]!!.value.toInt() * match.groups["number2"]!!.value.toInt()
                }
            }
            "do" -> enabled = true
            "don't" -> enabled = false
        }
    }

    println(sum)
}