package day06

fun main(vararg args: String) {

    val identifiedCoordinates = loadIdentifiedCoordinates()
    val coordinates = identifiedCoordinates.map(IdentifiedCoordinates::coordinates)

    val gridBounds = Bounds.fromCoordinates(coordinates)
    val grid = Grid(gridBounds)

    // Part 1
    grid.fillWithClosestCoordinatesId(identifiedCoordinates)

    val infiniteAreaIds = grid.getIdsAtEdge()
    val finiteAreaIds = identifiedCoordinates.map { it.id } - infiniteAreaIds

    val maxFiniteArea = finiteAreaIds.map { id -> grid.getAreaForCellsMatchingCondition { it == id } }.max()

    println("Largest finite area: \n\t$maxFiniteArea")
    println()

    // Part 2
    grid.fillWithCumulativeDistanceToAllCoordinates(coordinates)
    val areaWithCumulativeDistancesBelow10K = grid.getAreaForCellsMatchingCondition { it < 10000 }

    println("Area with cumulative distances smaller than 10000: \n\t$areaWithCumulativeDistancesBelow10K")

}