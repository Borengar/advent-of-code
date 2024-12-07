import java.io.File
import kotlin.math.abs

fun main() {
    val inputData = File("src/input.txt").readLines()
    var safeReports = 0

    for (report in inputData) {
        if (reportIsSafe(report.split(" "))) {
            safeReports++
        }
    }

    println(safeReports)
}

fun reportIsSafe(levels: List<String>, dampened: Boolean = false): Boolean {
    val increasing = levels[0].toInt() < levels[1].toInt()

    for (i in 0..<levels.count() - 1) {
        val delta = abs(levels[i + 1].toInt() - levels[i].toInt())
        if (delta < 1 || delta > 3 || levels[i].toInt() < levels[i + 1].toInt() != increasing) {
            if (dampened) {
                return false
            }

            val copy1 = levels.toMutableList()
            val copy2 = levels.toMutableList()
            copy1.removeAt(i)
            copy2.removeAt(i + 1)

            if (i == 0) {
                return reportIsSafe(copy1, true) || reportIsSafe(copy2, true)
            } else {
                val copy3 = levels.toMutableList()
                copy3.removeAt(i - 1)
                return reportIsSafe(copy1, true) || reportIsSafe(copy2, true) || reportIsSafe(copy3, true)
            }
        }
    }

    return true
}