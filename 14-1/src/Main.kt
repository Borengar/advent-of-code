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

    for (robot in robots) {
        robot.calculatePositionAfterSteps(100)
    }

    val nwRobots = robots.filter { it.x < (SPACE_WIDTH - 1) / 2 && it.y < (SPACE_HEIGHT - 1) / 2 }
    val neRobots = robots.filter { it.x < (SPACE_WIDTH - 1) / 2 && it.y > (SPACE_HEIGHT - 1) / 2 }
    val swRobots = robots.filter { it.x > (SPACE_WIDTH - 1) / 2 && it.y < (SPACE_HEIGHT - 1) / 2 }
    val seRobots = robots.filter { it.x > (SPACE_WIDTH - 1) / 2 && it.y > (SPACE_HEIGHT - 1) / 2 }

    println(nwRobots.count() * neRobots.count() * swRobots.count() * seRobots.count())
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