package partitioning

import scala.collection.mutable.ListBuffer

object SpatialPartitioning {

  def partition(positions: Seq[Position], gridSize: Int, bucketSize: Float) : PartitionProduct = {
    val matrix = Array.ofDim[ListBuffer[Position]](gridSize, gridSize)
    for (pos <- positions) {
      val (x, y) = castPositionToBucketIndex(pos, gridSize, bucketSize)
      if (matrix(x)(y) == null)
        matrix(x)(y) = new ListBuffer[Position]
      matrix(x)(y) += pos
    }
    new PartitionProduct(matrix, gridSize, bucketSize)
  }

  trait Position {

    def position(): (Float, Float)

  }

  class PartitionProduct(buckets: Array[Array[ListBuffer[Position]]], gridSize: Int, bucketSize: Float) {

    def neighborsOf(who: Position): Seq[Position] = {
      val (x, y) = castPositionToBucketIndex(who, gridSize, bucketSize)
      def stub(x: Int, y: Int): Seq[Position] = {
        val max = gridSize - 1
        val min = 0
        if (x > max || y > max || x < min || y < min)
          return Seq.empty
        val result = buckets(x)(y)
        if (result == null)
          return Seq.empty
        result
      }
      val joined = new ListBuffer[Position]()
      for (cx <- -1 to 1) {
        for (cy <- -1 to 1) {
          joined ++= stub(x + cx, y + cy)
        }
      }
      val others = joined.filterNot(tested => tested == who)
      others
    }

  }

  private def castPositionToBucketIndex(spot: Position, gridSize: Int, bucketSize: Float): (Int, Int) = {
    val (x, y) = spot.position()
    def keepArraySize(value: Int): Int = {
      val max = gridSize - 1
      if (value > max)
        return max
      if (value < 0)
        return 0
      value
    }
    val cellX = keepArraySize((x / bucketSize).toInt)
    val cellY = keepArraySize((y / bucketSize).toInt)
    (cellX, cellY)
  }

}
