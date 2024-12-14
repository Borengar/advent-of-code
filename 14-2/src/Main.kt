import java.io.File

const val SPACE_WIDTH = 101
const val SPACE_HEIGHT = 103

fun main() {
    val inputData = File("src/input.txt").readText()
    val regex = """p=(?<x>\d+),(?<y>\d+) v=(?<vX>-?\d+),(?<vY>-?\d+)""".toRegex()
    val matchResult = regex.findAll(inputData)
    val robots: MutableList<Robot> = mutableListOf()

    for (result in matchResult) {
        robots.add(Robot(
            result.groups["x"]!!.value.toInt(),
            result.groups["y"]!!.value.toInt(),
            result.groups["vX"]!!.value.toInt(),
            result.groups["vY"]!!.value.toInt()
        ))
    }

    var maxSafetyRatingSoFar = 0
    var maxSafetyStep = 0
    for (i in 0..10000)
    for (robot in robots) {
        robot.calculatePositionAfterSteps(1)
        val robotsAtCenter = robots.filter { it.x > 30 && it.x < 70 && it.y > 30 && it.y < 70 }
        if (robotsAtCenter.count() <= maxSafetyRatingSoFar) {
            continue
        }
        drawRobots(robots)
        maxSafetyRatingSoFar = robotsAtCenter.count()
        maxSafetyStep = i + 1
    }
    println(maxSafetyStep)
}

class Robot(var x: Int, var y: Int, val vX: Int, val vY: Int) {
    fun calculatePositionAfterSteps(steps: Int) {
        x = (x + steps * vX) % SPACE_WIDTH
        if (x < 0) {
            x += SPACE_WIDTH
        }
        y = (y + steps * vY) % SPACE_HEIGHT
        if (y < 0) {
            y += SPACE_HEIGHT
        }
    }
}

// Draw robots position in console
fun drawRobots(robots: MutableList<Robot>) {
    // Clear console
    print("\u001b[H\u001b[2J")
    val drawing: Array<Array<Char>> = Array(SPACE_WIDTH) { Array(SPACE_HEIGHT) { '.' } }
    for (robot in robots) {
        drawing[robot.x][robot.y] = '#'
    }
    for (y in 0..<SPACE_HEIGHT) {
        for (x in 0..<SPACE_WIDTH) {
            print(drawing[x][y])
        }
        println()
    }
}