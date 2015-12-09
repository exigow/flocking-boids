import java.lang.Math._

object VertexArrayTransformations {

  def rotate(vertices: Array[Float], radians: Float): Array[Float] = forEachVertex(vertices,
    (x, y) => (
      (cos(radians) * x - sin(radians) * y).toFloat,
      (sin(radians) * x + cos(radians) * y).toFloat
    )
  )
  
  def move(vertices: Array[Float], transformX: Float, transformY: Float): Array[Float] = forEachVertex(vertices,
    (x, y) => (
      x + transformX,
      y + transformY
    )
  )

  private def forEachVertex(vertices: Array[Float], transform: (Float, Float) => (Float, Float)) : Array[Float] = {
    val result = new Array[Float](vertices.length)
    val count = vertices.length / 2 - 1
    for (pivot <- 0 to count) {
      val pointer = (pivot * 2, pivot * 2 + 1)
      val transformed = transform.apply(vertices(pointer._1), vertices(pointer._2))
      result(pointer._1) = transformed._1
      result(pointer._2) = transformed._2
    }
    result
  }

}
