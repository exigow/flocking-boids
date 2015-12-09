import java.lang.Math._

object VertexArrayTransformations {

  def rotate(vertices: Array[Float], radians: Float): Array[Float] = forEachVertex(vertices,
    (x, y) => (
        (cos(radians) * x - sin(radians) * y).toFloat,
        (sin(radians) * x + cos(radians) * y).toFloat
      )
    )
  
  def move(vertices: Array[Float], transformX: Float, transformY: Float): Array[Float] = forEachVertex(vertices,
    (x, y) => (x + transformX, y + transformY))

  def forEachVertex(vertices: Array[Float], vertex: (Float, Float) => (Float, Float)) : Array[Float] = {
    val result = new Array[Float](vertices.length)
    for (pivot <- 0 to 3) {
      val pivotX = pivot * 2
      val pivotY = pivot * 2 + 1
      val newPosition = vertex.apply(vertices(pivotX), vertices(pivotY))
      result(pivotX) = newPosition._1
      result(pivotY) = newPosition._2
    }
    result
  }

}
