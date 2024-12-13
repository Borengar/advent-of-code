import java.io.File

fun main() {
    val inputData = File("src/input.txt").readText()
    val regex = """Button A: X\+(?<aX>\d+), Y\+(?<aY>\d+)\nButton B: X\+(?<bX>\d+), Y\+(?<bY>\d+)\nPrize: X=(?<prizeX>\d+), Y=(?<prizeY>\d+)""".toRegex()
    val matchResult = regex.findAll(inputData)
    val clawMachines: MutableList<ClawMachine> = mutableListOf()

    for (result in matchResult) {
        clawMachines.add(ClawMachine(
            result.groups["aX"]!!.value.toDouble(),
            result.groups["aY"]!!.value.toDouble(),
            result.groups["bX"]!!.value.toDouble(),
            result.groups["bY"]!!.value.toDouble(),
            result.groups["prizeX"]!!.value.toDouble() + 10000000000000,
            result.groups["prizeY"]!!.value.toDouble() + 10000000000000))
    }

    var tokensUsed = 0L
    for (machine in clawMachines) {
        val buttonPresses = machine.calculateButtonPresses()
        if (buttonPresses.first % 1 == 0.toDouble() && buttonPresses.second % 1 == 0.toDouble()) {
            tokensUsed += buttonPresses.first.toLong() * 3 + buttonPresses.second.toLong()
        }
    }
    println(tokensUsed)
}

class ClawMachine(val aX: Double, val aY: Double, val bX: Double, val bY: Double, val prizeX: Double, val prizeY: Double) {
    /*
    94a + 22b - 8400 = 0
    34a + 67b - 5400 = 0

    22*(-5400) - 67*(-8400)         bX*prizeY*-1 - bY*prizeX*-1
    ----------------------- = 80    ---------------------------
    94*67 - 34*22                   aX*bY - bX*aY

    (-8400)*34 - (-5400)*94         prizeX*aY*-1 - prizeY*aX*-1
    ----------------------- = 40    ---------------------------
    94*67 - 34*22                   ax*bY - bX*aY
     */
    fun calculateButtonPresses(): Pair<Double, Double> {
        val aPresses = (bX * prizeY * -1 - bY * prizeX * -1) / (aX * bY - bX * aY)
        val bPresses = (prizeX * aY * -1 - prizeY * aX * -1) / (aX * bY - bX * aY)

        return Pair(aPresses, bPresses)
    }
}