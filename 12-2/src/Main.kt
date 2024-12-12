import java.io.File

fun main() {
    val garden = getInput()
    val areas: MutableList<Area> = mutableListOf()
    for (x in 0..<garden.count()) {
        for (y in 0..<garden[x].count()) {
            val expandableAreas = areas.filter { it.plotCanBeAddedToArea(garden[x][y], x, y) }
            if (expandableAreas.isEmpty()) {
                val area = Area(garden[x][y], x, y)
                areas.add(area)
            } else if (expandableAreas.count() == 1) {
                expandableAreas[0].addPlotToArea(x, y)
            } else {
                expandableAreas[0].addPlotToArea(x, y)
                for (i in 1..<expandableAreas.count()) {
                    expandableAreas[0].mergeAreas(expandableAreas[i])
                    areas.remove(expandableAreas[i])
                }
            }
        }
    }

    var sum = 0
    for (area in areas) {
        sum += area.getFencePrice()
    }
    println(sum)
}

fun getInput(): Array<Array<Char>> {
    val inputData = File("src/input.txt").readLines()
    val garden: MutableList<Array<Char>> = mutableListOf()
    for (line in inputData) {
        garden.add(line.toCharArray().toTypedArray())
    }
    return garden.toTypedArray()
}

class Area(val PlantType: Char, x: Int, y: Int) {
    val plots: MutableList<Pair<Int, Int>> = mutableListOf(Pair(x, y))

    fun getFencePrice(): Int {
        return plots.count() * getSideCount()
    }

    fun getSideCount(): Int {
        val sides: MutableList<Pair<Pair<Int, Int>, Direction>> = mutableListOf()
        var count = 0
        for (plot in plots.sortedWith(compareBy<Pair<Int, Int>> { it.first }.thenBy { it.second })) {
            // North
            if (!plots.any { it.first == plot.first - 1 && it.second == plot.second }) {
                sides.add(Pair(Pair(plot.first, plot.second), Direction.NORTH))

                if (!sides.any { it.second == Direction.NORTH && it.first.first == plot.first && it.first.second == plot.second - 1 }) {
                    count++
                }
            }

            // East
            if (!plots.any { it.first == plot.first && it.second == plot.second + 1 }) {
                sides.add(Pair(Pair(plot.first, plot.second), Direction.EAST))

                if (!sides.any { it.second == Direction.EAST && it.first.first == plot.first - 1 && it.first.second == plot.second }) {
                    count++
                }
            }

            // South
            if (!plots.any { it.first == plot.first + 1 && it.second == plot.second }) {
                sides.add(Pair(Pair(plot.first, plot.second), Direction.SOUTH))

                if (!sides.any { it.second == Direction.SOUTH && it.first.first == plot.first && it.first.second == plot.second - 1 }) {
                    count++
                }
            }

            // West
            if (!plots.any { it.first == plot.first && it.second == plot.second - 1 }) {
                sides.add(Pair(Pair(plot.first, plot.second), Direction.WEST))

                if (!sides.any { it.second == Direction.WEST && it.first.first == plot.first - 1 && it.first.second == plot.second }) {
                    count++
                }
            }
        }
        return count
    }

    fun plotCanBeAddedToArea(type: Char, x: Int, y: Int): Boolean {
        if (type != PlantType)
            return false

        return plots.any {
            (it.first == x && it.second == y + 1) ||
            (it.first == x && it.second == y - 1) ||
            (it.first == x - 1 && it.second == y) ||
            (it.first == x + 1 && it.second == y)
        }
    }

    fun addPlotToArea(x: Int, y: Int) {
        plots.add(Pair(x, y))
    }

    fun mergeAreas(area: Area) {
        plots.addAll(area.plots)
    }
}

enum class Direction {
    NORTH, EAST, SOUTH, WEST
}