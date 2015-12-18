package partitioning

import scala.collection.mutable.ListBuffer

object SpatialPartitioning {

  private val GRID_SIZE = 16
  private val CELL_SIZE = 64

  def partition(positions: Seq[Position]) : Product = {
    val matrix = Array.ofDim[ListBuffer[Position]](GRID_SIZE, GRID_SIZE)
    for (pos <- positions) {
      val (x, y) = castPositionToCellIndex(pos)
      if (matrix(x)(y) == null) {
        matrix(x)(y) = new ListBuffer[Position]
      }
      matrix(x)(y) += pos
    }
    new Product(matrix)
  }

  trait Position {

    def position(): (Float, Float)

  }

  private def applyBoundingBox(value: Int): Int = {
    val max = GRID_SIZE - 1
    if (value > max)
      return max
    if (value < 0)
      return 0
    value
  }

  class Product(input: Array[Array[ListBuffer[Position]]]) {

    def neighborsOf(who: Position): Seq[Position] = {
      val (x, y) = castPositionToCellIndex(who)
      val a = input(x)(y) // todo input(x)(y) ++ input(x)(y + 1)
      if (a == null)
        return List.empty
      val others = a.filterNot(tested => tested == who)
      others
    }

  }

  private def castPositionToCellIndex(spot: Position): (Int, Int) = {
    val (x, y) = spot.position()
    val cellX = applyBoundingBox((x / CELL_SIZE).toInt)
    val cellY = applyBoundingBox((y / CELL_SIZE).toInt)
    (cellX, cellY)
  }

}
