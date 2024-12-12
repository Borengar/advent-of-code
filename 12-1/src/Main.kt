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
        return plots.count() * getFenceCount()
    }

    fun getFenceCount(): Int {
        var count = 0
        for (plot in plots) {
            count += 4 - plots.count {
                (it.first == plot.first && it.second == plot.second + 1) ||
                (it.first == plot.first && it.second == plot.second - 1) ||
                (it.first == plot.first - 1 && it.second == plot.second) ||
                (it.first == plot.first + 1 && it.second == plot.second)
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