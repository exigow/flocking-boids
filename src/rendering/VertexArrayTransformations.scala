package rendering

import com.badlogic.gdx.math.MathUtils._

object VertexArrayTransformations {

  def rotate(vertices: Array[Float], radians: Float): Array[Float] = forEachVertex(vertices,
    (x, y) => (cos(radians) * x - sin(radians) * y, sin(radians) * x + cos(radians) * y)
  )
  
  def move(vertices: Array[Float], transformX: Float, transformY: Float): Array[Float] = forEachVertex(vertices,
    (x, y) => (x + transformX, y + transformY)
  )

  def scale(vertices: Array[Float], scale: Float): Array[Float] = forEachVertex(vertices,
    (x, y) => (x * scale, y * scale)
  )

  private def forEachVertex(vertices: Array[Float], transform: (Float, Float) => (Float, Float)) : Array[Float] = {
    val result = new Array[Float](vertices.length)
    val count = vertices.length / 2 - 1
    for (pivot <- 0 to count) {
      val (px, py) = (pivot * 2, pivot * 2 + 1)
      val (tx, ty) = transform.apply(vertices(px), vertices(py))
      result(px) = tx
      result(py) = ty
    }
    result
  }

}
