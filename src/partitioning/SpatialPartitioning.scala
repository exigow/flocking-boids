package partitioning

object SpatialPartitioning {

  def partition(positions: Seq[Position]): SpatialGrid = {
    ???
  }

  trait Position {

    def getX(): Float

    def getY(): Float

  }
  
  class SpatialGrid(cells: Seq[Cell], cellsOneDimensionCount: Int, cellSize: Float) {

    def neighborsOf(spot: Position): Seq[Position] = {
      val cellIndex = cellIndexOf(spot)
      ???
    }

    def cellIndexOf(spot: Position): Int = {
      val (x, y) = (spot.getX(), spot.getY())
      val cellX: Int = (x / cellSize).toInt
      val cellY: Int = (y / cellSize).toInt
      cellsOneDimensionCount * cellX + cellY
    }

  }

  abstract class Cell extends Seq[Position]

}
