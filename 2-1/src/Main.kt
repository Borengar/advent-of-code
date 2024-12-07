import java.io.File
import kotlin.math.abs

fun main() {
    val inputData = File("src/input.txt").readLines()
    var safeReports = 0

    for (report in inputData) {
        if (reportIsSafe(report)) {
            safeReports++
        }
    }

    println(safeReports)
}

fun reportIsSafe(report: String): Boolean {
    val levels = report.split(" ")
    val increasing = levels[0].toInt() < levels[1].toInt()

    for (i in 0..<levels.count() - 1) {
        val delta = abs(levels[i + 1].toInt() - levels[i].toInt())
        if (delta < 1 || delta > 3) {
            return false
        }

        if (levels[i].toInt() < levels[i + 1].toInt() != increasing) {
            return false
        }
    }

    return true
}